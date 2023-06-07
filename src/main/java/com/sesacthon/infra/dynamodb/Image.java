package com.sesacthon.infra.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName="TrashImg")
@Getter
@NoArgsConstructor
public class Image {

  @DynamoDBHashKey(attributeName = "trashCategory")
  private String trashCategory;

  @DynamoDBAttribute(attributeName = "imgUrl")
  private String imgUrl;

  @Builder
  public Image(String trashCategory, String imageUrl) {
    this.trashCategory = trashCategory;
    this.imgUrl = imageUrl;
  }

}
