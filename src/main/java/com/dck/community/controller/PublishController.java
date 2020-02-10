package com.dck.community.controller;

import com.dck.community.mapper.QuestionMapper;
import com.dck.community.mapper.UserMapper;
import com.dck.community.model.Question;
import com.dck.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(Model model){
        Question question=new Question();
        model.addAttribute("question",question);
        return "publish";
    }

    @PostMapping("/publish")
    public String dopublish(Question question, HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (question != null){
            model.addAttribute("question",question);
            System.out.println("model里的"+question);
        }
        if(question.getTitle()==null||question.getTitle()==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(question.getDescription()==null||question.getDescription()==""){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if(question.getTag()==null||question.getTag()==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        if(cookies != null && cookies.length !=0){
        for(Cookie cookie:cookies ){
            if (cookie.getName().equals("token")) {
                String token=cookie.getValue();
                user= userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        }
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        question.setCommentCount(0);
        question.setViewCount(0);
        question.setLikeCount(0);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        System.out.println(question);
        return  "redirect:/";
    }
}
