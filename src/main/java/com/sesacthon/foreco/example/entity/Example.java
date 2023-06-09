package com.sesacthon.foreco.example.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.sesacthon.foreco.trash.entity.Trash;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쓰레기 예시 정보
 */
@Entity
@NoArgsConstructor
@Getter
public class Example {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 예시 쓰레기 이름
   */
  private String trashName;

  /**
   * 예시 쓰레기 배출방법
   */
  private String disposalMethod;

  /**
   * 예시 쓰레기 이미지
   */
  private String imgUrl;

  /**
   * 해당 데이터를 예시로 가지는 상세 쓰레기
   */
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "trash_id")
  private Trash trash;

}
