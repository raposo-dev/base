package com.base.base.client;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

        BufferedReader resp = baseHttpClient.getBaseResponseBufferedReader(url, 1, 10);

        assertEquals(resp.readLine(), "<html>");
        assertEquals(resp.readLine(), "mockedline");
        assertEquals(resp.readLine(), "</html>");
    }

    @Test
    public void verifyHttpPostREMOVE() throws IOException {
        HttpClient client = HttpClientBuilder.create()
                .build();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("type", "search_contratos"));
        params.add(new BasicNameValuePair("query", ""));
        params.add(new BasicNameValuePair("sort", "-publicationDate"));
        params.add(new BasicNameValuePair("page", "0"));
        params.add(new BasicNameValuePair("size", "25"));

        HttpUriRequest request = RequestBuilder.post()
                .setUri("https://www.base.gov.pt/Base4/pt/resultados/")
                .setEntity(new UrlEncodedFormEntity(params))
                .build();

        HttpResponse httpResponse = client.execute(request);


        HttpEntity entity = httpResponse.getEntity();
        String result = EntityUtils.toString(entity);

    }


    private InputStream inputStreamReaderFromString(String string) throws IOException {
        return IOUtils.toInputStream(string, "UTF-8");
    }
}
