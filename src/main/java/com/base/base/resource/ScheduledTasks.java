package com.base.base.resource;

import com.base.base.service.BaseIngestionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScheduledTasks {

	private final BaseIngestionService baseIngestionService;

	public ScheduledTasks(BaseIngestionService baseIngestionService) {
		this.baseIngestionService = baseIngestionService;
	}

	@Async
	@Scheduled(fixedDelay = 100000)
	public void runGetContracts() throws IOException {

		baseIngestionService.insertContracts();
	}

	@Async
	@Scheduled(fixedDelay = 100000, initialDelay = 60000)
	public void runUpdateContractDetails() {
		baseIngestionService.insertContractDetails();
	}
}
