package com.basetwitter.basetwitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class BaseTwitterApplication {

  public static void main(String[] args) {
    SpringApplication.run(BaseTwitterApplication.class, args);
  }
}
