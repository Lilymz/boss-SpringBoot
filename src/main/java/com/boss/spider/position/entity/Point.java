package com.boss.spider.position.entity;

/**
 * @author minnan
 * @date 2021/2/1-20:04
 */
public class Point {
    private String x;
    private String y;
    private String branch;

    public Point(String x, String y, String branch) {
        this.x = x;
        this.y = y;
        this.branch = branch;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getBranch() {
        return branch;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
