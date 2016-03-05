package com.dean.travltotibet.fragment;

import android.os.Bundle;

import com.dean.greendao.Scenic;
import com.dean.travltotibet.dialog.AroundScenicCommentDialog;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.ScenicComment;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AroundScenicCommentFragment extends AroundCommentFragment {

    private Scenic mScenic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScenic = (Scenic) getAroundActivity().getAroundObj();
    }

    @Override
    public void goComment() {
        super.goComment();
        BaseCommentDialog dialogFragment = new AroundScenicCommentDialog();
        Bundle bundle = new Bundle();
        dialogFragment.setArguments(bundle);
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), AroundScenicCommentDialog.class.getName());
    }

    @Override
    public void getCommentData() {
        super.getCommentData();

        BmobQuery<ScenicComment> query = new BmobQuery<>();
        query.addWhereEqualTo("scenic_belong", mScenic.getScenic_f_belong());
        query.addWhereEqualTo("scenic_name", mScenic.getScenic_name());
        query.setLimit(Constants.COMMENT_LENGTH_LIMIT);
        query.order("-createdAt");
        query.findObjects(getActivity(), new FindListener<ScenicComment>() {
            @Override
            public void onSuccess(List<ScenicComment> list) {
                ArrayList<Comment> comments = new ArrayList<>();
                comments.addAll(list);
                setComments(comments);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

}