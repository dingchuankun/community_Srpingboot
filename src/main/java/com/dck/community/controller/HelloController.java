package com.dck.community.controller;

import com.dck.community.dto.PageDTO;
import com.dck.community.dto.QuestionDTO;
import com.dck.community.mapper.QuestionMapper;
import com.dck.community.mapper.UserMapper;
import com.dck.community.model.Question;
import com.dck.community.model.User;
import com.dck.community.service.QuestionService;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HelloController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

//  显示主页
    @GetMapping("/")
    public String index(HttpServletRequest request
                        ,Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "page",defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length !=0){
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();
                User user =userMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user",user);
                    };
                break;
                };

            }
        }
        PageDTO questionList=questionService.questionlist(page,size);

        model.addAttribute("questions",questionList);
        System.out.println("测试："+questionList);
        return "index";

    }
}
