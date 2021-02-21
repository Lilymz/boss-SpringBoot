package com.boss.spider.position.dao;

import com.boss.spider.position.entity.SalaryView;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/23-19:57
 */
public interface SalaryViewMapper {
    @Insert({"INSERT INTO tb_salaryview(salaryv_id,salaryv_avgrange,salaryv_area) VALUES(null,#{salaryvAvgrange},#{salaryvArea})"})
    int insertSalaryView(SalaryView salaryView);
    @Select({"SELECT salaryv_id,salaryv_avgrange,salaryv_area FROM tb_salaryview"})
    List<SalaryView> findALL();
}
