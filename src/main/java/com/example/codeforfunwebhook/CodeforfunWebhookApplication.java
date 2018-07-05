package com.example.codeforfunwebhook;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author wangbin
 */
@SpringBootApplication
@RestController
public class CodeforfunWebhookApplication {

  @Value("hooks.prefix")
  private String hooksPrefix;

  @PostMapping("/{projectName}/{token}")
  public ResponseEntity post(@PathVariable("projectName") String projectName, @PathVariable("token") String token) throws IOException {
    if (ObjectUtils.isEmpty(projectName) || ObjectUtils.isEmpty(token)) {
      return badRequest().build();
    }
    String url = "http://" + hooksPrefix + "/job/" + projectName + "/build?token=" + token;
    run(url);
    return ok().build();
  }

  private OkHttpClient client = new OkHttpClient();

  private String run(String url) throws IOException {
    Request request = new Request.Builder()
            .url(url)
            .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  public static void main(String[] args) {
    SpringApplication.run(CodeforfunWebhookApplication.class, args);
  }
}
