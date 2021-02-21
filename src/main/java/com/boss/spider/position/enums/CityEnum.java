package com.boss.spider.position.enums;

/**
 * @author minnan
 * @date 2021/1/27-18:17
 */
public enum CityEnum {
    CITY_ENUM1(101010100,"北京"),CITY_ENUM2(101020100,"上海"),
    CITY_ENUM3(101280100,"广州"),CITY_ENUM4(101280600,"深圳"),
    CITY_ENUM5(101210100,"杭州"),CITY_ENUM6(101030100,"天津"),
    CITY_ENUM7(101110100,"西安"),CITY_ENUM8(101190400,"苏州"),
    CITY_ENUM9(101200100,"武汉"),CITY_ENUM10(101230200,"厦门"),
    CITY_ENUM11(101250100,"长沙"),CITY_ENUM12(101270100,"成都"),
    CITY_ENUM13(101180100,"郑州"),CITY_ENUM14(101040100,"重庆"),
    CITY_ENUM15(101230100,"福州"),CITY_ENUM16(101260100,"贵阳"),
    CITY_ENUM17(101300500,"桂林"),CITY_ENUM18(101280800,"佛山"),
    CITY_ENUM19(101230600,"漳州");
    private String name;
    private Integer code;
    CityEnum(Integer code,String name){this.code=code;this.name=name;}
    public static Integer getCode(String name){
        for (CityEnum cityEnum:CityEnum.values()){
            if (cityEnum.getName().equals(name)){
                return cityEnum.code;
            }
        }
        return null;
    }
    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
