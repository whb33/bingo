package com.bingo.web.springbootdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ViewController extends BaseController {

    @GetMapping(value = "/home")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("business/home");
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            modelAndView.setViewName("business/login");
            return modelAndView;
        }
        return modelAndView;
    }

    @GetMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("business/login");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            modelAndView.setViewName("business/home");
            return modelAndView;
        }
        return modelAndView;
    }

}
