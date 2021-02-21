package com.boss.spider.position.entity;

/**
 * @author minnan
 * @date 2021/1/23-19:58
 */
public class SalaryView {
    private Integer salaryvId;
    private String salaryvAvgrange;
    private String salaryvArea;

    public Integer getSalaryvId() {
        return salaryvId;
    }

    public void setSalaryvId(Integer salaryvId) {
        this.salaryvId = salaryvId;
    }

    public String getSalaryvAvgrange() {
        return salaryvAvgrange;
    }

    public void setSalaryvAvgrange(String salaryvAvgrange) {
        this.salaryvAvgrange = salaryvAvgrange;
    }

    public String getSalaryvArea() {
        return salaryvArea;
    }

    public void setSalaryvArea(String salaryvArea) {
        this.salaryvArea = salaryvArea;
    }

    @Override
    public String toString() {
        return "SalaryView{" +
                "salaryvId=" + salaryvId +
                ", salaryvAvgrange='" + salaryvAvgrange + '\'' +
                ", salaryvArea='" + salaryvArea + '\'' +
                '}';
    }
}
