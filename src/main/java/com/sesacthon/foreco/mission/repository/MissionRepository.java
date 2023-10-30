package com.sesacthon.foreco.mission.repository;

import com.sesacthon.foreco.mission.entity.Difficulty;
import com.sesacthon.foreco.mission.entity.Kind;
import com.sesacthon.foreco.mission.entity.Mission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission,Long> {

    @Query("select m from Mission m where m.kind = :kind")
    List<Mission> findByKind(@Param("kind") Kind kind);

    @Query("select m from Mission m where m.kind = :kind and m.difficulty = :difficulty")
    List<Mission> findByKindAndDifficulth(
        @Param("kind") Kind kind, @Param("difficulty") Difficulty difficulty);
}
