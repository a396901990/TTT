package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import com.dean.travltotibet.ui.CustomProgress;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.List;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseGuideFragment extends Fragment {

    private RouteActivity routeActivity;

    public abstract void update();

    public View getRoadMessageView() {

        View roadMessageContent = LayoutInflater.from(getActivity()).inflate(R.layout.road_message_cotent, null);

        View feedbackBtn = roadMessageContent.findViewById(R.id.feedback_btn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() == null) {
                    return;
                }
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), RoadInfoCreateActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, routeActivity.getRouteName());
                getActivity().startActivity(intent);
            }
        });
        return roadMessageContent;
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.road_message_item, null);
        ((ImageView) view.findViewById(R.id.priority_icon)).setImageResource(RoadInfo.getPriorityIcon(roadInfo.getPriority()));
        ((TextView) view.findViewById(R.id.road_text)).setText(roadInfo.getTitle());
        MaterialRippleLayout materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.ripple_view);
        materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), RoadInfoDetailActivity.class);
                    intent.putExtra(IntentExtra.INTENT_ROAD, roadInfo);
                    getActivity().startActivity(intent);
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
