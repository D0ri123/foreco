package com.sesacthon.foreco.disposal.repository;


import com.sesacthon.foreco.disposal.entity.Disposal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DisposalRepository extends JpaRepository<Disposal, Long> {

  @Query("select d from Disposal d join fetch d.category "
      + "where d.region.id = :regionId and d.day = :today")
  List<Disposal> findDisposableList(@Param("regionId") Long regionId,
                                    @Param("today") String today);
}
