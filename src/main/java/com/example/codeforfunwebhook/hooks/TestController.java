package com.example.codeforfunwebhook.hooks;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author wangbin
 */
@RestController
public class TestController {
  @Value("hooks.prefix")
  private String hooksPrefix;

  @Resource
  private HooksRepository hooksRepository;

  @PostMapping("/")
  public ResponseEntity post(@RequestParam("token") String token) throws IOException {
    List<Hooks> all = hooksRepository.findAll();
    if (ObjectUtils.isEmpty(all)) {
      return badRequest().build();
    }
    Hooks hooks = all.get(0);
    String url = "http://" + hooks.getUsername() + ":" + hooks.getPassword() + "@" + hooksPrefix + token;
    run(url);
    return ok().build();
  }

  OkHttpClient client = new OkHttpClient();

  private String run(String url) throws IOException {
    Request request = new Request.Builder()
            .url(url)
            .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }
}
