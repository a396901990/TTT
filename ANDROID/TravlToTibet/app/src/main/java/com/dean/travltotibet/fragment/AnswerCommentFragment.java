package com.dean.travltotibet.fragment;

import android.os.Bundle;

import com.dean.travltotibet.activity.AnswerDetailActivity;
import com.dean.travltotibet.base.NewBaseInsideCommentFragment;
import com.dean.travltotibet.model.BaseCommentBmobObject;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 5/12/16.
 */
public class AnswerCommentFragment extends NewBaseInsideCommentFragment {

    AnswerDetailActivity answerDetailActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        answerDetailActivity = (AnswerDetailActivity) getActivity();
    }

    @Override
    public String getCommentType() {
        return Comment.ANSWER_COMMENT;
    }

    @Override
    public BaseCommentBmobObject getCommentObject() {
        if (answerDetailActivity == null) {
            return null;
        }
        return answerDetailActivity.getAnswerInfo();
    }
}