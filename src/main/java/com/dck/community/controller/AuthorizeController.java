package com.dck.community.controller;

import com.dck.community.dto.AccessTokenDTO;
import com.dck.community.dto.GithubUser;
import com.dck.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("Iv1.8f73bbce1121d66b");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret("3b1347ca5d8ed950870ccdd77953de7d1afa1106");
        String accessToken = githubProvider.getAccessToke(accessTokenDTO);
        GithubUser usewr= githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}
