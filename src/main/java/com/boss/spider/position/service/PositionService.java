package com.boss.spider.position.service;

import com.boss.spider.position.entity.Position;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/23-20:47
 */
public interface PositionService {
    //插入抓取的职位信息
    int insertPositionService(Position position);
    //模糊查询:根据自己的标题进行模糊查询
    List<Position> findPositionLikeByTitle(String keyWord);
    List<Position> findPositionLikeByCity(Position position);
    List<Position> findPositionForPoint(Position position);
}
