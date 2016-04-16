package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.ExpandableTextView;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 10/14/15.
 * 用来显示route详细介绍视图
 */
public class InfoDetailFragment extends BaseInfoFragment {

    private InfoActivity infoActivity;

    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.info_detail_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        infoActivity = (InfoActivity) getActivity();

        initDetail();
        initExpandableTextView();
    }

    private void initDetail() {
        TextView routeStartEnd = (TextView) root.findViewById(R.id.detail_route_start_end);
        TextView routeDistance = (TextView) root.findViewById(R.id.detail_route_distance);
        TextView routeSubName = (TextView) root.findViewById(R.id.detail_route_sub_name);

        // 设置路线起始终点
        String start = TTTApplication.getDbHelper().getFromName(infoActivity.getRoute(), true);
        String end = TTTApplication.getDbHelper().getToName(infoActivity.getRoute(), true);
        routeStartEnd.setText(String.format(Constants.HEADER_START_END, start, end));

        // sub name
        String subName = TTTApplication.getDbHelper().getRoadSubNameWithName(infoActivity.getRouteName());
        if (!TextUtils.isEmpty(subName)) {
            routeSubName.setVisibility(View.VISIBLE);
            routeSubName.setText(subName);
        } else {
            routeSubName.setVisibility(View.GONE);
        }

        // 设置路线距离
        String distance = TTTApplication.getDbHelper().getRouteDistance(infoActivity.getRoute());
        routeDistance.setText(distance);
    }

    /**
     * 初始化ExpandableTextView
     */
    private void initExpandableTextView() {
        final ExpandableTextView expandableTextView = (ExpandableTextView) root.findViewById(R.id.expandable_text_view);
        final View shadeView = root.findViewById(R.id.bottom_shade);

        // 设置文字
        String detail = TTTApplication.getDbHelper().getRouteDetail(infoActivity.getRoute());
        expandableTextView.setText(detail);

        // 设置监听状态
        expandableTextView.setOnExpandListener(new ExpandableTextView.OnExpandListener() {
            // 展开时显示阴影
            @Override
            public void onExpand(ExpandableTextView parent) {
                shadeView.setVisibility(View.INVISIBLE);
            }
        }).setOnCollapseListener(new ExpandableTextView.OnCollapseListener() {
            // 收起时关闭阴影
            @Override
            public void onCollapse(ExpandableTextView parent) {
                shadeView.setVisibility(View.VISIBLE);
            }
        }).setOnClickListener(new View.OnClickListener() {
            @Override
            // 点击打开关闭阴影
            public void onClick(View v) {
                expandableTextView.toggle();
            }
        });
    }

    @Override
    public void updateType(String type) {
        super.updateType(type);

        if (type.equals(TravelType.BIKE)) {
        }
        else if (type.equals(TravelType.HIKE)) {
        }
        else if (type.equals(TravelType.MOTO)) {
        }
        else if (type.equals(TravelType.CAR)) {
        }

    }
}
