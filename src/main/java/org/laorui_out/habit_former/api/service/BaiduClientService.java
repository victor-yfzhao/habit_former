package org.laorui_out.habit_former.api.service;
import okhttp3.*;
import okio.BufferedSource;
import org.laorui_out.habit_former.api.entity.ClientParam;
import org.laorui_out.habit_former.api.entity.PlaningResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class BaiduClientService implements ClientService {
    private OkHttpClient client;
    private Request request;
    //待修改
    //private ClientParam clientParam;

    //设置客户端必备的参数
    //private void setClientParam(){}

    @Override
    public void init(String messages, ClientParam clientParam) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        this.client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();
        okhttp3.RequestBody body = okhttp3.RequestBody.Companion.create(mediaType, messages);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(clientParam.getModelUrl()).newBuilder();
        urlBuilder.addQueryParameter("access_token", clientParam.getAccessToken());
        String url = urlBuilder.build().toString();

        this.request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();
    }

    @Override
    public PlaningResponse getResponse() {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                PlaningResponse planingResponse = new PlaningResponse();
                planingResponse.setRaw(responseBody);
                System.out.println(responseBody);
                return planingResponse;
            } else {
                System.err.println("Request failed: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SseEmitter getResponseStream() {
        SseEmitter emitter = new SseEmitter();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
                    try (Response response = client.newCall(request).execute()) {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            BufferedSource bufferedSource = responseBody.source();
                            char tmp;
                            String res = "", buf = "";
                            while (bufferedSource.isOpen() && !bufferedSource.exhausted()) {
                                //String buf;
                                buf = bufferedSource.readUtf8LineStrict();
                                res += buf;
                                System.out.println(buf);
                                emitter.send(buf);
                                //buf="";
                            }
                            //emitter.complete();//在这里complete会报错？最后一条message发不出去。
                        } else {
                            System.err.println("Request failed: " + response.code());
                        }
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                    emitter.complete();
                }
        );
        return emitter;
    }
}