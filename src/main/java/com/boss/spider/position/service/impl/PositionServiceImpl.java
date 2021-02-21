package com.boss.spider.position.service.impl;

import com.boss.spider.position.dao.PositionMapper;
import com.boss.spider.position.entity.Position;
import com.boss.spider.position.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/23-20:56
 */
@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionMapper positionMapper;
    @Override
    public int insertPositionService(Position position) {
        if (position!=null&&!position.getPositionTitle().equals("")){
            try {
                int effectROW=positionMapper.insertPosition(position);
                if(effectROW>0){
                    System.out.println("成功插入职位爬取数据条数："+effectROW+" 条");
                    return 1;
                }
                System.out.println("插入失败：请检查职位数据是否符合规范");
                return -1;
            }catch (Exception e){
                System.out.println("Position插入出错："+e.getMessage());
                return -1;
            }
        }
        return 0;
    }

    @Override
    public List<Position> findPositionLikeByTitle(String keyWord) {
        //keyWord的非空判断
        try {
            if (keyWord!=null&&!keyWord.equals("")){
                List<Position> result=positionMapper.findPositionLike(keyWord);
                if (result!=null&&result.size()>0){
                    System.out.println("查询爬取职位成功，共查取到："+result.size());
                    return result;
                }
                System.out.println("查询爬取职位失败：未拥有该信息或keyWord出错！");
                return null;
            }
        }catch (Exception e){
            System.out.println("查询职位爬取出错："+e.getMessage());
        }
        return null;
    }

    @Override
    public List<Position> findPositionLikeByCity(Position position) {
        //keyWord的非空判断
        try {
            if (position!=null){
                List<Position> result=positionMapper.findPositionLikeCity(position);
                if (result!=null&&result.size()>0){
                    System.out.println("查询爬取职位成功，共查取到："+result.size());
                    return result;
                }
                System.out.println("查询爬取职位失败：未拥有该信息或keyWord出错！");
                return null;
            }
        }catch (Exception e){
            System.out.println("查询职位爬取出错："+e.getMessage());
        }
        return null;
    }

    @Override
    public List<Position> findPositionForPoint(Position position) {
        //keyWord的非空判断
        try {
            if (position!=null){
                List<Position> result=positionMapper.findPositionForPoint(position);
                if (result!=null&&result.size()>0){
                    System.out.println("查询职位成功，共查取到："+result.size());
                    return result;
                }
                System.out.println("查询职位失败：未拥有该信息或keyWord出错！");
                return null;
            }
        }catch (Exception e){
            System.out.println("查询出错："+e.getMessage());
        }
        return null;
    }
}
