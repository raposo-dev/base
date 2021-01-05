package com.base.base.resource;

import static org.mockito.Mockito.verify;

import com.base.base.service.BaseService;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ScheduledTasksTest {

  @InjectMocks ScheduledTasks scheduledTasks;

  @Mock
  BaseService baseService;

  @Test
  public void verifyRunGetContractsIsCalled() throws IOException{
    scheduledTasks.runGetContracts();

    verify(baseService).insertContracts();
  }

  @Test
  public void verifyUpdateContractDetailsIsCalled() throws IOException {
    scheduledTasks.runUpdateContractDetails();

    verify(baseService).insertContractDetails();
  }
}
