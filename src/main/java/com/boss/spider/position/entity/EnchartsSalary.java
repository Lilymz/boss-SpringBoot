package com.boss.spider.position.entity;

/**
 * @author minnan
 * @date 2021/1/28-22:00
 */
public class EnchartsSalary {
    //地区
    private String city;
    //平局工资
    private String salary;
    public EnchartsSalary(){}

    public EnchartsSalary(String city, String salary) {
        this.city = city;
        this.salary = salary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EnchartsSalary{" +
                "city='" + city + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
