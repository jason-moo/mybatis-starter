package com.demo.controller;

import com.demo.entity.Ds;
import com.demo.entity.News;
import com.demo.mapper.db1.NewsMapper;
import com.demo.mapper.db2.DsMapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xuezn
 * @Date 2018年12月11日 17:03:41
 */
@RestController
public class TestController {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private DsMapper dsMapper;

    @RequestMapping("")
    public Object test(){
        News news = newsMapper.selectById(1);
        System.out.println(news);
        Ds ds = dsMapper.selectById(1);
        System.out.println(ds);
        return ds;
    }
}
