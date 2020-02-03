package com.dck.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
//  显示主页
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
