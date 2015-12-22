package com.dean.travltotibet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AttentionActivity;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.ExpandableTextView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 10/14/15.
 * 用来显示route详细介绍视图
 */
public class InfoDetailFragment extends BaseInfoFragment {

    private InfoActivity infoActivity;

    private View root;

    private ExpandableTextView expandableTextView;

    private View shadeView;

    private TextView routeName;

    private TextView routeStartEnd;

    private TextView routeDistance;

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
        initRouteAttention();
    }

    /**
     * 注意事项按钮，打开注意事项
     */
    private void initRouteAttention() {
        View attention = root.findViewById(R.id.route_attention);
        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttentionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(IntentExtra.INTENT_ROUTE, infoActivity.getRoute());
                bundle.putString(IntentExtra.INTENT_ROUTE_TYPE, infoActivity.getRouteType());
                intent.putExtra(IntentExtra.INTENT_ROUTE_BUNDLE, bundle);
                startActivity(intent);
            }
        });
    }

    private void initDetail() {
        routeStartEnd = (TextView) root.findViewById(R.id.detail_route_start_end);
        routeDistance = (TextView) root.findViewById(R.id.detail_route_distance);

        // 设置路线起始终点
        String start = TTTApplication.getDbHelper().getFromName(infoActivity.getRoute(), true);
        String end = TTTApplication.getDbHelper().getToName(infoActivity.getRoute(), true);
        routeStartEnd.setText(String.format(Constants.HEADER_START_END, start, end));

        // 设置路线距离
        String distance = TTTApplication.getDbHelper().getRouteDistance(infoActivity.getRoute());
        routeDistance.setText(distance);
    }

    /**
     * 初始化ExpandableTextView
     */
    private void initExpandableTextView() {
        expandableTextView = (ExpandableTextView) root.findViewById(R.id.expandable_text_view);
        shadeView = root.findViewById(R.id.bottom_shade);

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
