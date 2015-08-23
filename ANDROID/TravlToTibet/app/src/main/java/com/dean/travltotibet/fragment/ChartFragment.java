package com.dean.travltotibet.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.greendao.Routes;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ChartActivity;
import com.dean.travltotibet.adapter.PlanSpinnerAdapter;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.model.AbstractSeries;
import com.dean.travltotibet.model.IndicatorSeries;
import com.dean.travltotibet.model.MountainSeries;
import com.dean.travltotibet.model.Place;
import com.dean.travltotibet.ui.IndicatorChartView;
import com.dean.travltotibet.ui.RouteChartView;
import com.dean.travltotibet.util.ChartCrosshairUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class ChartFragment extends Fragment implements RouteFragment.RouteListener{

    private View root;

    private RouteChartView mChartView;

    private IndicatorChartView mIndicatorView;

    private View mHeaderView;

    private MountainSeries series;

    private IndicatorSeries indicatorSeries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.chart_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 初始化视图
        mHeaderView = root.findViewById(R.id.chart_header_view);
        mChartView = (RouteChartView) root.findViewById(R.id.chart);
        mIndicatorView = (IndicatorChartView) root.findViewById(R.id.indicator);
        mIndicatorView.setChartView(mChartView);

        // 初始化header menu两侧按钮
        initHeaderButton();

        // 根据路线的起始和终点 获取数据
        List<Geocode> geocodes = TTTApplication.getDbHelper().getGeocodeList();

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

        // 重置图标视图数据
        mChartView.setAxisRange(-30, 0, pointLength + 30, 6500);
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
                updateHeader(point);
            }
        });
        mChartView.setPointListener(new AbstractSeries.PointListener() {

            @Override
            public void pointOnTouched(AbstractPoint point) {
                updateHeader(point);
            }
        });
    }

    /**
     * 初始化header左右两侧按钮
     */
    private void initHeaderButton() {
        Button menuBtn = (Button) mHeaderView.findViewById(R.id.menu_btn);
        Button routeBtn = (Button) mHeaderView.findViewById(R.id.route_btn);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChartActivity) getActivity()).showMenu();
            }
        });

        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChartActivity) getActivity()).showSecondaryMenu();
            }
        });

    }

    /**
     * 更新标题头
     */
    protected void updateHeader(AbstractPoint point) {
        TextView posName = (TextView) mHeaderView.findViewById(R.id.header_position_name);
        TextView posHeight = (TextView) mHeaderView.findViewById(R.id.header_position_height);
        TextView posMileage = (TextView) mHeaderView.findViewById(R.id.header_position_mileage);

        Place place = series.getPlace(point);

        if (place != null) {
            posHeight.setText(place.getHeight());
            posMileage.setText(place.getMileage());
            posName.setText(place.getName());
        }

    }

    /**
     * 初始化下拉菜单
     */
    private void initDropdownNavigation() {
        ArrayList<PlanSpinnerAdapter.PlanNavItem> mPlans = new ArrayList<PlanSpinnerAdapter.PlanNavItem>();

        // 获取数据库路线
        List<Routes> routes = TTTApplication.getDbHelper().getRoutsList();

        // 为下拉菜单赋值
        // 第一个初始默认，每次加载会调用第一个的onItemSelected方法
        mPlans.add(new PlanSpinnerAdapter.PlanNavItem("新藏线", "叶城县", "拉萨", "2793KM"));
        // 其他路线
        for (Routes r : routes) {
            mPlans.add(new PlanSpinnerAdapter.PlanNavItem("D" + r.getId(), r.getStart(), r.getEnd(), r.getDistance()));
        }

        PlanSpinnerAdapter adapter = new PlanSpinnerAdapter(getActivity());
        adapter.setData(mPlans);

        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        final View spinnerView = mInflater.inflate(R.layout.plan_spinner, null);
        Spinner spinner = (Spinner) spinnerView.findViewById(R.id.plan_spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PlanSpinnerAdapter.PlanNavItem planNavItem = (PlanSpinnerAdapter.PlanNavItem) parent.getItemAtPosition(position);
                String date = planNavItem.getPlanDate();
                String start = planNavItem.getPlanDetailStart();
                String end = planNavItem.getPlanDetailEnd();

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

                // 重置图标视图数据
                mChartView.setAxisRange(-30, 0, pointLength + 30, 6500);
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
                        updateHeader(point);
                    }
                });
                mChartView.setPointListener(new AbstractSeries.PointListener() {

                    @Override
                    public void pointOnTouched(AbstractPoint point) {
                        updateHeader(point);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.RIGHT;
        layoutParams.rightMargin = 5;
        getActivity().getActionBar().setCustomView(spinnerView, layoutParams);
        getActivity().getActionBar().setDisplayShowCustomEnabled(true);
    }

    @Override
    public void updateRoute(List<Geocode> geocodes) {
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

        // 重置图标视图数据
        mChartView.setAxisRange(-30, 0, pointLength + 30, 6500);
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
                updateHeader(point);
            }
        });
        mChartView.setPointListener(new AbstractSeries.PointListener() {

            @Override
            public void pointOnTouched(AbstractPoint point) {
                updateHeader(point);
            }
        });
    }
}
