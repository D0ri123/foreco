package com.sesacthon.foreco.disposal.repository;


import com.sesacthon.foreco.disposal.entity.Disposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DisposalRepository extends JpaRepository<Disposal, Long> {

  @Query("select d from Disposal d where d.regionCategory.id = :regionCategoryId")
  Disposal findByRegionCategoryId(@Param("regionCategoryId") Long regionCategoryId);
}
