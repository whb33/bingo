package com.bingo.web.springbootdemo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}