package com.sesacthon.foreco.mission.service;

import com.sesacthon.foreco.category.entity.Trash;
import com.sesacthon.foreco.category.repository.TrashRepository;
import com.sesacthon.foreco.member.repository.MemberRepository;
import com.sesacthon.foreco.mission.QuizMissionImage;
import com.sesacthon.foreco.mission.dto.MissionDto;
import com.sesacthon.foreco.mission.entity.Difficulty;
import com.sesacthon.foreco.mission.entity.Kind;
import com.sesacthon.foreco.mission.entity.Participation;
import com.sesacthon.foreco.mission.dto.MissionDetailDto;
import com.sesacthon.foreco.mission.dto.MissionResultInfoDto;
import com.sesacthon.foreco.mission.dto.MissionResultDto;
import com.sesacthon.infra.feign.dto.request.ImageDivisionRequestDto;
import com.sesacthon.foreco.mission.dto.MissionInfo;
import com.sesacthon.foreco.mission.dto.QuizMissionAnswer;
import com.sesacthon.foreco.mission.dto.QuizMissionChoice;
import com.sesacthon.foreco.mission.dto.QuizMissionDto;
import com.sesacthon.foreco.mission.entity.Mission;
import com.sesacthon.foreco.mission.exception.ExceedsMaximumParticipationException;
import com.sesacthon.foreco.mission.exception.MissionNotFountException;
import com.sesacthon.foreco.mission.repository.MissionRepository;
import com.sesacthon.foreco.mission.repository.ParticipationRepository;
import com.sesacthon.foreco.trash.entity.TrashInfo;
import com.sesacthon.foreco.trash.repository.TrashInfoRepository;
import com.sesacthon.global.exception.ErrorCode;
import com.sesacthon.infra.feign.client.mission.QuizMissionClient;
import com.sesacthon.infra.s3.service.S3Downloader;
import com.sesacthon.infra.s3.service.S3Uploader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class MissionService {

  private final QuizMissionClient quizMissionAiServer;
  private final S3Uploader s3Uploader;
  private final S3Downloader s3Downloader;
  private final TrashInfoRepository trashInfoRepository;
  private final TrashRepository trashRepository;
  private final MissionRepository missionRepository;
  private final ParticipationRepository participationRepository;
  private final MemberRepository memberRepository;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  //TODO api요청한 멤버정보(memberId, regiondId)를 controller에서 넘겨주는 코드로 수정한후 아래 2개의 필드 삭제 필요.
  private static final Long REGION_ID = 1L; //지역 고정 -
  private static final UUID USER_ID = null;//임시 UUID...

  public QuizMissionDto getQuizMission(Long missionId) {
    //미션 정보 조회, 조회가 안될 시 에러 발생
    Mission mission = missionRepository.findById(missionId)
        .orElseThrow(() -> new MissionNotFountException(
            ErrorCode.MISSION_NOT_FOUND));
    MissionInfo missionInfo = getMissionInfo(mission);
    //mission에 사용할 쓰레기들의 정보를 4개 가져옴
    List<QuizMissionImage> quizMissionImages = QuizMissionImage.getRandomQuizMissionImages();
    //첫번째 값을 정답으로 취급함.
    QuizMissionImage answerInfo = quizMissionImages.get(0);
    //AI서버에 정답 이미지를 보내 4개의 이미지로 분할함
    List<String> dividedImageUrls = divideImage(answerInfo);
    List<QuizMissionChoice> choices = new ArrayList<>();
    Set<String> mixUpChoices = new HashSet<>();
    quizMissionImages.forEach(choice -> {
      mixUpChoices.add(choice.getTrash());
    });
    //answerDto객체
    QuizMissionAnswer answer = null;
    answer = getQuizMissionAnswer(answerInfo, choices, mixUpChoices, answer);
    //배출방법 조회
    return new QuizMissionDto(missionInfo, dividedImageUrls, choices, answer);
  }

  /**
   * @param answerInfo   정답으로 사용할 Image의 정보
   * @param choices      response로 반환할 choicesDto
   * @param mixUpChoices 선택지의 순서를 섞은 set
   * @param answer       response로 반환할 answerDto
   * @return
   */
  private QuizMissionAnswer getQuizMissionAnswer(QuizMissionImage answerInfo,
      List<QuizMissionChoice> choices, Set<String> mixUpChoices, QuizMissionAnswer answer) {
    Long index = 0L;
    for (String mixUpChoice : mixUpChoices) {
      choices.add(new QuizMissionChoice(++index, mixUpChoice));
      if (mixUpChoice.equals(answerInfo.getTrash())) {
        String disposalMethod = getDisposalMethod(answerInfo.getTrash());//배출방법 조회
        answer = new QuizMissionAnswer(index, answerInfo.getTrash(),
            "https://" + bucket + answerInfo.getUrl(),
            disposalMethod);
      }
    }
    return answer;
  }

  /**
   * @param mission 미션entity
   * @return missionInfo(리워드, 제목, 소개, 유저가 이용한 횟수, 유저가 최대 참여 가능한 횟수)
   */
  private MissionInfo getMissionInfo(Mission mission) {
    MissionInfo missionInfo;
    Long missionId = mission.getId();
    Long rewardPoint = mission.getRewardPoint();
    String title = mission.getTitle();
    String description = mission.getDescription();
    Long personalCount = mission.getPersonalCount();
    //참여가능한 횟수를 초과했는지 확인함.
    Long personalParticipatingCount = participationRepository.countByMemberIdAndMissionId(USER_ID,
        missionId);
    //현재 유저가 이용가능한 횟수를 초과했는지 확인
    if (personalParticipatingCount >= personalCount) {
      throw new ExceedsMaximumParticipationException(ErrorCode.EXCEEDS_MAXIMUM_PARTICIPATION);
    }
    missionInfo = new MissionInfo(rewardPoint, title, description, personalParticipatingCount,
        personalCount);
    log.info("personalParticipatingCount:{}", personalParticipatingCount);
    return missionInfo;
  }

  /**
   * 이미지를 분할하고 url을 반환 받음
   *
   * @param answerInfo
   * @return 분할되고 s3에 업로드된 이미지 url들
   */
  private List<String> divideImage(QuizMissionImage answerInfo) {
    List<String> dividedAnswerImages = quizMissionAiServer.divideImage(
        new ImageDivisionRequestDto("https://" + bucket + answerInfo.getUrl(),
            answerInfo.getCoordinate())).getImages();
    //이미지를 decoding한 후, s3에 업로드함
    List<String> imageUrls = new ArrayList<>();
    for (String base64Data : dividedAnswerImages) {
      String prefix = "dividedTrashImage";
      imageUrls.add(s3Uploader.uploadFileUsingStream(base64Data, prefix));
    }
    return imageUrls;
  }

  /**
   * @param trashName 조회할 쓰레기 이름
   * @return 배출방법이 존재하면, 배출방법을 조회하지 않을 경우 " "
   */
  private String getDisposalMethod(String trashName) {
    List<Trash> trashs = trashRepository.findByKeyword(trashName);
    if (trashs.size() > 0) {
      Trash trash = trashs.get(0);
      Optional<TrashInfo> trashInfo = trashInfoRepository.findByTrashIdAndRegionId(
          trash.getId(), REGION_ID);
      if (trashInfo.isPresent()) {
        return trashInfo.get().getMethod();
      }
    }
    return " ";
  }

  public List<MissionDetailDto> findMissionsWithKind(String kind, UUID memberId) {
    List<Mission> missions = missionRepository.findByKind(Kind.valueOf(kind));
    return createEntityToDto(missions, memberId);
  }

  public List<MissionDetailDto> findMissionsWithKindAndDifficulty(String kind, String difficulty, UUID memberId) {
    List<Mission> missions = missionRepository.findByKindAndDifficulty(Kind.valueOf(kind), Difficulty.valueOf(difficulty));
    return createEntityToDto(missions, memberId);
  }

  private List<MissionDetailDto> createEntityToDto(List<Mission> missions, UUID memberId) {
    return missions.stream()
        .map(mission -> {
          // 미션 아이디에 맞는 모든 이력 데이터를 가져온다.
          List<Participation> totalParticipation = participationRepository.findByMissionId(mission.getId());

          long totalParticipationSize = totalParticipation.size();
          // 멤버 아이디에 해당하는 참여 이력 데이터들을 가져온다.
          long personalParticipationSize = getPersonalParticipationSize(memberId, totalParticipation);
          String iconUrl = s3Downloader.getIconUrl(mission.getIcon().getIconFile());
          return new MissionDetailDto(mission, personalParticipationSize, totalParticipationSize, iconUrl);
        })
        .collect(Collectors.toList());
  }

  private long getPersonalParticipationSize(UUID memberId, List<Participation> totalParticipation) {
    return totalParticipation.stream()
        .filter(participation -> participation.getMember().getId().equals(memberId))
        .filter(participation -> participation.getPerformDate().equals(LocalDate.now()))
        .count();
  }

  /**
   * @param missionResultDto (미션ID, 성공/실패 여부)
   * @return
   */
  @Transactional
  public MissionResultInfoDto recordHistory(MissionResultDto missionResultDto, UUID memberId) {
    Mission mission = missionRepository.findById(missionResultDto.getMissionId())
        .orElseThrow(() -> new MissionNotFountException(
            ErrorCode.MISSION_NOT_FOUND));
    if (missionResultDto.getIsSuccess()) {
      return new MissionResultInfoDto(2000L, 2000L);
    } else {
      return new MissionResultInfoDto(2000L, 0L);
    }
    //TODO ContextHolde에서 User의 UUID를 받아온 이후, 아래 주석은 활성화하고 상단의 코드는 제거 해야함
//    Mission mission = missionRepository.findById(missionResultDto.getMissionId())
//        .orElseThrow(() -> new MissionNotFountException(
//            ErrorCode.MISSION_NOT_FOUND));
//    Member member  = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
//    MissionResultInfoDto missionResult = new MissionResultInfoDto(mission.getRewardPoint(), member.getTotalPoint());
//    //성공한 경우
//    if(missionResultDto.getIsSuccess()){
//      member.updateTotalPoint(member.getTotalPoint() + mission.getRewardPoint());
//      missionResult = new MissionResultInfoDto(mission.getRewardPoint(), member.getTotalPoint());
//    }
//    return missionResult;
  }

  public List<MissionDetailDto> findMissions(String kind, String difficulty, UUID memberId) {
    if (difficulty == null) {
      return findMissionsWithKind(kind, memberId);
    }
    return findMissionsWithKindAndDifficulty(kind, difficulty, memberId);
  }

  public MissionDto filterAvailable(List<MissionDetailDto> missions, boolean available) {
    if (!available) {
      return new MissionDto(missions);
    }

    List<MissionDetailDto> filteredMissions = missions.stream()
        .filter(mission -> mission.getPersonalCount() - mission.getPersonalParticipatingCount() > 0)
        .collect(Collectors.toList());
    return new MissionDto(filteredMissions);
  }
}
