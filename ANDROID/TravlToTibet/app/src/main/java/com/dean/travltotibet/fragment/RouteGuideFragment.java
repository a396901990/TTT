package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.MenuUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;

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

    private BaseGuideFragment hotelFragment;

    private RoutePlanFragment planFragment;

    private com.dean.travltotibet.ui.fab.FloatingActionButton hotel;
    private com.dean.travltotibet.ui.fab.FloatingActionButton detail;
    private com.dean.travltotibet.ui.fab.FloatingActionButton overView;

    private final static int HOTEL = 0;
    private final static String HOTEL_TITLE = "住宿";

    private final static int DETAIL = 1;
    private final static String DETAIL_TITLE = "行程";

    private final static int OVERVIEW = 2;
    private final static String OVERVIEW_TITLE = "简介";

    private final static String ROOT_VIEW_ROUTE = "route";

    private final static String ROOT_VIEW_GUIDE = "guide";

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
        routeActivity = (RouteActivity) getActivity();
        if (routeActivity.isRoute()) {
            initPlanView();
        } else {
            initGuideView();
        }
    }

    /**
     * 初始化guide视图
     */
    private void initGuideView() {
        // 清空视图
        if (contentView != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null)
                parent.removeView(contentView);

            destroyFragmentView();
        }

        try {
            // 添加guide视图
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            contentView = inflater.inflate(R.layout.guide_route_fragment_view, null, false);

            // overview fragment
            overviewFragment = (BaseGuideFragment) getFragmentManager().findFragmentById(R.id.guide_overview_fragment);
            // detail fragment
            detailFragment = (BaseGuideFragment) getFragmentManager().findFragmentById(R.id.guide_detail_fragment);
            // hotel fragment
            hotelFragment = (BaseGuideFragment) getFragmentManager().findFragmentById(R.id.guide_hotel_fragment);

            mScrollView = (ScrollView) contentView.findViewById(R.id.scroll_view);
        } catch (InflateException e) {
            // 报错则存在，就用之前的视图。。。
        }

        contentView.setTag(ROOT_VIEW_GUIDE);
    }

    /**
     * 初始化plan视图
     */
    private void initPlanView() {
        // 清空视图
        if (contentView != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null)
                parent.removeView(contentView);

            destroyFragmentView();
        }

        // 添加plan视图
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        try {
            contentView = inflater.inflate(R.layout.guide_plan_fragment_view, null, false);
            // plan fragment
            planFragment = (RoutePlanFragment) getFragmentManager().findFragmentById(R.id.guide_plan_fragment);
            planFragment.hideHeaderView();
        } catch (InflateException e) {
            // 报错则存在，就用之前的视图。。。
        }

        contentView.setTag(ROOT_VIEW_ROUTE);
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

        // 选择plan后，根据当前是route还是guide变更视图
        if (routeActivity.isRoute()) {
            initPlanView();
            ((ViewGroup) rootView).addView(contentView);
            initMenu(routeActivity.getFloatingActionMenu());
        } else {
            // 如果当前rootView为route视图，则换成guide视图。
            // 如果已经是guide视图，则不需要改变
            if (contentView.getTag().equals(ROOT_VIEW_ROUTE)) {
                initGuideView();
                ((ViewGroup) rootView).addView(contentView);
                initMenu(routeActivity.getFloatingActionMenu());
            }

            // 更新guide视图下的fragment
            if (overviewFragment != null && overviewFragment.isAdded()) {
                overviewFragment.update();
            }
            if (detailFragment != null && detailFragment.isAdded()) {
                detailFragment.update();
            }
            if (hotelFragment != null && hotelFragment.isAdded()) {
                hotelFragment.update();
            }

            // 切换plan时，跳转到顶部。并获取焦点防止scroll view下滑（内嵌list view的bug）
            View topView = contentView.findViewById(R.id.top_view);
            if (topView != null) {
                mScrollView.scrollTo(0, topView.getTop());
                topView.setFocusable(true);
                topView.setFocusableInTouchMode(true);
                topView.requestFocus();
            }
        }
    }

    @Override
    public void fabBtnEvent() {

    }

    @Override
    public void initMenu(final FloatingActionMenu menu) {

        if (routeActivity.isRoute()) {
            menu.hideMenu(true);
            return;
        }

        super.initMenu(menu);

        // 住宿
        hotel = MenuUtil.getFAB(getActivity(), HOTEL_TITLE, GoogleMaterial.Icon.gmd_terrain);
        menu.addMenuButton(hotel);
        // 攻略
        detail = MenuUtil.getFAB(getActivity(), DETAIL_TITLE, GoogleMaterial.Icon.gmd_event_note);
        menu.addMenuButton(detail);
        // 简介
        overView = MenuUtil.getFAB(getActivity(), OVERVIEW_TITLE, GoogleMaterial.Icon.gmd_web_asset);
        menu.addMenuButton(overView);

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentShowType(HOTEL);
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

        final View overview = contentView.findViewById(R.id.overview_content);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mScrollView.smoothScrollTo(0, overview.getTop());
            }
        });
    }

    public void setCurrentShowType(int currentShowType) {

        switch (currentShowType) {
            case HOTEL:
                final View hotel = contentView.findViewById(R.id.hotel_content);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.smoothScrollTo(0, hotel.getTop());
                    }
                });
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

    /**
     * 移除所有fragment视图
     */
    private void destroyFragmentView() {
        FragmentManager fm = getFragmentManager();
        Fragment overviewFrag = fm.findFragmentById(R.id.guide_overview_fragment);
        if (overviewFrag != null) {
            fm.beginTransaction().remove(overviewFrag).commitAllowingStateLoss();
        }
        Fragment detailFrag = fm.findFragmentById(R.id.guide_detail_fragment);
        if (detailFrag != null) {
            fm.beginTransaction().remove(detailFrag).commitAllowingStateLoss();
        }
        Fragment hotelFrag = fm.findFragmentById(R.id.guide_hotel_fragment);
        if (hotelFrag != null) {
            fm.beginTransaction().remove(hotelFrag).commitAllowingStateLoss();
        }
        Fragment planFrag = fm.findFragmentById(R.id.guide_plan_fragment);
        if (planFrag != null) {
            fm.beginTransaction().remove(planFrag).commitAllowingStateLoss();
        }
    }

    @Override
    public void onDestroyView() {
        if (!getActivity().isFinishing()) {
            destroyFragmentView();
        }
        super.onDestroyView();
    }

}
