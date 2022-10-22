package com.base.base.client;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        for (String s : Arrays.asList("<html>", "mockedline", "</html>")) {
            assertEquals(resp.readLine(), s);
        }
    }

    @Test
    public void verifyGetBaseResponseBufferedReaderWithRange() throws IOException {
        URL url = mock(URL.class);
        HttpURLConnection huc = mock(HttpURLConnection.class);
        when(url.openConnection()).thenReturn(huc);
        when(huc.getInputStream()).thenReturn(inputStreamReaderFromString(MOCKED_RESP));

        BufferedReader resp = baseHttpClient.getBaseResponseBufferedReader(url, 1, 10);

        for (String s : Arrays.asList("<html>", "mockedline", "</html>")) {
            assertEquals(resp.readLine(), s);
        }
    }

    private InputStream inputStreamReaderFromString(String string) throws IOException {
        return IOUtils.toInputStream(string, "UTF-8");
    }
}
