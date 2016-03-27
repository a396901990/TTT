package com.dean.travltotibet.fragment;

import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleCommentFragment extends BaseCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.ARTICLE_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        if (getActivity() == null) {
            return null;
        }
        return ((BaseCommentActivity) getActivity()).getObj().getObjectId();
    }
}
