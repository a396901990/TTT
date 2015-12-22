package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.chart.AbstractPoint;
import com.dean.travltotibet.ui.chart.AbstractSeries;
import com.dean.travltotibet.ui.chart.IndicatorSeries;
import com.dean.travltotibet.ui.chart.MountainSeries;
import com.dean.travltotibet.ui.chart.IndicatorChartView;
import com.dean.travltotibet.ui.chart.RouteChartView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.ui.chart.PointManager;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.StringUtil;

import java.util.List;

/**
 * Created by DeanGuo on 12/12/15.
 */
public class FullChartFragment extends Fragment {

    private static final int BORDER_EXTRA_LENGTH = 40;

    private View rootView;

    private RouteChartView mChartView;

    private IndicatorChartView mIndicatorView;

    private MountainSeries series;

    private IndicatorSeries indicatorSeries;

    // 当前计划
    private String planStart;
    private String planEnd;

    // 当前线路名称
    private String routeName;

    // 当前线路类型
    private String routeType;

    // 当前是否向前，也就是正向反向 f/r
    private boolean isForward;

    public static FullChartFragment newInstance() {
        return new FullChartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chart_fragment_view, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            routeName = bundle.getString(IntentExtra.INTENT_ROUTE);
            routeType = bundle.getString(IntentExtra.INTENT_ROUTE_TYPE);
            isForward = bundle.getBoolean(IntentExtra.INTENT_ROUTE_DIR, true);
            planStart = bundle.getString(IntentExtra.INTENT_PLAN_START);
            planEnd = bundle.getString(IntentExtra.INTENT_PLAN_END);
        }

        mChartView = (RouteChartView) rootView.findViewById(R.id.chart);
        mIndicatorView = (IndicatorChartView) rootView.findViewById(R.id.indicator);
        mIndicatorView.setChartView(mChartView);

        initChartRoute();
    }

    /**
     * 更新图标细节
     */
    public void updateChartDetail(String name) {
        TextView posName = (TextView) rootView.findViewById(R.id.header_position_name);
        TextView posHeight = (TextView) rootView.findViewById(R.id.header_position_height);
        TextView posMilestone = (TextView) rootView.findViewById(R.id.header_position_mileage);

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
            String milestone = TTTApplication.getDbHelper().getMilestoneWithName(name);
            milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, road, milestone);

            posName.setText(name);
            posHeight.setText(height);
            posMilestone.setText(milestone);
        }
        // 切换时清空
        else {
            if (posName != null) {
                posName.setText("");
            }
            if (posHeight != null) {
                posHeight.setText("");
            }
            if (posMilestone != null) {
                posMilestone.setText("");
            }
        }
    }

    /**
     * 更新chart视图路线
     */
    public void initChartRoute() {

        List<Geocode> geocodes = TTTApplication.getDbHelper().getGeocodeListWithNameAndRoute(routeName, planStart, planEnd, isForward);

        series = new MountainSeries();
        indicatorSeries = new IndicatorSeries();

        double mileage = 0;

        for (int i = 0; i < geocodes.size(); i++) {
            Geocode geocode = geocodes.get(i);

            if (i == 0 || i == geocodes.size() - 1) {
                series.addPoint(new MountainSeries.MountainPoint((int) mileage, (int) geocode.getElevation(), geocode.getName(), PointManager.START_END));
            } else {
                series.addPoint(new MountainSeries.MountainPoint((int) mileage, (int) geocode.getElevation(), geocode.getName(), geocode.getTypes()));
            }
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
                mChartView.setAxisRange(-border / BORDER_EXTRA_LENGTH, 0, border + border / BORDER_EXTRA_LENGTH, 7000);
                //mChartView.setAxisRange(-5, 0, border + 5, 6500);
                //mIndicatorView.setCurrentViewport(mChartView.getCurrentViewport()); // ??????????????????
            }
        });
        //mChartView.setAxisRange(-5, 0, border+5, 6500);
        mChartView.setAxisRange(-border / BORDER_EXTRA_LENGTH, 0, border + border / BORDER_EXTRA_LENGTH, 6500);
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

}
