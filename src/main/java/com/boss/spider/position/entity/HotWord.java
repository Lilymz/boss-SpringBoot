package com.boss.spider.position.entity;

/**
 * @author minnan
 * @date 2021/1/13-20:00
 */
public class HotWord {
    private Integer hotId;
    private String  hotWord;
    public HotWord(){}
    public HotWord(Integer hotId, String hotWord) {
        this.hotId = hotId;
        this.hotWord = hotWord;
    }

    public Integer getHotId() {
        return hotId;
    }

    public void setHotId(Integer hotId) {
        this.hotId = hotId;
    }

    public String getHotWord() {
        return hotWord;
    }

    public void setHotWord(String hotWord) {
        this.hotWord = hotWord;
    }

    @Override
    public String toString() {
        return "HotWord{" +
                "hotId=" + hotId +
                ", hotWord='" + hotWord + '\'' +
                '}';
    }
}
