package com.example.cts31301163.model;

/**
 * Created by Administrator on 2016/7/7.
 */
public class PlanBean {
     int planId ;
     String planTitle ;
    String planType ;
    String planContent ;
    String planAddTime ;
    String planClockTime ;
    String planMessage ;


    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }

    public String getPlanAddTime() {
        return planAddTime;
    }

    public void setPlanAddTime(String planAddTime) {
        this.planAddTime = planAddTime;
    }

    public String getPlanClockTime() {
        return planClockTime;
    }

    public void setPlanClockTime(String planClockTime) {
        this.planClockTime = planClockTime;
    }

    public String getPlanMessage() {
        return planMessage;
    }

    public void setPlanMessage(String planMessage) {
        this.planMessage = planMessage;
    }
}
