package com.sesacthon.foreco.mission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.Getter;

@Getter
public enum QuizMissionImage {
  BOX("박스",
      ".s3.ap-northeast-2.amazonaws.com/quiz/49c79115-dbc6-43df-b6d5-0179dae7d042.jpg", "0.46588541666666666 0.5819444444444445 0.4390625 0.6027777777777777"),
  STYROFOAM("스티로폼",
      ".s3.ap-northeast-2.amazonaws.com/quiz/216e523d-b631-4454-8536-5eb1fd2bb72e.jpg","0.5065972222222223 0.4528891202498699 0.49375 0.36543466944299846"),
  GLASS_BOTTLE("유리병",
      ".s3.ap-northeast-2.amazonaws.com/quiz/d9afcb2a-02f2-4864-875e-5cb81151d1bb.jpg","0.45677083333333335 0.49027777777777776 0.16770833333333332 0.8305555555555556"),
  CAN("캔음료",
      ".s3.ap-northeast-2.amazonaws.com/quiz/8c4f63b9-b39f-4530-931e-68e588d908b3.jpg","0.5109318063508589 0.5024305555555556 0.42321707444039564 0.9506944444444444"),
  LAMP("형광등",
      ".s3.ap-northeast-2.amazonaws.com/quiz/ce13962d-9559-43d5-9039-253a0a5e8486.jpg","0.50859375 0.4704861111111111 0.40260416666666665 0.18819444444444444"),
  CLOTHES("헌옷",
      ".s3.ap-northeast-2.amazonaws.com/quiz/30a3a7ad-597c-43d7-b5e2-af34f185ff29.jpg","0.46171875 0.47685185185185186 0.7703125 0.8481481481481481");

  private String trash;
  private String url;
  private String coordinate;

  QuizMissionImage(String trash, String url, String coordinate) {
    this.trash = trash;
    this.url = url;
    this.coordinate = coordinate;
  }

  /**
   * @return 구성요소의 개수
   */
  private static int count() {
    return QuizMissionImage.values().length;
  }

  //미션에 사용할 값 반환
  public static List<QuizMissionImage> getRandomQuizMissionImages() {
    QuizMissionImage[] quizs = QuizMissionImage.values();

    Set<Integer> choices = new HashSet<>();
    Random rand = new Random();
    while (choices.size() < 4) {
      choices.add(rand.nextInt(count()));
    }

    List<QuizMissionImage> quizMissionImages = new ArrayList<>();
    for(int choice: choices){
      quizMissionImages.add(quizs[choice]);
    }

    return quizMissionImages;
  }
}
