package com.sesacthon.foreco.mission.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import com.sesacthon.foreco.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 미션에 참가하는 멤버 정보를 담은 테이블
 */
@Entity
@NoArgsConstructor
@Getter
public class Participation {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private LocalDate performDate;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "mission_id")
  private Mission mission;

}
