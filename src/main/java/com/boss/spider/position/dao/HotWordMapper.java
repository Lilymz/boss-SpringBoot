package com.boss.spider.position.dao;

import com.boss.spider.position.entity.HotWord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author minnan
 * @date 2021/1/20-20:46
 */
public interface HotWordMapper {
    @Insert({"INSERT INTO tb_hot(hot_id,hot_word) VALUES(null,#{hotWord})"})
    int insertHotWord(HotWord hotWord);
    @Select({"SELECT hot_id,hot_word FROM tb_hot"})
    List<HotWord> findHotWordALL();
    @Delete({"DELETE FROM tb_hot WHERE hot_id=#{id}"})
    int deleteHotWordById(Integer id);
}
