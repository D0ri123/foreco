package com.sesacthon.foreco.category.repository;

import com.sesacthon.foreco.category.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("select c from Category c where c.region.id = :regionId and c.trashType = :categoryName")
  Optional<Category> findCategoryInfo(@Param("regionId") Long regionId,@Param("categoryName") String categoryName);
}
