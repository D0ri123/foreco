package com.sesacthon.foreco.expectedOutput.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.sesacthon.foreco.motivation.entity.Motivation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ExpectedOutput {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String outputName;
  private String outputImg;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "motivation_id")
  private Motivation motivation;
}
