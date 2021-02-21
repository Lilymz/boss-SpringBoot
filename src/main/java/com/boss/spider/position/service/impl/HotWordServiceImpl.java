package com.boss.spider.position.service.impl;

import com.boss.spider.position.dao.HotWordMapper;
import com.boss.spider.position.entity.HotWord;
import com.boss.spider.position.service.HotWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/23-20:21
 */
@Service
public class HotWordServiceImpl implements HotWordService {

    @Autowired
    private HotWordMapper hotWordMapper;
    @Override
    public int insertHotWordService(HotWord hotWord) {
        if (hotWord!=null&&hotWord.getHotWord()!=null){
            try {
                int effectROW=hotWordMapper.insertHotWord(hotWord);
                if(effectROW>0){
                    System.out.println("成功插入热词数据条数："+effectROW+" 条");
                    return 1;
                }
                System.out.println("插入失败：请检查热词数据是否符合规范");
                return -1;
            }catch (Exception e){
                System.out.println("hotWord插入出错："+e.getMessage());
            }
        }
        return -1;
    }

    @Override
    public List<HotWord> findHotWordService() {
        //获取查询结果
       try {
           List<HotWord> result=hotWordMapper.findHotWordALL();
           if (result!=null&&result.size()>0){
               System.out.println("成功查询到数据条数："+result.size());
               return result;
           }
           System.out.println("插入失败：数据库获取无数据");
           return null;
       }catch (Exception e){
           System.out.println("插入失败："+e.getMessage());
           return null;
       }
    }

    @Override
    public int deleteHotWordService(Integer id) {
        if (id!=null&&!id.equals("")){
            try {
                int effectROW=hotWordMapper.deleteHotWordById(id);
                if(effectROW>0){
                    System.out.println("成功删除热词数据条数："+effectROW+" 条");
                    return 1;
                }
                System.out.println("插入失败：请检查id是否符合规范");
                return -1;
            }catch (Exception e){
                System.out.println("hotWord插入出错："+e.getMessage());
            }
        }
        return -1;
    }
}
