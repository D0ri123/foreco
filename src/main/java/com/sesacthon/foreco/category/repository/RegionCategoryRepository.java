package com.sesacthon.foreco.category.repository;

import com.sesacthon.foreco.category.entity.RegionCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionCategoryRepository extends JpaRepository<RegionCategory, Long> {

  @Query("select rc from RegionCategory rc join fetch rc.trash where rc.region.id = :regionId")
  List<RegionCategory> findByRegionId(@Param("regionId") Long regionId);
}
