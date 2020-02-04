package com.dck.community.provider;

import com.alibaba.fastjson.JSON;
import com.dck.community.dto.AccessTokenDTO;
import com.dck.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToke(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string=response.body().string();
                String token=string.split("&")[0].split("=")[1];
                System.out.println(token);
                return token;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
//    用户信息接口https://api.github.com/user?access_token=14e7a0782f416f606279b108c9da6aabe4ccb82d
        public GithubUser getUser(String accessToken){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String string=response.body().string();
                GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
                return githubUser;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

}
