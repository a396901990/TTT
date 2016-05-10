package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by DeanGuo on 5/9/16.
 */
public class BaseCommentBmobObject extends BmobObject {

    private BmobRelation replyComments;

    public BmobRelation getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(BmobRelation replyComments) {
        this.replyComments = replyComments;
    }
}