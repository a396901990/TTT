package com.dean.travltotibet.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dean.greendao.RecentRoute;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.adapter.PrepareRoutePlanListAdapter;
import com.dean.travltotibet.ui.CustomDialog;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/23/15.
 */
public class InfoConfirmDialog extends DialogFragment {

    private InfoRouteActivity infoRouteActivity;

    private View contentLayout;
    private boolean isForward = true;

    private String route;
    private String routeName;
    private String routeType;

    private View fromView;
    private View toView;

    private TextView fromText;
    private TextView toText;
    private ImageView rotateArrow;

    private ListView mListView;
    private PrepareRoutePlanListAdapter mAdapter;
    private Animation switchAnim;
    private ArrayList<RoutePlan> plans;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        infoRouteActivity = (InfoRouteActivity) getActivity();

        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.prepare_confirm_dialog_fragment, null);

        if (getArguments() != null) {
            route = getArguments().getString(Constants.INTENT_ROUTE);
            routeName = getArguments().getString(Constants.INTENT_ROUTE_NAME);
            routeType = getArguments().getString(Constants.INTENT_ROUTE_TYPE);
            isForward = true;
        }

        initDirection();
        initAnimation();
        initPlanList();

        // 创建对话框，设置标题，布局，关闭响应
        CustomDialog dialog = new CustomDialog(getActivity());
        dialog.setTitle(routeName);
        dialog.setCustomContentView(contentLayout);
        dialog.setCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return dialog;
    }

    /**
     * 初始化方向
     */
    private void initDirection() {
        fromView = contentLayout.findViewById(R.id.from_view);
        toView = contentLayout.findViewById(R.id.to_view);
        fromText = (TextView) contentLayout.findViewById(R.id.from_text);
        toText = (TextView) contentLayout.findViewById(R.id.to_text);
        rotateArrow = (ImageView) contentLayout.findViewById(R.id.arrow);

        String from = TTTApplication.getDbHelper().getFromName(route, isForward);
        String to = TTTApplication.getDbHelper().getToName(route, isForward);
        fromText.setText(from);
        toText.setText(to);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {

        switchAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.arrow_rotate);
        switchAnim.setAnimationListener(new SwitchAnimationListener());

        rotateArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(switchAnim);
            }
        });
    }

    /**
     * 初始化计划列表
     */
    private void initPlanList() {
        mListView = (ListView) contentLayout.findViewById(R.id.plan_list);
        plans = (ArrayList<RoutePlan>) TTTApplication.getDbHelper().getRoutePlans(route, routeType, isForward);
        mAdapter = new PrepareRoutePlanListAdapter(infoRouteActivity);
        mAdapter.setData(plans);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dismiss();

                // 跳转到RouteActivity
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                intent.putExtra(Constants.INTENT_ROUTE, route);
                intent.putExtra(Constants.INTENT_ROUTE_TYPE, routeType);
                intent.putExtra(Constants.INTENT_ROUTE_DIR, isForward);
                intent.putExtra(Constants.INTENT_ROUTE_PLAN_ID, plans.get(position).getId());
                startActivity(intent);

                // 插入最近路线数据
                insertRecentData(position);

            }
        });
    }

    /**
     * 插入最近数据
     */
    private void insertRecentData(int position) {
        // 初始化对象
        RecentRoute recentRoute = new RecentRoute();
        recentRoute.setRoute(route);
        recentRoute.setRoute_name(routeName);
        recentRoute.setType(routeType);
        recentRoute.setFR(isForward ? "F" : "R");
        recentRoute.setRoute_plan_id(plans.get(position).getId() + "");
        // 先查看是否含有该数据，有则删除
        TTTApplication.getDbHelper().checkRecentRoute(recentRoute);
        // 添加数据
        TTTApplication.getDbHelper().insertRecentRoute(recentRoute);
    }

    /**
     * 重新设置list数据
     */
    private void resetListData() {
        plans = (ArrayList<RoutePlan>) TTTApplication.getDbHelper().getRoutePlans(route, routeType, isForward);
        mAdapter.setData(plans);
        mAdapter.notifyDataSetInvalidated();
    }

    /**
     * 交换动画
     */
    private class SwitchAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

            // 改变方向
            isForward = !isForward;
            // 交换两边文字
            initDirection();
            // 重置数据
            resetListData();

            // 播放动画
            fromView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.text_translate_left_anim));
            toView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.text_translate_right_anim));
        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
