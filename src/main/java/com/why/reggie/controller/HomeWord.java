package com.why.reggie.controller;

import com.why.reggie.common.R;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
class HomeWord {
    public static final String API_KEY = "7O4RPz6dRqwM5yjgs1iOIV5r";
    public static final String SECRET_KEY = "AxcxgIcdxNyTLX1Ou3FtZ8E7npnzOjc6";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();


    @GetMapping("/homeword/text/{ocr}")
    public R<String[]> main(@PathVariable String ocr) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/json");
        System.out.println("{\"text\":\"" + ocr + "\"}");
        RequestBody body = RequestBody.create(mediaType, "{\"text\":\"" + ocr + "\"}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/nlp/v1/poem?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String str = response.body().string();
        String string = str.replaceAll("\\s*", "").replaceAll("[^(\u4e00-\u9fa5)]", "");
        char[] chars = str.replaceAll("\\s*", "").replaceAll("[^(\u4e00-\u9fa5)]", "").toCharArray();


        int len = chars.length / 4;
        String[] string1 = new String[4];

        for (int i = 0; i < 4; i++) {
            string1[i] = string.substring(i * len, (i + 1) * len);
        }

        for (String i : string1) {
            System.out.println(i);
        }
        return R.success(string1);
    }


    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }

}