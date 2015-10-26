package com.dean.travltotibet.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.model.AbstractSeries;
import com.dean.travltotibet.model.IndicatorSeries;
import com.dean.travltotibet.model.MountainSeries;
import com.dean.travltotibet.ui.IndicatorChartView;
import com.dean.travltotibet.ui.RouteChartView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ProgressUtil;
import com.dean.travltotibet.util.StringUtil;

import java.util.List;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class RouteChartFragment extends BaseRouteFragment {

    private View root;

    private RouteChartView mChartView;

    private IndicatorChartView mIndicatorView;

    private MountainSeries series;

    private IndicatorSeries indicatorSeries;

    private RouteActivity routeActivity;

    public static RouteChartFragment newInstance() {
        return new RouteChartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.chart_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        // 初始化视图
        mChartView = (RouteChartView) root.findViewById(R.id.chart);
        mIndicatorView = (IndicatorChartView) root.findViewById(R.id.indicator);
        mIndicatorView.setChartView(mChartView);

        updateChartRoute();
    }

    /**
     * 更新图标细节
     */
    public void updateChartDetail(String name) {
        TextView posName = (TextView) root.findViewById(R.id.header_position_name);
        TextView posHeight = (TextView) root.findViewById(R.id.header_position_height);
        TextView posMilestone = (TextView) root.findViewById(R.id.header_position_mileage);

        if (name != null) {
            // 高度
            String height = StringUtil.formatDoubleToInteger(TTTApplication.getDbHelper().getElevationWithName(name));
            height = String.format(Constants.GUIDE_OVERALL_HEIGHT_FORMAT, height);

            // 路牌
            String road = TTTApplication.getDbHelper().getRoadWithName(name);
            if (!TextUtils.isEmpty(road)) {
                road = road.split("/")[1];
            }

            // 里程碑
            String milestone = StringUtil.formatDoubleToFourInteger(TTTApplication.getDbHelper().getMilestoneWithName(name));
            milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, road, milestone);

            posName.setText(name);
            posHeight.setText(height);
            posMilestone.setText(milestone);
        }
    }

    /**
     * 更新chart视图路线
     */
    public void updateChartRoute() {

        // 根据路线的起始和终点 获取数据
        String routeName = routeActivity.getCurrentRoute().getRoute();
        String planStart = routeActivity.getPlanStart();
        String planEnd = routeActivity.getPlanEnd();
        boolean isForward = routeActivity.isForward();

        List<Geocode> geocodes = TTTApplication.getDbHelper().getGeocodeListWithNameAndRoute(routeName, planStart, planEnd, isForward);

        series = new MountainSeries();
        indicatorSeries = new IndicatorSeries();

        double mileage = 0;

        for (int i = 0; i < geocodes.size(); i++) {
            Geocode geocode = geocodes.get(i);

            series.addPoint(new MountainSeries.MountainPoint((int) mileage, (int) geocode.getElevation(), geocode.getName(), AbstractSeries.getType(geocode.getTypes())));
            indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint((int) mileage, (int) geocode.getElevation()));

            // 最后一个位置不需要进行计算，根据距离计算每个点得距离长度
            if (i < geocodes.size() - 2) {
                double distance = isForward ? geocode.getF_distance() : geocode.getR_distance();
                mileage = mileage + distance;
            }
        }

        series.initPaint();

        final float border = (float) mileage;

        // 另起一个子线程更新视窗大小变换，修复切换fragment重新加载bug
        mChartView.post(new Runnable() {
            @Override
            public void run() {
                // 重置图标视图数据
                // 远离屏幕左右间隔是起点终点长的1/10
                mChartView.setAxisRange(-border / 10, 0, border + border / 10, 6500);
                //mIndicatorView.setCurrentViewport(mChartView.getCurrentViewport()); // ??????????????????
            }
        });
        mChartView.setAxisRange(-border / 10, 0, border + border / 10, 6500);
        mChartView.addSeries(series);
        mChartView.initCrosshair();

        // 重置指示视图数据
        mIndicatorView.addSeries(indicatorSeries);
        mIndicatorView.initIndicator();
        mIndicatorView.setChartView(mChartView);

        // 设置监听
        mChartView.setPointListener(new AbstractSeries.PointListener() {

            @Override
            public void pointOnTouched(AbstractPoint point) {
                String placeName = series.getPointName(point);
                updateChartDetail(placeName);
            }
        });
    }

    @Override
    protected void onLoadPrepared() {

    }

    @Override
    protected void onLoading() {

    }

    @Override
    protected void onLoadFinished() {

    }

    @Override
    public void updateRoute() {
        updateChartRoute();
    }
}
