package com.dean.travltotibet.dialog;

import android.os.Bundle;

import com.dean.travltotibet.activity.AnswerDetailActivity;
import com.dean.travltotibet.model.AnswerInfo;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 5/11/16.
 */
public class AnswerCommentDialog extends ReplyCommentDialog {

    private AnswerInfo answerInfo;

    private AnswerDetailActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AnswerDetailActivity) getActivity();
        answerInfo = mActivity.getAnswerInfo();
    }

    @Override
    public String getCommentType() {
        return Comment.ANSWER_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        return answerInfo.getObjectId();
    }

    @Override
    public String getCommentTypeDesc() {
        return answerInfo.getQuestionTitle();
    }

    @Override
    public boolean isShowRattingBar() {
        return false;
    }
}