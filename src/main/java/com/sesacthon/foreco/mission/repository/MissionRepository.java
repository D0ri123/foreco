package com.sesacthon.foreco.mission.repository;

import com.sesacthon.foreco.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission,Long> {

}
