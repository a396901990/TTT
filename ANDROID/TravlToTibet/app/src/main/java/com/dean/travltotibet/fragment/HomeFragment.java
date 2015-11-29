package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.activity.WhereGoActivity;
import com.dean.travltotibet.adapter.HomeGridAdapter;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.DefaultHeaderTransformer;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by DeanGuo on 10/15/15.
 */
public class HomeFragment extends BaseHomeFragment implements OnRefreshListener {

    private View root;
    private GridView gridView;
    private HomeGridAdapter adapter;
    private ArrayList<Route> routes;
    private HomeActivity mActivity;
    private PullToRefreshLayout mPullToRefreshLayout;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();

        getRouteData();
        initGridView();
        initPullToRefresh();
        initWhereGo();
    }

    private void initWhereGo() {
        View whereGoBtn = root.findViewById(R.id.where_go);
        whereGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WhereGoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initPullToRefresh() {
        mPullToRefreshLayout = (PullToRefreshLayout) root.findViewById(R.id.pull_to_refresh_view);
        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefreshLayout);

        // 设置ProgressBar颜色为白色
        DefaultHeaderTransformer transformer = (DefaultHeaderTransformer) mPullToRefreshLayout.getHeaderTransformer();
        transformer.setProgressBarColor(getResources().getColor(R.color.white_background));
    }

    private void initGridView() {
        gridView = (GridView) root.findViewById(R.id.gridView);
        adapter = new HomeGridAdapter(getActivity());
        adapter.setData(routes);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // 显示选择类型对话框
//                DialogFragment dialogFragment = new TravelTypeDialog();
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.INTENT_ROUTE, routes.get(position).getRoute());
//                bundle.putString(Constants.INTENT_ROUTE_NAME, routes.get(position).getName());
//                bundle.putString(Constants.INTENT_FROM_WHERE, TravelTypeDialog.FROM_FIRST);
//                dialogFragment.setArguments(bundle);
//                dialogFragment.show(getFragmentManager(), TravelTypeDialog.class.getName());

                // 跳转到InfoRouteActivity(类型BIKE)
                Intent intent = new Intent(getActivity(), InfoRouteActivity.class);
                intent.putExtra(Constants.INTENT_ROUTE, routes.get(position).getRoute());
                intent.putExtra(Constants.INTENT_ROUTE_NAME, routes.get(position).getName());
                intent.putExtra(Constants.INTENT_ROUTE_TYPE, TravelType.BIKE);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取route数据
     */
    private void getRouteData() {
        routes = (ArrayList<Route>) TTTApplication.getDbHelper().getRoutsList();
    }

    /**
     * 刷新视图
     */
    @Override
    public void onRefreshStarted(View view) {
        mPullToRefreshLayout.setRefreshing(true);
        // Simulate a long running activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setData(routes);
                mPullToRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void update() {

    }
}
