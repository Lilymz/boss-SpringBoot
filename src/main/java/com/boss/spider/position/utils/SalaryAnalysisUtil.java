package com.boss.spider.position.utils;

import com.boss.spider.position.entity.EnchartsSalary;
import com.boss.spider.position.entity.Position;
import com.boss.spider.position.mongodb.InsertPositionMongo;
import com.boss.spider.position.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minnan
 * @date 2021/1/28-18:36
 */
@Component
public class SalaryAnalysisUtil {
    @Autowired
    private InsertPositionMongo insertPositionMongo;
        public List<EnchartsSalary> AvgSalaryByArea(String keyWord){
            System.out.println(insertPositionMongo);
            List<EnchartsSalary> enchartsSalary=new ArrayList<>();
            if (keyWord!=null){
                //根据关键词获取当前岗位中不为空的地区
                List<String> _cityAll=insertPositionMongo.mysqlFindCity(keyWord);
                Position position=new Position();
                position.setPositionTitle(keyWord);
                if(_cityAll!=null&&_cityAll.size()>0){
                    for (String city:_cityAll) {
                        position.setPositionCity(city);
                       EnchartsSalary enchartsSalary1= insertPositionMongo.mysqlFindPositionByKeyWordAndCity(position);
                        if (enchartsSalary1!=null)
                            enchartsSalary.add(enchartsSalary1);
                    }
                    return enchartsSalary;
                }
             }
            return null;
        }
}
