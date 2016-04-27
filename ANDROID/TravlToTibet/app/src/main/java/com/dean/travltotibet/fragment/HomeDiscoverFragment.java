package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.TeamCreateRequestActivity;
import com.dean.travltotibet.activity.TeamRequestPersonalActivity;
import com.dean.travltotibet.adapter.TeamRequestListAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.dialog.ShowHtmlDialogFragment;
import com.dean.travltotibet.dialog.TeamRequestFilterDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.fab.FloatingActionButton;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.MenuUtil;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class HomeDiscoverFragment extends BaseRefreshFragment {

    private static final int CREATE_REQUEST = 0;

    private View root;

    private View articleHeader;

    private HomeActivity mActivity;

    private HomeTeamRequestFragment homeTeamRequestFragment;

    private HomeTopicFragment homeTopicFragment;

    public static HomeDiscoverFragment newInstance() {
        HomeDiscoverFragment fragment = new HomeDiscoverFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_discorver_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();

        homeTeamRequestFragment = HomeTeamRequestFragment.newInstance();
        homeTopicFragment = HomeTopicFragment.newInstance();
        initBottomView();
        initCreateBtn();
    }

    private void initCreateBtn() {
        // 添加结伴
        final FloatingActionMenu mFloatingActionMenu = (FloatingActionMenu) root.findViewById(R.id.add_btn);
        mFloatingActionMenu.setClosedOnTouchOutside(true);
        FloatingActionButton teamFab = (FloatingActionButton) root.findViewById(R.id.team_create_fab);
        FloatingActionButton askFab = (FloatingActionButton) root.findViewById(R.id.ask_create_fab);
        mFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatingActionMenu.isOpened()) {
                }

                mFloatingActionMenu.toggle(true);
            }
        });
    }

    private void initBottomView() {

        View filterView = root.findViewById(R.id.filter_team_request);
        View myView = root.findViewById(R.id.my_team_request);

        // 搜索结伴
        filterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                getFragmentManager().beginTransaction().replace(R.id.content_view, homeTeamRequestFragment).commit();
            }
        });

        // 我的结伴
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                getFragmentManager().beginTransaction().replace(R.id.content_view, homeTopicFragment).commit();
            }
        });
    }

}
