package com.zhiyou.bd14.bi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstTest {

    @RequestMapping("/hello")
    public String helloPage(Model model){
        model.addAttribute("dataFromBack","后台传来的数据");
        return "hello";
    }
}
