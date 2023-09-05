package com.sesacthon.foreco.category.repository;

import com.sesacthon.foreco.category.entity.RegionCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionCategoryRepository extends JpaRepository<RegionCategory, Long> {

  Optional<RegionCategory> findByTrashIdAndRegionId(Long parentTrashId, Long regionId);

  @Query("select rc from RegionCategory rc join fetch rc.trash where rc.region.id = :regionId")
  List<RegionCategory> findRegionCategories(@Param("regionId") Long regionId);

  @Query("select rc.trash.id from RegionCategory rc where rc.region.id = :regionId")
  List<Long> findCategoryIdsByRegionId(@Param("regionId") Long regionId);

}
