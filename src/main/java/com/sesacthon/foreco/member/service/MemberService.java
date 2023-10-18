package com.sesacthon.foreco.member.service;

import static com.sesacthon.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.sesacthon.foreco.member.entity.Member;
import com.sesacthon.foreco.member.exception.MemberNotFoundException;
import com.sesacthon.foreco.member.repository.MemberRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  public Optional<Member> getMemberByUserNumber(String username) {
    return memberRepository.findByUsername(username);
  }

  public Member saveInfo(Member member) {
    return memberRepository.save(member);
  }

  public Member getMemberById(UUID uuid) {
    log.info("해당 uuid를 가진 멤버를 찾습니다.");
    return memberRepository.findById(uuid)
        .orElseThrow(()->new MemberNotFoundException(MEMBER_NOT_FOUND));
  }
}
