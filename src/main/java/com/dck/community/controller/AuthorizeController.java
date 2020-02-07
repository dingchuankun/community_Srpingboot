package com.dck.community.controller;

import com.dck.community.dto.AccessTokenDTO;
import com.dck.community.dto.GithubUser;
import com.dck.community.mapper.UserMapper;
import com.dck.community.model.User;
import com.dck.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.credirect_uri}")
    private String redirect_uri;
    @Value("${github.client_secret}")
    private String client_secret;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(client_secret);
        String accessToken = githubProvider.getAccessToke(accessTokenDTO);
        GithubUser githubUser= githubProvider.getUser(accessToken);
        if(githubUser != null){
            //登陆成功
            User user=new User();
            final String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            System.out.println("输出user："+user);
            userMapper.insert(user);
            //写入cookie
            response.addCookie(new Cookie("token",token));
            System.out.println(githubUser);
            return  "redirect:/";
        }else {
            //登陆失败
            return  "redirect:/";
        }
    }
}
