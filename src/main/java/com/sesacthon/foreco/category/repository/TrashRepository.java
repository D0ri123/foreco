package com.sesacthon.foreco.category.repository;

import com.sesacthon.foreco.category.entity.Trash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashRepository extends JpaRepository<Trash, Long> {
  
}
