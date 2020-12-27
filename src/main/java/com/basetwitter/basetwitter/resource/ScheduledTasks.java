package com.basetwitter.basetwitter.resource;

import com.basetwitter.basetwitter.service.FetchBaseService;
import java.io.IOException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  private final FetchBaseService fetchBaseService;

  public ScheduledTasks(FetchBaseService fetchBaseService) {
    this.fetchBaseService = fetchBaseService;
  }
  @Async
  @Scheduled(fixedDelay = 100000)
  public void runFetchBase() throws IOException {

     fetchBaseService.insertContracts();
  }
  @Async
  @Scheduled(fixedDelay =  100000)
  public void runUpdateContractDetails() {
    fetchBaseService.insertContractDetails();
  }
}
