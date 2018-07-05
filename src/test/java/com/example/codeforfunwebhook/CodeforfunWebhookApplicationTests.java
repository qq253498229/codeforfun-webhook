package com.example.codeforfunwebhook;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

public class CodeforfunWebhookApplicationTests {
  private OkHttpClient client = new OkHttpClient();

  @Test
  public void contextLoads() throws IOException {
    String url = "http://jenkins.codeforfun.cn/job/codeforfun-webhooks/build?token=jfdlaskjfljwerijoqweruq";
    String s = get(url);
    System.out.println(s);


  }

  public String get(String url) throws IOException {
    Request request = new Request.Builder()
            .url(url)
            .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }
}
