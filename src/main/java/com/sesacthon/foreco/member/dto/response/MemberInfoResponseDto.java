package com.sesacthon.foreco.member.dto.response;


import com.sesacthon.foreco.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MemberInfoResponseDto {
  @Schema(description = "member의 프로필 이미지 url")
  private final String profileUrl;

  @Schema(description = "member의 이름")
  private final String name;

  @Schema(description = "member의 현재 리워드 포인트")
  private final Long point;

  public MemberInfoResponseDto(Member member) {
    this.profileUrl = member.getProfileUrl();
    this.name = member.getUsername();
    this.point = member.getTotalPoint();
  }

}
