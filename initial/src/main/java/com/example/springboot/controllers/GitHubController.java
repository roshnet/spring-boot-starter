package com.example.springboot.controllers;

import com.example.springboot.Utils;

import org.json.JSONObject;
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
      String response = Utils.httpGet(endpoint).toString();
      JSONObject jsonResponse = Utils.parseJSON(response);

      String login = jsonResponse.getString("login");
      Object followers = jsonResponse.getInt("followers");
      return String.format("%s has %s followers", login, followers);
    } catch (Exception e) {
      return e.toString();
    }
  }

}
