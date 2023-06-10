package com.sesacthon.foreco.region.repository;

import com.sesacthon.foreco.region.entity.Region;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

  @Query("select r.id from Region r where r.city = :city and r.gu = :gu and r.dong = :dong")
  Optional<Long> findRegionByCityAndGuAndDong(@Param("city") String city,
      @Param("gu") String gu, @Param("dong") String dong);

}
