package com.sesacthon.foreco.motivation.repository;

import com.sesacthon.foreco.motivation.entity.Motivation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MotivationRepository extends JpaRepository<Motivation, Long> {

  @Query("select m from Motivation m where m.category.id = :categoryId")
  Optional<Motivation> findResultByCategoryId(@Param("categoryId") Long categoryId);
}
