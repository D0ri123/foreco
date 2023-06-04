package com.sesacthon.foreco.category.entity;

import com.sesacthon.foreco.trash.entity.Trash;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쓰레기 품목(카테고리).
 */
@Entity
@NoArgsConstructor
@Getter
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String trashType;

  @OneToMany(mappedBy = "category")
  private List<Trash> trashes = new ArrayList<>();

}
