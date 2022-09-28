package com.al3xkras.web_notebook_api.user_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
