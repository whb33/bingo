package com.bingo.web.springbootdemo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bingo.web.springbootdemo.entity.About;
import com.bingo.web.springbootdemo.mapper.AboutMapper;
import com.bingo.web.springbootdemo.service.AboutService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    private AboutMapper aboutMapper;

    @Override
    public String getAboutList(String page, String rows, String title) throws Exception {
        int count = aboutMapper.selectCount(new EntityWrapper<About>().like("TITLE", title));
        List<About> list = aboutMapper.selectPage(
                new Page<About>(Integer.parseInt(page), Integer.parseInt(rows)),
                new EntityWrapper<About>().like("TITLE", title)
        );
        JSONObject obj = new JSONObject();
        obj.put("rows", list);
        obj.put("total", count);
        return obj.toString();
    }

    @Override
    public About getAboutById(String id) {
        return aboutMapper.selectById(id);
    }

}
