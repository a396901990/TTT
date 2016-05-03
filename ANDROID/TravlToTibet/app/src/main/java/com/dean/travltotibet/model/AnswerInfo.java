package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AnswerInfo extends BmobObject {

    public final static String PASS_STATUS = "P";

    public final static String WAIT_STATUS = "W";

    public final static String NO_PASS_STATUS = "N";

    public String status;

    public String content;

    public String userName;

    public String userIcon;

    public String userGender;

    private UserInfo user;

    private QARequest qaRequest;

    private int like;

    private int unlike;
}