package com.basetwitter.basetwitter.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

public interface BaseHttpClient {

  Integer getNumberOfContracts(URL url) throws IOException;

  BufferedReader getBaseResponseBufferedReader(URL url) throws IOException;

  BufferedReader getBaseResponseBufferedReader(URL url, int lowerRange, int upperRange)
      throws IOException;
}
