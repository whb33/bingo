package com.bingo.web.springbootdemo.controller;

import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping(value = "/easyui")
public class ViewController extends BaseController {

    @GetMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "/login";
    }

    @GetMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "/index";
    }


}
