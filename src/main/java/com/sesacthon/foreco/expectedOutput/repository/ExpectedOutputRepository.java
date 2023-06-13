package com.sesacthon.foreco.expectedOutput.repository;

import com.sesacthon.foreco.expectedOutput.entity.ExpectedOutput;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpectedOutputRepository extends JpaRepository<ExpectedOutput, Long> {

  @Query("select e from ExpectedOutput e where e.motivation.id = :motivationId")
  List<ExpectedOutput> findExpectedOutputs(@Param("motivationId") Long motivationId);
}
