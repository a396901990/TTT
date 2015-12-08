package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.MenuUtil;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteGuideFragment extends BaseRouteFragment {

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    private ScrollView mScrollView;

    private BaseGuideFragment overviewFragment;

    private BaseGuideFragment detailFragment;

    private com.dean.travltotibet.ui.fab.FloatingActionButton satellite;
    private com.dean.travltotibet.ui.fab.FloatingActionButton detail;
    private com.dean.travltotibet.ui.fab.FloatingActionButton overView;

    private final static int SATELLITE = 0;

    private final static int DETAIL = 1;

    private final static int OVERVIEW = 2;

    private int currentShowType = 1;

    public static RouteGuideFragment newInstance() {
        RouteGuideFragment newFragment = new RouteGuideFragment();
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_content_frame, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();

    }

    @Override
    protected void onLoadPrepared() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        contentView = inflater.inflate(R.layout.guide_route_fragment_view, null, false);
        overviewFragment = (BaseGuideFragment) getFragmentManager().findFragmentById(R.id.guide_overview_fragment);
        detailFragment = (BaseGuideFragment) getFragmentManager().findFragmentById(R.id.guide_detail_fragment);
        mScrollView = (ScrollView) contentView.findViewById(R.id.scroll_view);
    }

    @Override
    protected void onLoading() {
    }

    @Override
    protected void onLoadFinished() {
        ((ViewGroup) rootView).addView(contentView);
    }

    @Override
    public void updateRoute() {

        if (overviewFragment != null && overviewFragment.isAdded()) {
            overviewFragment.update();
        }
        if (overviewFragment != null && detailFragment.isAdded()) {
            detailFragment.update();
        }
    }

    @Override
    public void fabBtnEvent() {

    }

    @Override
    public void initMenu(final FloatingActionMenu menu) {
        menu.removeAllMenuButtons();

        // 住宿
        satellite = MenuUtil.initFAB(getActivity(), "住宿", R.drawable.ic_ab_back_icon);
        menu.addMenuButton(satellite);
        // 攻略
        detail = MenuUtil.initFAB(getActivity(), "行程", R.drawable.ic_ab_back_icon);
        menu.addMenuButton(detail);
        // 简介
        overView = MenuUtil.initFAB(getActivity(), "简介", R.drawable.ic_ab_back_icon);
        menu.addMenuButton(overView);

        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentShowType(OVERVIEW);
                menu.close(true);
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentShowType(DETAIL);
                menu.close(true);
            }
        });

        overView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentShowType(OVERVIEW);
                menu.close(true);
            }
        });
    }

    public void setCurrentShowType(int currentShowType) {
        this.currentShowType = currentShowType;

        switch (currentShowType) {
            case SATELLITE:
                break;
            case DETAIL:
                final View detail = contentView.findViewById(R.id.detail_content);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.smoothScrollTo(0, detail.getTop());
                    }
                });
                break;
            case OVERVIEW:
                final View overview = contentView.findViewById(R.id.overview_content);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.smoothScrollTo(0, overview.getTop());
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroyView() {
        if (!getActivity().isFinishing()) {
            FragmentManager fm = getFragmentManager();
            Fragment overviewFrag = fm.findFragmentById(R.id.guide_overview_fragment);
            if (overviewFrag != null) {
                fm.beginTransaction().remove(overviewFrag).commitAllowingStateLoss();
            }
            Fragment detailFrag = fm.findFragmentById(R.id.guide_detail_fragment);
            if (detailFrag != null) {
                fm.beginTransaction().remove(detailFrag).commitAllowingStateLoss();
            }
        }
        super.onDestroyView();
    }

}
