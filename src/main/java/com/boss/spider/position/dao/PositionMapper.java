package com.boss.spider.position.dao;

import com.boss.spider.position.entity.Position;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/19-15:48
 */
public interface PositionMapper {

    @Insert({"INSERT INTO tb_position(position_id,position_title,position_salary_range,position_link,position_require,position_company_SIZE,position_build_time,position_work_time,create_time,position_area,position_city,company_name)   " +
            "VALUES(null,#{positionTitle},#{positionSalaryRange},#{positionLink},#{positionRequire},#{positionCompanySIZE},#{positionBuildTime},#{positionWorkTime},#{createTime},#{positionArea},#{positionCity},#{companyName})"})
    @Options(useGeneratedKeys = true,keyColumn = "position_id",keyProperty = "positionId")
    int insertPosition(Position position);

    @Select({"SELECT position_id,position_title,position_salary_range,position_link,position_require,position_company_SIZE,position_build_time,position_work_time,create_time,position_area,position_city,company_name " +
            "FROM tb_position WHERE position_title like concat('%',#{positionTitle},'%') and position_city=#{positionCity}"})
    List<Position> findPositionLikeCity(Position position);
    @Select({"SELECT position_area,position_city,company_name " +
            "FROM tb_position WHERE position_title like concat('%',#{positionTitle},'%') and position_city=#{positionCity} ORDER BY create_time DESC"})
    List<Position> findPositionForPoint(Position position);
    @Select({"SELECT position_id,position_title,position_salary_range,position_link,position_require,position_company_SIZE,position_build_time,position_work_time,create_time,position_area,position_city,company_name " +
            "FROM tb_position WHERE position_title like concat('%',#{keyWord},'%') ORDER BY create_time DESC"})
    List<Position> findPositionLike(String keyWord);
}
