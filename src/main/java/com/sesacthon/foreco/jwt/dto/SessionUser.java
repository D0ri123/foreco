package com.sesacthon.foreco.jwt.dto;

import com.sesacthon.foreco.member.entity.Member;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser implements Serializable {

  private UUID uuid;
  private String authority;

  public SessionUser(Member member) {
    this.uuid = member.getId();
    this.authority = member.getRole().getAuthority();
  }
}
