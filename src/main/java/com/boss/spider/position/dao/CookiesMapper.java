package com.boss.spider.position.dao;

import com.boss.spider.position.entity.Cookies;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/19-17:26
 */
public interface CookiesMapper {
    @Insert({"INSERT INTO tb_cookies(cookie_id,cookie_value) " +
            "VALUES(null,#{cookieValue})"})
    @Options(useGeneratedKeys = true,keyColumn = "cookie_id",keyProperty = "cookieId")
    int insertCookies(Cookies cookies);
    @Select({"SELECT cookie_id,cookie_value FROM tb_cookies limit 31"})
    List<Cookies> findALL();
    @Delete({"DELETE FROM tb_cookies WHERE cookie_id=#{id}"})
    int DeleteCookie(Integer id);
}
