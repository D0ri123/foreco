package com.sesacthon.foreco.mission.repository;

import com.sesacthon.foreco.mission.entity.Participation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

  @Query("select count(p) from Participation p where p.member.id =:memberId and p.mission.id =:missionId")
  Long countByMemberIdAndMissionId(@Param("memberId") UUID memberId, @Param("missionId") Long missionId);

  @Query("select p from Participation p where p.mission.id = :missionId")
  List<Participation> findByMissionId(@Param("missionId") Long missionId);

}
