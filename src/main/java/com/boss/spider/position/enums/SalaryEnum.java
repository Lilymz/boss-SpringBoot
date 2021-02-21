package com.boss.spider.position.enums;

/**
 * @author minnan
 * @date 2021/1/27-19:20
 */
public enum SalaryEnum {
    SALARY_ENUM1("sel-salary-1","3K以下"),SALARY_ENUM2("sel-salary-2","3-5K"),
    SALARY_ENUM3("sel-salary-3","5-10K"),SALARY_ENUM4("sel-salary-4","10-15K"),
    SALARY_ENUM5("sel-salary-5","15-20K"),SALARY_ENUM6("sel-salary-6","20-30K");
    private String salaryCode;
    private String SalaryName;
    SalaryEnum(String salaryCode,String salaryName){this.salaryCode=salaryCode;this.SalaryName=salaryName;}
    public static String getName(String salaryName){
        for (SalaryEnum salaryEnum:SalaryEnum.values()){
            if (salaryEnum.getSalaryName().equals(salaryName)){
                return salaryEnum.salaryCode;
            }
        }
        return null;
    }
    public String getSalaryCode() {
        return salaryCode;
    }

    public void setSalaryCode(String salaryCode) {
        this.salaryCode = salaryCode;
    }

    public String getSalaryName() {
        return SalaryName;
    }

    public void setSalaryName(String salaryName) {
        SalaryName = salaryName;
    }
}
