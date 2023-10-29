package com.sesacthon.foreco.mission.service;

import com.sesacthon.foreco.category.entity.Trash;
import com.sesacthon.foreco.category.repository.TrashRepository;
import com.sesacthon.foreco.mission.QuizMissionImage;
import com.sesacthon.foreco.mission.dto.ImageDivisionRequestDto;
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
import com.sesacthon.infra.s3.service.S3Uploader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class MissionService {

  private final QuizMissionClient quizMissionAiServer;
  private final S3Uploader s3Uploader;
  private final TrashInfoRepository trashInfoRepository;
  private final TrashRepository trashRepository;
  private final MissionRepository missionRepository;
  private final ParticipationRepository participationRepository;
  //TODO api요청한 멤버정보(memberId, regiondId)를 controller에서 넘겨주는 코드로 수정한후 아래 2개의 필드 삭제 필요.
  private static final Long REGION_ID = 1L; //지역 고정 -
  private static final UUID USER_ID = null;//임시 UUID...

  public QuizMissionDto getQuizMission(Long missionId) {
    //미션 정보 조회, 조회가 안될 시 에러 발생
    Mission mission = missionRepository.findById(missionId)
        .orElseThrow(() -> new MissionNotFountException(
            ErrorCode.MISSION_NOT_FOUND));
    MissionInfo missionInfo = getMissionInfo(missionId, mission);

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
        answer = new QuizMissionAnswer(index, answerInfo.getTrash(), answerInfo.getUrl(),
            disposalMethod);
      }
    }
    return answer;
  }

  /**
   * @param missionId 미션id
   * @param mission   미션entity
   * @return missionInfo(리워드, 제목, 소개, 유저가 이용한 횟수, 유저가 최대 참여 가능한 횟수)
   */
  private MissionInfo getMissionInfo(Long missionId, Mission mission) {
    MissionInfo missionInfo;
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
        new ImageDivisionRequestDto(answerInfo.getUrl(), answerInfo.getCoordinate())).getImages();
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

    Optional<Trash> trash = trashRepository.findByNameContainingAndParentTrashIsNotNull(trashName);
    if (trash.isPresent()) {
      Optional<TrashInfo> trashInfo = trashInfoRepository.findByTrashIdAndRegionId(
          trash.get().getId(), REGION_ID);
      if (trashInfo.isPresent()) {
        return trashInfo.get().getMethod();
      }
    }
    return " ";
  }


}
