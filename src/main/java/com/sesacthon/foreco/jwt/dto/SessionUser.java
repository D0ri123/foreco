package com.sesacthon.foreco.jwt.dto;

import com.sesacthon.foreco.member.entity.Member;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SessionUser implements Serializable {

  private final UUID uuid;
  private final String authority;

  public SessionUser(Member member) {
    this.uuid = member.getId();
    this.authority = member.getRole().getAuthority();
  }

}
