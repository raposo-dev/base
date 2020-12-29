package com.basetwitter.basetwitter.resource;

import static org.mockito.Mockito.verify;

import com.basetwitter.basetwitter.service.BaseService;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledTasksTest {


  @InjectMocks ScheduledTasks scheduledTasks;

  @Mock
  BaseService baseService;

  @Test
  public void verifyRunGetContractsIsCalled() throws IOException {
    scheduledTasks.runGetContracts();

    verify(baseService).insertContracts();
  }

  @Test
  public void verifyUpdateContractDetailsIsCalled() throws IOException {
    scheduledTasks.runUpdateContractDetails();

    verify(baseService).insertContractDetails();
  }
}
