package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class TeamRequestReport extends BmobObject {

    private String reportId;
    private String reportUserId;
    private String reportUserName;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }

    public String getReportUserName() {
        return reportUserName;
    }

    public void setReportUserName(String reportUserName) {
        this.reportUserName = reportUserName;
    }
}
