package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RoadInfoCreateActivity;
import com.dean.travltotibet.activity.RoadInfoDetailActivity;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.model.RoadInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.List;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseGuideFragment extends Fragment {

    private RouteActivity routeActivity;

    private View expandBtn, collapseBtn;

    private View roadMessageContent;

    private static final int SHOW_SIZE = 6;

    public abstract void update();

    public View getRoadMessageView() {

        roadMessageContent = LayoutInflater.from(routeActivity).inflate(R.layout.road_message_cotent, null);
        // 隐藏阴影
        expandBtn = roadMessageContent.findViewById(R.id.expand_btn);
        expandBtn.setVisibility(View.GONE);
        expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeActivity != null) {
                    showMore();
                }
            }
        });

        collapseBtn = roadMessageContent.findViewById(R.id.collapse_btn);
        collapseBtn.setVisibility(View.GONE);
        collapseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeActivity != null) {
                    showLess();
                }
            }
        });

        View feedbackBtn = roadMessageContent.findViewById(R.id.feedback_btn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeActivity == null) {
                    return;
                }
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(routeActivity, RoadInfoCreateActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, routeActivity.getRouteName());
                routeActivity.startActivity(intent);
            }
        });
        return roadMessageContent;
    }

    public void showMore() {
        List<RoadInfo> list = routeActivity.getRoadInfos();

        ViewGroup roadContent = (ViewGroup) roadMessageContent.findViewById(R.id.road_message_content_view);
        if (roadContent == null) {
            return;
        }

        for (int i = SHOW_SIZE; i < list.size(); i++) {
            RoadInfo roadInfo = list.get(i);
            generateMessage(roadContent, roadInfo);
        }

        // 隐藏展开按钮
        expandBtn.setVisibility(View.GONE);
        collapseBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                collapseBtn.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    public void showLess() {

        List<RoadInfo> list = routeActivity.getRoadInfos();

        ViewGroup roadContent = (ViewGroup) roadMessageContent.findViewById(R.id.road_message_content_view);
        if (roadContent == null) {
            return;
        }
        roadContent.removeAllViews();

        for (int i=0; i<list.size(); i++) {

            // 少于或等于不显示更多按钮
            if (i < SHOW_SIZE) {
                expandBtn.setVisibility(View.GONE);
                RoadInfo roadInfo = list.get(i);
                generateMessage(roadContent, roadInfo);
            }
            else {
                expandBtn.setVisibility(View.VISIBLE);
            }
        }

        collapseBtn.setVisibility(View.GONE);
    }

    public void setUpMessage(List<RoadInfo> list, View container) {
        ViewGroup roadContent = (ViewGroup) container.findViewById(R.id.road_message_content_view);
        if (roadContent == null) {
            return;
        }
        roadContent.removeAllViews();
        for (RoadInfo roadInfo : list)
        {
            generateMessage(roadContent, roadInfo);
        }
    }


    private void generateMessage( final ViewGroup container, final RoadInfo roadInfo)
    {
        View view = LayoutInflater.from(routeActivity).inflate(R.layout.road_message_item, null);
        // Priority
        if (!TextUtils.isEmpty(roadInfo.getPriority())) {
            ((ImageView) view.findViewById(R.id.priority_icon)).setImageResource(RoadInfo.getPriorityIcon(roadInfo.getPriority()));
        }
        // Title
        if (!TextUtils.isEmpty(roadInfo.getTitle())) {
            ((TextView) view.findViewById(R.id.road_text)).setText(roadInfo.getTitle());
        }
        MaterialRippleLayout materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.ripple_view);
        materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (routeActivity != null) {
                    Intent intent = new Intent(routeActivity, RoadInfoDetailActivity.class);
                    intent.putExtra(IntentExtra.INTENT_ROAD, roadInfo);
                    routeActivity.startActivity(intent);
                }
            }
        });

        container.addView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
    }

}
