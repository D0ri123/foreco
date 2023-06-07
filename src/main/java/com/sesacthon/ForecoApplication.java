package com.sesacthon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.sesacthon.foreco")
public class ForecoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ForecoApplication.class, args);
  }

}
