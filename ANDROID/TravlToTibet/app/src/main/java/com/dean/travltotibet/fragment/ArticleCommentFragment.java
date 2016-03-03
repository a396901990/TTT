package com.dean.travltotibet.fragment;

import com.dean.travltotibet.model.ArticleComment;
import com.dean.travltotibet.model.Comment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleCommentFragment extends BaseCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.ARTICLE_COMMENT;
    }

    @Override
    public void getCommentData() {

        BmobQuery<ArticleComment> query = new BmobQuery<>();
        query.addWhereEqualTo("article_id", mActivity.getObj().getObjectId());
        query.findObjects(getActivity(), new FindListener<ArticleComment>() {
            @Override
            public void onSuccess(List<ArticleComment> list) {

                ArrayList<Comment> mComments = new ArrayList<Comment>();
                for (ArticleComment articleComment : list) {
                    Comment comment = articleComment;
                    mComments.add(comment);
                }
                getDataSuccess(mComments);
            }

            @Override
            public void onError(int i, String s) {
                getDataFailed();
            }
        });
    }

}
