package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.model.AbstractSeries;
import com.dean.travltotibet.model.IndicatorSeries;
import com.dean.travltotibet.model.MountainSeries;
import com.dean.travltotibet.model.Place;
import com.dean.travltotibet.ui.IndicatorChartView;
import com.dean.travltotibet.ui.RouteChartView;
import com.dean.travltotibet.util.ChartCrosshairUtil;

import java.util.List;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class ChartFragment extends BaseRouteFragment {

    private View root;

    private RouteChartView mChartView;

    private IndicatorChartView mIndicatorView;

    private MountainSeries series;

    private IndicatorSeries indicatorSeries;

    private RouteActivity routeActivity;

    public static ChartFragment newInstance() {
        return new ChartFragment();
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

        updateChartRoute("叶城县", "拉萨");
        // need improve
        routeActivity.updateHeader("叶城县", "拉萨", "新藏线","2579M");
    }

//    /**
//     * 更新标题头
//     */
//    protected void updateHeader(AbstractPoint point) {
//        TextView posName = (TextView) mHeaderView.findViewById(R.id.header_position_name);
//        TextView posHeight = (TextView) mHeaderView.findViewById(R.id.header_position_height);
//        TextView posMileage = (TextView) mHeaderView.findViewById(R.id.header_position_mileage);
//
//        Place place = series.getPlace(point);
//
//        if (place != null) {
//            posHeight.setText(place.getHeight());
//            posMileage.setText(place.getMileage());
//            posName.setText(place.getName());
//        }
//
//    }

    /**
     * 更新chart视图路线
     *
     * @param start 初始点
     * @param end   终点
     */
    public void updateChartRoute(String start, String end) {

        // 根据路线的起始和终点 获取数据
        List<Geocode> geocodes = TTTApplication.getDbHelper().getGeocodeListWithName(start, end);

        series = new MountainSeries();
        indicatorSeries = new IndicatorSeries();

        // 计算两个路线点起点和终点的间距，使路线在屏幕中间
        float firstPointLength = (float) geocodes.get(0).getMileage();
        float lastPointLength = (float) geocodes.get(geocodes.size() - 1).getMileage();
        float pointLength = lastPointLength - firstPointLength;

        for (Geocode geocode : geocodes) {
            series.addPoint(new MountainSeries.MountainPoint((int) geocode.getMileage() - firstPointLength, (int) geocode.getElevation(), geocode.getName(), AbstractSeries.getType(geocode.getTypes())));
            indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint((int) geocode.getMileage() - firstPointLength, (int) geocode.getElevation()));
        }

        series.initPaint();

        // 重置图标视图数据
        // 远离屏幕左右间隔是起点终点长的1/10
        mChartView.setAxisRange(-pointLength / 10, 0, pointLength + pointLength / 10, 6500);
        mChartView.addSeries(series);
        mChartView.initCrosshair();

        // 重置指示视图数据
        mIndicatorView.addSeries(indicatorSeries);
        mIndicatorView.initIndicator();
        mIndicatorView.setChartView(mChartView);

        // 设置监听
        mChartView.addCrosshairPaintedListener(new ChartCrosshairUtil.OnCrosshairPainted() {

            @Override
            public void onCrosshairPainted(AbstractPoint point) {
                Place place = series.getPlace(point);
                routeActivity.updateHeader(place);
            }
        });
        mChartView.setPointListener(new AbstractSeries.PointListener() {

            @Override
            public void pointOnTouched(AbstractPoint point) {
                Place place = series.getPlace(point);
                routeActivity.updateHeader(place);
            }
        });
    }

    @Override
    public void updateRoute(String start, String end, String date, String distance) {
        super.updateRoute(start, end, date, distance);
        updateChartRoute(start, end);
    }
}
