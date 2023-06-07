package com.sesacthon.infra.dynamodb.repository;

import com.sesacthon.infra.dynamodb.Image;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface SpringDataDynamoImageRepository extends CrudRepository<Image, String> {

}
