package com.example.springboot.controllers;

import java.util.HashMap;
import java.util.Map;

import com.example.springboot.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubController {

  @GetMapping(value="/github/{username}", produces=MediaType.APPLICATION_JSON_VALUE)
  public Object index(@PathVariable("username") String username) {
    String endpoint = String.format("https://api.github.com/users/%s", username);
    try {
      Object response = Utils.httpGet(endpoint);
      return response;
    } catch (Exception e) {
      return e.toString();
    }
  }

  @GetMapping("/github/{username}/followers")
  public Object getFollowers(@PathVariable("username") String username) {
    String endpoint = String.format("https://api.github.com/users/%s", username);
    try {
      Object response = Utils.httpGet(endpoint);

      // [todo] Find out why this isn't working
      // Map<String, String> map = Arrays.stream(responseString.split(","))
      // .map(entry -> entry.split(":"))
      // .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));

      String responseString = response.toString();
      Map<String, String> mapper = new ObjectMapper().readValue(responseString, HashMap.class);

      String login = mapper.get("login");
      Object followers = mapper.get("followers");
      return String.format("%s has %s followers", login, followers);
    } catch (Exception e) {
      return e.toString();
    }
  }

}
