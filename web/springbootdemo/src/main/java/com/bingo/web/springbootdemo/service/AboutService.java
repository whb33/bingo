package com.bingo.web.springbootdemo.service;

import com.bingo.web.springbootdemo.entity.About;

public interface AboutService {

    String getAboutList(String page, String rows, String title) throws Exception;

    About getAboutById(String id);

}
