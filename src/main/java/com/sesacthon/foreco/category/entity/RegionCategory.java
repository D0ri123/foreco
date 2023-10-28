package com.sesacthon.foreco.category.entity;

import static jakarta.persistence.FetchType.*;

import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.disposal.controller.region.entity.Region;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RegionCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "trash_id")
  private Trash trash;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "region_id")
  private Region region;

  @OneToMany(mappedBy = "regionCategory")
  private List<Disposal> disposals = new ArrayList<>();

}
