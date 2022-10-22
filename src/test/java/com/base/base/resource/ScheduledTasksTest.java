package com.base.base.resource;

import com.base.base.service.BaseIngestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ScheduledTasksTest {

	@InjectMocks
	ScheduledTasks scheduledTasks;

	@Mock
	BaseIngestionService baseIngestionService;

	@Test
	public void verifyRunGetContractsIsCalled() throws IOException {
		scheduledTasks.runGetContracts();

		verify(baseIngestionService).insertContracts();
	}

	@Test
	public void verifyUpdateContractDetailsIsCalled() throws IOException {
		scheduledTasks.runUpdateContractDetails();

		verify(baseIngestionService).insertContractDetails();
	}
}
