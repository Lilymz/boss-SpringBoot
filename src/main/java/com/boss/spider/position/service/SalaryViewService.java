package com.boss.spider.position.service;

import com.boss.spider.position.entity.SalaryView;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/23-20:36
 */
public interface SalaryViewService {
    //插入工资视图
    int insertSalaryViewService(SalaryView salaryView);
    //查询工资视图
    List<SalaryView> findSalaryViewALL();
}
