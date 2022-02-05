package com.example.springboot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Utils {

  private static Object httpRequest(String type, String endpoint) {
    StringBuilder fullResponseBuilder = new StringBuilder();
    try {
      URL url = new URL(endpoint);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod(type);
      conn.setRequestProperty("Content-Type", "application/json");
      BufferedReader response = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      StringBuffer content = new StringBuffer();
      while ((inputLine = response.readLine()) != null) {
          content.append(inputLine);
      }
      fullResponseBuilder.append(content);
      response.close();
      conn.disconnect();
      return fullResponseBuilder.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "Failed to get info, something went wrong.";
    }
  }

  public static Object httpGet(String endpoint) {
    Object resp = httpRequest("GET", endpoint);
    return resp;
  }

  public static Object httpPost(String endpoint) {
    Object resp = httpRequest("POST", endpoint);
    return resp;
  }

  public static JSONObject parseJSON(String httpBody) {
    JSONObject json = new JSONObject(httpBody);
    return json;
  }

  public static void log(String arg) {
    System.out.println(arg);
  }

}
