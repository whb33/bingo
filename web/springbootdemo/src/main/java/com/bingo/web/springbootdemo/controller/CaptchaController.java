package com.bingo.web.springbootdemo.controller;

import com.bingo.web.springbootdemo.utils.captcha.Captcha;
import com.bingo.web.springbootdemo.utils.captcha.GifCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class CaptchaController extends BaseController {

    @GetMapping(value = "captcha1")
    public void getGifCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");
            /*
             * gif格式动画验证码
             * 宽，高，位数
             */
            Captcha captcha = new GifCaptcha(146, 33, 4);
            //输出
            captcha.out(response.getOutputStream());
            Session session = SecurityUtils.getSubject().getSession();
            //存入Session
            session.setAttribute("session_captcha", captcha.text().toLowerCase());
        } catch (Exception e) {
            log.error("获取验证码异常：" + e.getMessage(), e);
        }
    }
}
