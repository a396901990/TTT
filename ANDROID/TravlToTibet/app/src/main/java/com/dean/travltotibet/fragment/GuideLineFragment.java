package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.GuideLineAdapter;
import com.dean.travltotibet.model.RoadInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 12/8/15.
 */
public class GuideLineFragment extends BaseGuideFragment {

    private View root,roadMessage,overview;

    private RouteActivity routeActivity;

    private ArrayList<Geocode> mDataResult;

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_line_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        initListView();
    }

    private void getRoadMessageInfo() {
        BmobQuery<RoadInfo> query = new BmobQuery<>();
        query.order("-comment,-createdAt");
        query.addQueryKeys("title,priority");
        query.addWhereEqualTo("route", routeActivity.getRouteName());
        query.addWhereContainedIn("belong", getBelongList());
//        query.addWhereEqualTo("status", TeamRequest.PASS_STATUS);
//        query.setLimit(3);

        query.findObjects(getActivity(), new FindListener<RoadInfo>() {
            @Override
            public void onSuccess(List<RoadInfo> list) {
                setUpMessage(list, roadMessage);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    private void initListView() {
        mListView = (ListView) root.findViewById(R.id.detail_list);

        GuideLineAdapter mAdapter = new GuideLineAdapter(getActivity());
        mAdapter.setCurRoute(routeActivity.getRouteName());
        // 设置正反
        mAdapter.setIsForward(routeActivity.isForward());
        // 初始化数据adapter并赋值
        mDataResult = getListData(routeActivity.getCurrentStart(), routeActivity.getCurrentEnd());

        if (mDataResult != null) {
            mAdapter.setData(mDataResult);
            mListView.setAdapter(mAdapter);
            mAdapter.setExpandableListener(new GuideLineAdapter.ExpandableListener() {
                @Override
                public void onExpand() {
                }

                @Override
                public void onCollapse() {
                }
            });
        }

        setHeaderView(mListView);
        setFooterView(mListView);
    }

    private void setFooterView(ListView listView) {

        if (listView.getFooterViewsCount() == 0) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View footerView = inflater.inflate(R.layout.guide_line_footer_view, null);
            listView.addFooterView(footerView);
        }

    }

    private void setHeaderView (ListView listView) {
        if (roadMessage == null) {
            roadMessage = getRoadMessageView();
        }
        if (overview == null) {
            overview = getOverviewView();
        }
        getRoadMessageInfo();
        getOverViewInfo();

        listView.removeHeaderView(roadMessage);
        listView.removeHeaderView(overview);

        listView.addHeaderView(roadMessage);
        listView.addHeaderView(overview);
    }

    private void getOverViewInfo() {
        TextView overviewDetail = (TextView) overview.findViewById(R.id.overview_detail);
        overviewDetail.setText(routeActivity.getCurrentPlan().getDescribe());
    }

    private View getOverviewView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View headerView = inflater.inflate(R.layout.guide_line_header_view, null);

        return headerView;
    }

    private ArrayList<Geocode> getListData(String start, String end) {

        // 根据起点终点获取数据
        String routeName = routeActivity.getCurrentRoute().getRoute();
        boolean isForward = routeActivity.isForward();
        ArrayList<Geocode> geocodes = (ArrayList<Geocode>) TTTApplication.getDbHelper().getNonPathMapGeocodeListWithNameAndRoute(routeName, start, end, isForward);

        return geocodes;
    }

    private ArrayList<String> getBelongList() {
        ArrayList<String> mBelongList = new ArrayList<>();
        if (mDataResult != null && mDataResult.size() > 0) {
            for (Geocode geocode : mDataResult) {
                mBelongList.add(geocode.getName());
            }
        }
        return mBelongList;
    }

    private void updateTimelineView() {
        initListView();
    }

    @Override
    public void update() {
        updateTimelineView();
    }
}
