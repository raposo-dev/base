package com.base.base.client;

import com.base.base.models.TotalContracts;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class BaseHttpClientImpl implements BaseHttpClient {

    @Override
    public Integer getNumberOfContracts(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create()
                .build();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("type", "search_contratos"));
        params.add(new BasicNameValuePair("query", ""));
        params.add(new BasicNameValuePair("sort", "-publicationDate"));
        params.add(new BasicNameValuePair("page", "0"));
        params.add(new BasicNameValuePair("size", "25"));
        HttpUriRequest request = RequestBuilder.post()
                .setUri(url)
                .setEntity(new UrlEncodedFormEntity(params))
                .build();

        HttpResponse httpResponse = client.execute(request);

        HttpEntity entity = httpResponse.getEntity();
        String result = EntityUtils.toString(entity);

        // TO-DO: Simplify this, maybe avoid using a POJO just to extract value from the body
        ObjectMapper mapper = new ObjectMapper();
        TotalContracts totalContracts = mapper.readValue(result, TotalContracts.class);

        return totalContracts.getTotal();
    }

    @Override
    public BufferedReader getBaseResponseBufferedReader(URL url) throws IOException {
        HttpURLConnection conn = getHttpUrlConnection(url);
        return new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }

    @Override
    public BufferedReader getBaseResponseBufferedReader(URL url, int lowerRange, int upperRange)
            throws IOException {
        HttpURLConnection conn = getHttpUrlConnection(url);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("range", String.format("%s-%s", lowerRange, upperRange));

        return new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }

    private HttpURLConnection getHttpUrlConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }

    private Integer getNumberContractsFromSpan(BufferedReader rd) throws IOException {
        String res = "";
        String line;
        while ((line = rd.readLine()) != null) {
            if (line.contains("Foram encontrados")) {
                Document doc = Jsoup.parse(line);
                for (Element e : doc.select("span")) {
                    res = e.text();
                }
            }
        }
        return Integer.parseInt(res);
    }
}
