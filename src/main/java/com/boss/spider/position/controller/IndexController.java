package com.boss.spider.position.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author minnan
 * @date 2021/1/23-22:07
 */
@Controller
public class IndexController {
    @GetMapping("Index")
    public String spiderIndex(){return "index";}
}
