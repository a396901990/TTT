package com.dean.travltotibet.fragment;

import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.base.BaseInsideCommentFragment;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleCommentFragment extends BaseInsideCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.ARTICLE_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        if (getActivity() == null) {
            return null;
        }

        if (((BaseCommentActivity) getActivity()).getObj() == null) {
            return null;
        }
        return ((BaseCommentActivity) getActivity()).getObj().getObjectId();
    }
}
