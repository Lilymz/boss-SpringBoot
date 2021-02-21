package com.boss.spider.position.service.impl;

import com.boss.spider.position.dao.SalaryViewMapper;
import com.boss.spider.position.entity.HotWord;
import com.boss.spider.position.entity.SalaryView;
import com.boss.spider.position.service.SalaryViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/23-20:39
 */
@Service
public class SalaryViewServiceImpl implements SalaryViewService {
    @Autowired
    private SalaryViewMapper salaryViewMapper;
    @Override
    public int insertSalaryViewService(SalaryView salaryView) {
        if (salaryView!=null&&!salaryView.getSalaryvAvgrange().equals("")){
            try {
                int effectROW=salaryViewMapper.insertSalaryView(salaryView);
                if(effectROW>0){
                    System.out.println("成功插入工资视图数据条数："+effectROW+" 条");
                    return 1;
                }
                System.out.println("插入失败：请检查工资视图数据是否符合规范");
                return -1;
            }catch (Exception e){
                System.out.println("salaryView插入出错："+e.getMessage());
                return -1;
            }
        }
        return 0;
    }

    @Override
    public List<SalaryView> findSalaryViewALL() {
        //获取查询结果
        try {
            List<SalaryView> result=salaryViewMapper.findALL();
            if (result!=null&&result.size()>0){
                System.out.println("成功查询到工资视图数据条数："+result.size());
                return result;
            }
            System.out.println("插入失败：数据库未获取工资视图数据");
            return null;
        }catch (Exception e){
            System.out.println("插入失败："+e.getMessage());
            return null;
        }
    }
}
