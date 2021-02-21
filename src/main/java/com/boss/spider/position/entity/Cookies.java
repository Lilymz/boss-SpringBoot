package com.boss.spider.position.entity;

/**
 * @author minnan
 * @date 2021/1/19-17:19
 */
public class Cookies {
    private Integer cookieId;
    private String cookieValue;
    public Cookies(){}
    public Integer getCookieId() {
        return cookieId;
    }

    public void setCookieId(Integer cookieId) {
        this.cookieId = cookieId;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    @Override
    public String toString() {
        return "Cookies{" +
                "cookieId=" + cookieId +
                ", cookieValue='" + cookieValue + '\'' +
                '}';
    }
}



