package com.boss.spider.position.service;

import com.boss.spider.position.entity.HotWord;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/23-20:18
 */
public interface HotWordService {
    //插入热词
    int insertHotWordService(HotWord hotWord);
    //查询出所有热词
    List<HotWord> findHotWordService();
    //根据id删除热词
    int deleteHotWordService(Integer id);
}
