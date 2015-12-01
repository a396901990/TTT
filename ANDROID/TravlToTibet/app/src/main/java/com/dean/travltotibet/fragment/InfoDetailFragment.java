package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.activity.InfoActivitynew;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.ExpandableTextView;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 10/14/15.
 * 用来显示route详细介绍视图
 */
public class InfoDetailFragment extends BaseInfoFragment {

    private InfoActivitynew infoActivity;

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

        infoActivity = (InfoActivitynew) getActivity();

        initDetail();
        initExpandableTextView();
    }

    private void initDetail() {
        routeName = (TextView) root.findViewById(R.id.detail_route_name);
        routeStartEnd = (TextView) root.findViewById(R.id.detail_route_start_end);
        routeDistance = (TextView) root.findViewById(R.id.detail_route_distance);

        // 设置路线名称
//        routeName.setText(infoActivity.getRouteName());

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
        //expandableTextView.setText(detail);
        expandableTextView.setText("下面说下思路吧，就是先获取TextView完全展开时的最大maxLines记录下来，让后再把TextView的maxLine设置为你想指定的任何值，我这里指定的是1，这样加载完成VIew之后我们看到的TextView就不是完全展开的，这样做的主要目的是拿到完全展开的maxLines，让后用户点击的时候不断的更新maxLine大小即可"
        );

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
