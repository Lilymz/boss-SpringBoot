package com.boss.spider.position.entity;

import java.util.Date;

/**
 * @author minnan
 * @date 2021/1/13-19:50
 */
public class Position {
    private Integer positionId;
    private String positionTitle;
    private String positionSalaryRange;
    private String positionLink;
    private String positionRequire;
    private String positionCompanySIZE;
    private String positionBuildTime;
    private Date positionWorkTime;
    private Date createTime;
    private String positionArea;
    private String positionCity;
    private String companyName;
    public Position() {
    }

    public Position(Integer positionId, String positionTitle, String positionSalaryRange, String positionLink, String positionRequire, String positionCompanySIZE, String positionBuildTime, Date positionWorkTime, Date createTime, String positionArea, String positionCity, String companyName) {
        this.positionId = positionId;
        this.positionTitle = positionTitle;
        this.positionSalaryRange = positionSalaryRange;
        this.positionLink = positionLink;
        this.positionRequire = positionRequire;
        this.positionCompanySIZE = positionCompanySIZE;
        this.positionBuildTime = positionBuildTime;
        this.positionWorkTime = positionWorkTime;
        this.createTime = createTime;
        this.positionArea = positionArea;
        this.positionCity = positionCity;
        this.companyName = companyName;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getPositionSalaryRange() {
        return positionSalaryRange;
    }

    public void setPositionSalaryRange(String positionSalaryRange) {
        this.positionSalaryRange = positionSalaryRange;
    }

    public String getPositionLink() {
        return positionLink;
    }

    public void setPositionLink(String positionLink) {
        this.positionLink = positionLink;
    }

    public String getPositionRequire() {
        return positionRequire;
    }

    public void setPositionRequire(String positionRequire) {
        this.positionRequire = positionRequire;
    }

    public String getPositionCompanySIZE() {
        return positionCompanySIZE;
    }

    public void setPositionCompanySIZE(String positionCompanySIZE) {
        this.positionCompanySIZE = positionCompanySIZE;
    }

    public String getPositionBuildTime() {
        return positionBuildTime;
    }

    public void setPositionBuildTime(String positionBuildTime) {
        this.positionBuildTime = positionBuildTime;
    }

    public Date getPositionWorkTime() {
        return positionWorkTime;
    }

    public void setPositionWorkTime(Date positionWorkTime) {
        this.positionWorkTime = positionWorkTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPositionArea() {
        return positionArea;
    }

    public void setPositionArea(String positionArea) {
        this.positionArea = positionArea;
    }

    public String getPositionCity() {
        return positionCity;
    }

    public void setPositionCity(String positionCity) {
        this.positionCity = positionCity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionId=" + positionId +
                ", positionTitle='" + positionTitle + '\'' +
                ", positionSalaryRange='" + positionSalaryRange + '\'' +
                ", positionLink='" + positionLink + '\'' +
                ", positionRequire='" + positionRequire + '\'' +
                ", positionCompanySIZE='" + positionCompanySIZE + '\'' +
                ", positionBuildTime='" + positionBuildTime + '\'' +
                ", positionWorkTime=" + positionWorkTime +
                ", createTime=" + createTime +
                ", positionArea='" + positionArea + '\'' +
                ", positionCity='" + positionCity + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
