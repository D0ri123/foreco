package com.sesacthon.foreco.trash.repository;

import com.sesacthon.foreco.trash.entity.Trash;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashRepository extends JpaRepository<Trash, Long> {
  @Query("select t from Trash t join fetch t.category"
      + " join fetch t.region"
      + " where t.region.id = :regionId and t.trashName = :name and t.orderOfTab = :tab")
  Optional<Trash> findTrashByRegionAndName(@Param("regionId") Long regionId,
                                         @Param("name") String name, @Param("tab") int tab);
}
