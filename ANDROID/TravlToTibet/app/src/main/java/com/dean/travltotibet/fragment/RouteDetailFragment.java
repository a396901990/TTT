package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.MenuUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteDetailFragment extends BaseRouteFragment {

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    private BaseGuideFragment detailFragment;

    private RoutePlanFragment planFragment;

    private final static String ROOT_VIEW_ROUTE = "route";

    private final static String ROOT_VIEW_GUIDE = "guide";

    public static RouteDetailFragment newInstance() {
        RouteDetailFragment newFragment = new RouteDetailFragment();
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
            contentView = inflater.inflate(R.layout.route_guide_detail_fragment_view, null, false);

            // detail fragment
            detailFragment = (BaseGuideFragment) getFragmentManager().findFragmentById(R.id.guide_detail_fragment);
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

            if (detailFragment != null && detailFragment.isAdded()) {
                detailFragment.update();
            }
        }
    }

    @Override
    public void fabBtnEvent() {

    }

    @Override
    public void initMenu(final FloatingActionMenu menu) {
        menu.hideMenu(true);
    }

    /**
     * 移除所有fragment视图
     */
    private void destroyFragmentView() {
        FragmentManager fm = getFragmentManager();
        Fragment detailFrag = fm.findFragmentById(R.id.guide_detail_fragment);
        if (detailFrag != null && getActivity() != null) {
            fm.beginTransaction().remove(detailFrag).commitAllowingStateLoss();
        }
        Fragment planFrag = fm.findFragmentById(R.id.guide_plan_fragment);
        if (planFrag != null && getActivity() != null) {
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
