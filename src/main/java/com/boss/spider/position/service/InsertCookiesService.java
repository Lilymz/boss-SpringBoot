package com.boss.spider.position.service;

import com.boss.spider.position.entity.Cookies;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/19-18:48
 */
public interface InsertCookiesService {
    int insertCookiesByTXT();
    List<Cookies> findALL();
    int DeleteById(Integer id);
}
