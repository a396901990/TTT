package com.dean.travltotibet.fragment;

import android.app.AlertDialog;
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

import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.activity.RouteInfoActivity;
import com.dean.travltotibet.adapter.RoutePlanListAdapter;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/23/15.
 */
public class RouteConfirmDialog extends DialogFragment {

    private RouteInfoActivity routeInfoActivity;

    private View root;
    private boolean isForward = true;
    private String routeName;
    private String routeType;

    private View fromView;
    private View toView;

    private TextView fromText;
    private TextView toText;
    private ImageView rotateArrow;

    private ListView mListView;
    private RoutePlanListAdapter mAdapter;
    private Animation switchAnim;
    private ArrayList<RoutePlan> plans;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        routeInfoActivity = (RouteInfoActivity) getActivity();

        root = LayoutInflater.from(getActivity()).inflate(R.layout.route_confirm_fragment, null);

        routeName = getArguments().getString(Constants.INTENT_ROUTE_NAME);
        routeType = getArguments().getString(Constants.INTENT_ROUTE_TYPE);

        initDirection();
        initAnimation();
        initPlanList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(root);
        return builder.create();
    }

    /**
     * 初始化方向
     */
    private void initDirection() {
        fromView = root.findViewById(R.id.from_view);
        toView = root.findViewById(R.id.to_view);
        fromText = (TextView) root.findViewById(R.id.from_text);
        toText = (TextView) root.findViewById(R.id.to_text);
        rotateArrow = (ImageView) root.findViewById(R.id.arrow);

        String from = TTTApplication.getDbHelper().getFromName(routeName, isForward);
        String to = TTTApplication.getDbHelper().getToName(routeName, isForward);
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
        mListView = (ListView) root.findViewById(R.id.plan_list);
        plans = (ArrayList<RoutePlan>) TTTApplication.getDbHelper().getRoutePlans(routeName, routeType, isForward);
        mAdapter = new RoutePlanListAdapter(routeInfoActivity);
        mAdapter.setData(plans);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dismiss();
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                intent.putExtra(Constants.INTENT_ROUTE_NAME, routeName);
                intent.putExtra(Constants.INTENT_ROUTE_TYPE, routeType);
                intent.putExtra(Constants.INTENT_ROUTE_DIR, isForward);
                intent.putExtra(Constants.INTENT_ROUTE_PLAN_ID, plans.get(position).getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 重新设置list数据
     */
    private void resetListData() {
        plans = (ArrayList<RoutePlan>) TTTApplication.getDbHelper().getRoutePlans(routeName, routeType, isForward);
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
