package org.laorui_out.habit_former.api.service;
import okhttp3.*;
import okio.BufferedSource;
import org.json.JSONObject;
import org.laorui_out.habit_former.api.entity.ClientParam;
import org.laorui_out.habit_former.api.entity.PlaningResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.laorui_out.habit_former.api.util.ResponseReader;
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
        this.client = new OkHttpClient.Builder().readTimeout(600, TimeUnit.SECONDS).build();
        okhttp3.RequestBody body = okhttp3.RequestBody.Companion.create(mediaType, messages);
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(clientParam.getModelUrl())).newBuilder();
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
                String responseBody = Objects.requireNonNull(response.body()).string();
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

    public SseEmitter getResponseStream(String theme, Date startDate) {
        SseEmitter emitter = new SseEmitter();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
                    try (Response response = client.newCall(request).execute()) {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            BufferedSource bufferedSource = Objects.requireNonNull(responseBody).source();
                            StringBuilder res = new StringBuilder();
                            String buf;
                            String result;
                            int index,ptr_start=-1,ptr_end=-1;
                            JSONObject jsonObject;
                            while (bufferedSource.isOpen() && !bufferedSource.exhausted()) {
                                buf = bufferedSource.readUtf8LineStrict();
                                if(buf.isEmpty())
                                    continue;
                                System.out.println("buf:"+buf);
                                index=buf.indexOf('{');
                                buf=buf.substring(index);
                                jsonObject=new JSONObject(buf);
                                result=jsonObject.getString("result");
                                res.append(result);//字符串缓冲区
                                //System.out.println(" res:"+res);
                                if(ptr_start==-1)
                                    ptr_start= res.toString().indexOf('{');
                                if(ptr_end==-1)
                                    ptr_end= res.toString().indexOf('}');
                                //System.out.println("start:"+ptr_start+" end:"+ptr_end);
                                if(ptr_start!=-1&&ptr_end!=-1){
                                    System.out.println("------starting to send!\nstart:"+ptr_start+" end:"+ptr_end+" res:"+res);
                                    switch (theme) {
                                        case Constants.FIT_PLAN_TYPE ->
                                                emitter.send(ResponseReader.readFPResponse(res.substring(ptr_start, ptr_end + 1),startDate));
                                        case Constants.STUDY_PLAN_TYPE ->
                                                emitter.send(ResponseReader.readSPResponse(res.substring(ptr_start, ptr_end + 1),startDate));
                                        default ->
                                                emitter.send(ResponseReader.readDPResponse(res.substring(ptr_start, ptr_end + 1),startDate));
                                    }
                                    if(res.length()==ptr_end+1)
                                        res = new StringBuilder();
                                    else res = new StringBuilder(res.substring(ptr_end + 1));
                                    ptr_start=-1;ptr_end=-1;
                                }
                            }
                            System.out.println("buf reading finished");
                            while(res.toString().indexOf('{')!=-1&& res.toString().indexOf('}')!=-1){
                                if(ptr_start==-1)
                                    ptr_start= res.toString().indexOf('{');
                                if(ptr_end==-1)
                                    ptr_end= res.toString().indexOf('}');
                                switch (theme) {
                                    case Constants.FIT_PLAN_TYPE ->
                                            emitter.send(ResponseReader.readFPResponse(res.substring(ptr_start, ptr_end + 1),startDate));
                                    case Constants.STUDY_PLAN_TYPE ->
                                            emitter.send(ResponseReader.readSPResponse(res.substring(ptr_start, ptr_end + 1),startDate));
                                    default ->
                                            emitter.send(ResponseReader.readDPResponse(res.substring(ptr_start, ptr_end + 1),startDate));
                                }
                                if(res.length()==ptr_end+1)
                                    res = new StringBuilder();
                                else res = new StringBuilder(res.substring(ptr_end + 1));
                                ptr_start=-1;ptr_end=-1;
                            }
                        } else {
                            System.err.println("Request failed: " + response.code());
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println("---emitter complete----");
                        emitter.complete();
                    }

                }
        );


        return emitter;
    }
}