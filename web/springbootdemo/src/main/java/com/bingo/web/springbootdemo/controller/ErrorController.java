package com.bingo.web.springbootdemo.controller;

import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class ErrorController {

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public String notFound(ResourceNotFoundException ex) {
        return "NOT FOUND";
    }

}