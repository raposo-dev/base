package com.base.base.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class BaseHttpClientImpl implements BaseHttpClient {

  @Override
  public Integer getNumberOfContracts(URL url) throws IOException {
    BufferedReader rd = getBaseResponseBufferedReader(url);

    Integer result;
    result = getNumberContractsFromSpan(rd);
    return result;
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
