package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ArticleActivity;
import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.adapter.ReplyCommentListAdapter;
import com.dean.travltotibet.base.BaseInsideCommentFragment;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.base.NewBaseInsideCommentFragment;
import com.dean.travltotibet.model.BaseCommentBmobObject;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.customScrollView.InsideScrollLoadMorePressListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 5/9/16.
 */
public class TeamShowRequestCommentFragment extends NewBaseInsideCommentFragment {

    TeamShowRequestDetailActivity teamShowRequestDetailActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        teamShowRequestDetailActivity = (TeamShowRequestDetailActivity) getActivity();
    }

    @Override
    public String getCommentType() {
        return Comment.TEAM_REQUEST_COMMENT;
    }

    @Override
    public BaseCommentBmobObject getCommentObject() {
        if (teamShowRequestDetailActivity == null) {
            return null;
        }
        return teamShowRequestDetailActivity.getTeamRequest();
    }
}