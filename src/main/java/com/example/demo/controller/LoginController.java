package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class LoginController {


    @GetMapping("login")
    public String Login(){
        return "login/login.html";
    }

//    @GetMapping("/login-success")
//    public String loginSuccess(Model model) {
//        model.addAttribute("registrationSuccess", true);
//        return "login/login.html";
//    }

    @GetMapping("/login-success")
    public String loginSuccess() {
        return "redirect:/Home/";
    }
}
