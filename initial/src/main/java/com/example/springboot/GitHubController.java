package com.example.springboot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubController {

  static void log(String arg) {
    System.out.println(arg);
  }

  @GetMapping(value="/github/{username}", produces=MediaType.APPLICATION_JSON_VALUE)
  public Object index(@PathVariable("username") String username) {
    String endpoint = String.format("https://api.github.com/users/%s", username);
    StringBuilder fullResponseBuilder = new StringBuilder();
    try {
      URL url = new URL(endpoint);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
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

  @GetMapping("/github/{username}/followers")
  public Object getFollowers(@PathVariable("username") String username) {
    String endpoint = String.format("https://api.github.com/users/%s", username);
    StringBuilder fullResponseBuilder = new StringBuilder();
    try {
      URL url = new URL(endpoint);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
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

      // [todo] Find out why this isn't working
      // Map<String, String> map = Arrays.stream(fullResponseBuilder.toString().split(","))
      // .map(entry -> entry.split(":"))
      // .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));

      String jsonString = fullResponseBuilder.toString();
      Map<String, String> mapper = new ObjectMapper().readValue(jsonString, HashMap.class);

      String login = mapper.get("login");
      Object followers = mapper.get("followers");
      return String.format("%s has %s followers", login, followers);
    } catch (Exception e) {
      e.printStackTrace();
      return "Failed to get info, something went wrong.";
    }
  }

}
