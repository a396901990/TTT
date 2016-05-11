package com.dean.travltotibet.model;

import android.content.Context;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class Report extends BmobObject {

    public  final static String REPORT_COMMENT = "comment";
    public  final static String REPORT_TEAM_REQUEST = "teamRequest";
    public  final static String REPORT_QA_REQUEST = "qa";
    public  final static String REPORT_ANSWER_REQUEST = "answer";

    private String reportId;
    private String reportType;
    private String reportUserId;
    private String reportUserName;
    private String reportByUserId;
    private String reportByUserName;

    public String getReportByUserId() {
        return reportByUserId;
    }

    public void setReportByUserId(String reportByUserId) {
        this.reportByUserId = reportByUserId;
    }

    public String getReportByUserName() {
        return reportByUserName;
    }

    public void setReportByUserName(String reportByUserName) {
        this.reportByUserName = reportByUserName;
    }

    public String getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }

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

    public String getReportUserName() {
        return reportUserName;
    }

    public void setReportUserName(String reportUserName) {
        this.reportUserName = reportUserName;
    }

    public void addReport(final Context context, String reportType, String reportId, String reportUserId, String reprotUserName) {
        Report report = new Report();
        report.setReportId(reportId);
        report.setReportType(reportType);
        report.setReportUserId(reportUserId);
        report.setReportUserName(reprotUserName);
        if (TTTApplication.hasLoggedIn() && TTTApplication.getUserInfo() != null) {
            report.setReportByUserId(TTTApplication.getUserInfo().getUserId());
            report.setReportByUserName(TTTApplication.getUserInfo().getUserName());
        }
        report.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, context.getString(R.string.report_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, context.getString(R.string.action_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
