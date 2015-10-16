package com.dean.travltotibet.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dean.greendao.RecentRoute;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.RecentListAdapter;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/10/15.
 */
public class HomeRecentFragment extends Fragment {

    private View root;
    private ListView mListView;
    private RecentListAdapter mAdapter;
    private ArrayList<RecentRoute> recentRoutes;
    private View deleteBtn;
    private HomeActivity mActivity;

    public HomeRecentFragment() {
    }

    public static HomeRecentFragment newInstance() {
        HomeRecentFragment fragment = new HomeRecentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.recent_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity)getActivity();

        getRecentData();
        initList();
        initDeleteBtn();
    }

    /**
     * 初始化删除按钮，并设置监听
     */
    private void initDeleteBtn() {
        deleteBtn = root.findViewById(R.id.recent_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.delete_recent_title)
                        .setMessage(R.string.delete_recent_msg)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TTTApplication.getDbHelper().cleanRecentRoutes();
                                updateRecentData();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null);
                builder.setCancelable(true);
                builder.create().show();
            }
        });
    }

    /**
     * 更新recentRoutes数据
     */
    public void updateRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
        mAdapter.setData(recentRoutes);
        mAdapter.notifyDataSetInvalidated();
    }

    /**
     * 初始化列表
     */
    private void initList() {
        mListView = (ListView) root.findViewById(R.id.recent_list);
        mAdapter = new RecentListAdapter(getActivity());

        if (recentRoutes != null) {
            mAdapter.setData(recentRoutes);
            mListView.setAdapter(mAdapter);

            // 设置列表点击监听事件
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RecentRoute recentRoute = recentRoutes.get(position);

                    // 跳转到RouteActivity
                    Intent intent = new Intent(getActivity(), RouteActivity.class);
                    intent.putExtra(Constants.INTENT_ROUTE, recentRoute.getRoute());
                    intent.putExtra(Constants.INTENT_ROUTE_TYPE, recentRoute.getType());
                    intent.putExtra(Constants.INTENT_ROUTE_DIR, recentRoute.getFR().equals("F") ? true : false);
                    intent.putExtra(Constants.INTENT_ROUTE_PLAN_ID, Long.parseLong(recentRoute.getRoute_plan_id()));
                    startActivity(intent);

                    // 关闭菜单
                    if (mActivity.getSlidingMenu().isMenuShowing()) {
                        mActivity.getSlidingMenu().toggle();
                    }

                }
            });
            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    // 长按删除单个视图
                    return false;
                }
            });
        }
    }

    /**
     * 获取最近显示数据
     */
    private void getRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
        for (RecentRoute recentRoute : recentRoutes) {
            Log.e("recentRoute", recentRoute.toString());
        }
    }


}
