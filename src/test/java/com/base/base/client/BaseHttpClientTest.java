package com.base.base.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BaseHttpClientTest {

  private static final String MOCKED_RESP = "<html>\nmockedline\n</html>";

  @InjectMocks
  BaseHttpClientImpl baseHttpClient;

  @Test
  public void verifyGetBaseResponseBufferedReader() throws IOException {
    URL url = mock(URL.class);
    HttpURLConnection huc = mock(HttpURLConnection.class);
    when(url.openConnection()).thenReturn(huc);
    when(huc.getInputStream()).thenReturn(inputStreamReaderFromString(MOCKED_RESP));

    BufferedReader resp = baseHttpClient.getBaseResponseBufferedReader(url);
    assertEquals(resp.readLine(), "<html>");
    assertEquals(resp.readLine(), "mockedline");
    assertEquals(resp.readLine(), "</html>");
  }

  @Test
  public void verifyGetBaseResponseBufferedReaderWithRange() throws IOException {
    URL url = mock(URL.class);
    HttpURLConnection huc = mock(HttpURLConnection.class);
    when(url.openConnection()).thenReturn(huc);
    when(huc.getInputStream()).thenReturn(inputStreamReaderFromString(MOCKED_RESP));

    BufferedReader resp = baseHttpClient.getBaseResponseBufferedReader(url,1, 10);

    assertEquals(resp.readLine(), "<html>");
    assertEquals(resp.readLine(), "mockedline");
    assertEquals(resp.readLine(), "</html>");
  }



  private InputStream inputStreamReaderFromString(String string) throws IOException {
    return IOUtils.toInputStream(string, "UTF-8");
  }
}
