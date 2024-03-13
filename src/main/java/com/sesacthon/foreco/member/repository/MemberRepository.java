package com.sesacthon.foreco.member.repository;

import com.sesacthon.foreco.member.entity.Member;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, UUID> {

  @Query("select m from Member m where m.id = :id")
  Optional<Member> findById(@Param("id") UUID uuid);

  @Query("select m from Member m where m.userNumber = :userNumber")
  Optional<Member> findByUsername(@Param("userNumber") String userNumber);

}
