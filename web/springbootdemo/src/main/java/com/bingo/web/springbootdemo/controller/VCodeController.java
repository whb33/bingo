package com.bingo.web.springbootdemo.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Controller
@Slf4j
@RequestMapping(value = "/vcode")
public class VCodeController {

    private RandomValidateCode randomValidateCode = new RandomValidateCode();

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/random")
    public void imageServletDo(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg"); //设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache"); //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            randomValidateCode.getRandomCode(request, response);//输出图片方法
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/validate")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean success = randomValidateCode.validateCode(request);
        redisTemplate.opsForValue().set("vcode-" + request.getSession().getId(), "0", 2, TimeUnit.SECONDS);
        JSONObject ret = new JSONObject();
        ret.put("success", success);
        try {
            response.getWriter().write(ret.toString());
            response.getWriter().flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 描述：验证码生产类
     */
    private class RandomValidateCode {

        private static final String RANDOM_CODE_KEY = "RANDOM_VALIDATE_CODE_KEY"; //放到session中的key
        private Random random = new Random();
        private String randString = "0123456789";//随机产生的字符串 可放入英文字母

        private int width = 80;//图片宽
        private int height = 26;//图片高
        private int lineSize = 40;//干扰线数量
        private int stringNum = 4;//随机产生字符数量

        // 获得字体
        private Font getFont() {
            return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
        }

        // 获得颜色
        private Color getRandColor(int fc, int bc) {
            if (fc > 255)
                fc = 255;
            if (bc > 255)
                bc = 255;
            int r = fc + random.nextInt(bc - fc - 16);
            int g = fc + random.nextInt(bc - fc - 14);
            int b = fc + random.nextInt(bc - fc - 18);
            return new Color(r, g, b);
        }

        private boolean validateCode(HttpServletRequest request) {
            HttpSession session = request.getSession();
            String sCode = session.getAttribute(RANDOM_CODE_KEY).toString();
            String iCode = request.getParameter("code");
            return StringUtils.equals(sCode, iCode);
        }

        // 生成随机图片
        private void getRandomCode(HttpServletRequest request, HttpServletResponse response) {
            HttpSession session = request.getSession();
            //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics g = image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
            g.fillRect(0, 0, width, height);
            g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
            g.setColor(getRandColor(110, 133));
            //绘制干扰线
            for (int i = 0; i <= lineSize; i++) {
                drawLine(g);
            }
            //绘制随机字符
            String randomString = "";
            for (int i = 1; i <= stringNum; i++) {
                randomString = drawString(g, randomString, i);
            }
            session.removeAttribute(RANDOM_CODE_KEY);
            session.setAttribute(RANDOM_CODE_KEY, randomString);
            g.dispose();
            try {
                ImageIO.write(image, "JPEG", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 绘制字符串
        private String drawString(Graphics g, String randomString, int i) {
            g.setFont(getFont());
            g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
            String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
            randomString += rand;
            g.translate(random.nextInt(3), random.nextInt(3));
            g.drawString(rand, 13 * i, 16);
            return randomString;
        }

        // 绘制干扰线
        private void drawLine(Graphics g) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(13);
            int yl = random.nextInt(15);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 获取随机的字符
        private String getRandomString(int num) {
            return String.valueOf(randString.charAt(num));
        }

    }

}
