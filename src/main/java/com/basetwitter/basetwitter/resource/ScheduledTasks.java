package com.basetwitter.basetwitter.resource;

import com.basetwitter.basetwitter.service.BaseService;
import java.io.IOException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  private final BaseService baseService;

  public ScheduledTasks(BaseService baseService) {
    this.baseService = baseService;
  }

  @Async
  @Scheduled(fixedDelay = 100000)
  public void runGetContracts() throws IOException {

    baseService.insertContracts();
  }

  @Async
  @Scheduled(fixedDelay = 100000)
  public void runUpdateContractDetails() {
    baseService.insertContractDetails();
  }
}