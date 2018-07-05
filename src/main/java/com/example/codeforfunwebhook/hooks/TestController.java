package com.example.codeforfunwebhook.hooks;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@Slf4j
public class TestController {
  @Value("${hooks.prefix}")
  private String hooksPrefix;

  @Resource
  private HooksRepository hooksRepository;

  @PostMapping("/{projectName}/{token}")
  public ResponseEntity post(@PathVariable("projectName") String projectName, @PathVariable("token") String token) throws IOException {
    log.debug("receive hooks. project name : " + projectName + ",token : " + token);
    if (ObjectUtils.isEmpty(projectName) || ObjectUtils.isEmpty(token)) {
      return badRequest().build();
    }

    List<Hooks> all = hooksRepository.findAll();
    if (CollectionUtils.isEmpty(all)) {
      return badRequest().build();
    }
    Hooks hooks = all.get(0);
    String url = "http://" + hooks.getUsername() + ":" + hooks.getPassword() + "@" + hooksPrefix + "/job/" + projectName + "/build?token=" + token;
    run(url);
    return ok().build();
  }

  private OkHttpClient client = new OkHttpClient();

  private String run(String url) throws IOException {
    log.debug("sending request, url : " + url);
    Request request = new Request.Builder()
            .url(url)
            .build();

    Response response = client.newCall(request).execute();
    String result = response.body().string();
    log.debug("request result : " + result);
    return result;
  }

}
