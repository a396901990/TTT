package com.dean.travltotibet.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class CommentReport extends BmobObject {

    private String reportId;
    private String reportType;
    private String reportUser;
    
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }
}
