package com.dean.travltotibet.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dean.greendao.RecentRoute;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.adapter.PrepareRoutePlanListAdapter;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.CustomDialog;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/23/15.
 */
public class InfoPlanConfirmDialog extends DialogFragment {

    private InfoActivity infoActivity;

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

    private final static int HALF_ALPHA = (int) (255 * 0.5);

    private final static int ALPHA = 255;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoActivity = (InfoActivity) getActivity();

        if (getArguments() != null) {
            route = getArguments().getString(IntentExtra.INTENT_ROUTE);
            routeName = getArguments().getString(IntentExtra.INTENT_ROUTE_NAME);
            routeType = getArguments().getString(IntentExtra.INTENT_ROUTE_TYPE);
            isForward = true;
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PopupDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.info_plan_confirm_view, null);
        initDirection();
        initRotate();
        initPlanList();
        initTravelHeader();

        return contentLayout;
    }

    private void initTravelHeader() {

        ImageView bikeBtn = (ImageView) contentLayout.findViewById(R.id.header_bike_type);
        ImageView hikeBtn = (ImageView) contentLayout.findViewById(R.id.header_hike_type);
        ImageView motoBtn = (ImageView) contentLayout.findViewById(R.id.header_moto_type);
        ImageView carBtn = (ImageView) contentLayout.findViewById(R.id.header_car_type);

        setTravelIcon(routeType);

        bikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTravelIcon(TravelType.BIKE);
            }
        });
        hikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTravelIcon(TravelType.HIKE);
            }
        });
        motoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTravelIcon(TravelType.MOTO);
            }
        });
        carBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTravelIcon(TravelType.CAR);
            }
        });


        ImageView cancelBtn = (ImageView) contentLayout.findViewById(R.id.header_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }

    private void setTravelIcon(String travelType) {
        ImageView bikeBtn = (ImageView) contentLayout.findViewById(R.id.header_bike_type);
        ImageView hikeBtn = (ImageView) contentLayout.findViewById(R.id.header_hike_type);
        ImageView motoBtn = (ImageView) contentLayout.findViewById(R.id.header_moto_type);
        ImageView carBtn = (ImageView) contentLayout.findViewById(R.id.header_car_type);

        bikeBtn.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.BIKE, R.color.white));
        hikeBtn.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.HIKE, R.color.white));
        motoBtn.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.MOTO, R.color.white));
        carBtn.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.CAR, R.color.white));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            bikeBtn.setImageAlpha(HALF_ALPHA);
            hikeBtn.setImageAlpha(HALF_ALPHA);
            motoBtn.setImageAlpha(HALF_ALPHA);
            carBtn.setImageAlpha(HALF_ALPHA);
        } else {
            bikeBtn.setAlpha(HALF_ALPHA);
            hikeBtn.setAlpha(HALF_ALPHA);
            motoBtn.setAlpha(HALF_ALPHA);
            carBtn.setAlpha(HALF_ALPHA);
        }

        if (travelType.equals(TravelType.BIKE)) {
            bikeBtn.setImageDrawable(TravelType.getTypeImageSrcWithColor(travelType, R.color.white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                bikeBtn.setImageAlpha(ALPHA);
            } else {
                bikeBtn.setAlpha(ALPHA);
            }
        } else if (travelType.equals(TravelType.HIKE)) {
            hikeBtn.setImageDrawable(TravelType.getTypeImageSrcWithColor(travelType, R.color.white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                hikeBtn.setImageAlpha(ALPHA);
            } else {
                hikeBtn.setAlpha(ALPHA);
            }
        } else if (travelType.equals(TravelType.MOTO)) {
            motoBtn.setImageDrawable(TravelType.getTypeImageSrcWithColor(travelType, R.color.white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                motoBtn.setImageAlpha(ALPHA);
            } else {
                motoBtn.setAlpha(ALPHA);
            }
        } else if (travelType.equals(TravelType.CAR)) {
            carBtn.setImageDrawable(TravelType.getTypeImageSrcWithColor(travelType, R.color.white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                carBtn.setImageAlpha(ALPHA);
            } else {
                carBtn.setAlpha(ALPHA);
            }
        }

        routeType = travelType;
        resetListData();
    }

    /**
     * 初始化方向
     */
    private void initDirection() {
        fromView = contentLayout.findViewById(R.id.from_view);
        toView = contentLayout.findViewById(R.id.to_view);
        fromText = (TextView) contentLayout.findViewById(R.id.from_text);
        toText = (TextView) contentLayout.findViewById(R.id.to_text);
        rotateArrow = (ImageView) contentLayout.findViewById(R.id.rotate_arrow);

        String from = TTTApplication.getDbHelper().getFromName(route, isForward);
        String to = TTTApplication.getDbHelper().getToName(route, isForward);
        fromText.setText(from);
        toText.setText(to);

        ImageView from_icon = (ImageView) contentLayout.findViewById(R.id.from_icon);
        ImageView to_icon = (ImageView) contentLayout.findViewById(R.id.to_icon);

        int circlePadding = ScreenUtil.dip2px(getActivity(), 20);
        int loactorPadding = ScreenUtil.dip2px(getActivity(), 18);

        if (isForward) {
            from_icon.setImageDrawable(ContextCompat.getDrawable(infoActivity, R.drawable.icon_ring_circular));
            from_icon.setPadding(circlePadding, circlePadding, circlePadding, circlePadding);

            to_icon.setImageDrawable(ContextCompat.getDrawable(infoActivity, R.drawable.icon_locator));
            to_icon.setPadding(loactorPadding, loactorPadding, loactorPadding, loactorPadding);
        } else {
            from_icon.setImageDrawable(ContextCompat.getDrawable(infoActivity, R.drawable.icon_locator));
            from_icon.setPadding(loactorPadding, loactorPadding, loactorPadding, loactorPadding);

            to_icon.setImageDrawable(ContextCompat.getDrawable(infoActivity, R.drawable.icon_ring_circular));
            to_icon.setPadding(circlePadding, circlePadding, circlePadding, circlePadding);
        }
    }

    /**
     * 初始化动画
     */
    private void initRotate() {

        switchAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.arrow_rotate);
        switchAnim.setAnimationListener(new SwitchAnimationListener());

        rotateArrow.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_swap_vert, TTTApplication.getMyColor(R.color.half_white)));
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
        mAdapter = new PrepareRoutePlanListAdapter(infoActivity, mListView);
        mAdapter.setData(plans);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dismiss();

                // 跳转到RouteActivity
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, route);
                intent.putExtra(IntentExtra.INTENT_ROUTE_TYPE, routeType);
                intent.putExtra(IntentExtra.INTENT_ROUTE_DIR, isForward);
                intent.putExtra(IntentExtra.INTENT_ROUTE_PLAN_ID, plans.get(position).getId());
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

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(getActivity(), 330));
        window.setGravity(Gravity.BOTTOM);
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
            fromView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.translate_up_anim));
            toView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.translate_down_anim));
        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
