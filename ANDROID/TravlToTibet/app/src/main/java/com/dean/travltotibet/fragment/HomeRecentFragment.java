package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.greendao.RecentRoute;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.RecentAdapter;
import com.dean.travltotibet.animator.CustomItemAnimator;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/10/15.
 */
public class HomeRecentFragment extends BaseHomeFragment {

    private View root;
    private ListView mListView;
    private RecentAdapter mAdapter;
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
        mActivity = (HomeActivity) getActivity();

        getRecentData();
        //initList();
        setUpList();
        //initDeleteBtn();
    }

    private void setUpList() {
        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.fragment_list_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new CustomItemAnimator());

        mAdapter = new RecentAdapter();

        if (recentRoutes != null) {
            mAdapter.setData(recentRoutes);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    /**
     * 初始化删除按钮，并设置监听
     */
//    private void initDeleteBtn() {
//        deleteBtn = root.findViewById(R.id.recent_delete_btn);
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final NormalDialog mDialog = new NormalDialog(getActivity(), R.style.Transparent_Dialog);
//                // 对话框视图
//                mDialog.setTitle(getString(R.string.delete_recent_title));
//                mDialog.setMsg(getString(R.string.delete_recent_msg));
//                mDialog.setOKListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TTTApplication.getDbHelper().cleanRecentRoutes();
//                        updateRecentData();
//                        mDialog.dismiss();
//                    }
//                });
//                mDialog.setCancelListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDialog.dismiss();
//                    }
//                });
//                mDialog.show();
//            }
//        });
//    }

    /**
     * 更新recentRoutes数据
     */
    public void updateRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
        mAdapter.setData(recentRoutes);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化列表
     */
//    private void initList() {
//        mListView = (ListView) root.findViewById(R.id.recent_list);
//        mAdapter = new RecentListAdapter(getActivity());
//
//        if (recentRoutes != null) {
//            mAdapter.setData(recentRoutes);
//            mListView.setAdapter(mAdapter);
//
//            // 设置列表点击监听事件
//            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    RecentRoute recentRoute = recentRoutes.get(position);
//
//                    // 跳转到RouteActivity
//                    Intent intent = new Intent(getActivity(), RouteActivity.class);
//                    intent.putExtra(Constants.INTENT_ROUTE, recentRoute.getRoute());
//                    intent.putExtra(Constants.INTENT_ROUTE_TYPE, recentRoute.getType());
//                    intent.putExtra(Constants.INTENT_ROUTE_DIR, recentRoute.getFR().equals("F") ? true : false);
//                    intent.putExtra(Constants.INTENT_ROUTE_PLAN_ID, Long.parseLong(recentRoute.getRoute_plan_id()));
//                    startActivity(intent);
//
////                    // 关闭菜单
////                    if (mActivity.getSlidingMenu().isMenuShowing()) {
////                        mActivity.getSlidingMenu().toggle();
////                    }
//
//                }
//            });
//            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                    // 长按删除单个视图
//                    return false;
//                }
//            });
//        }
//    }

    /**
     * 获取最近显示数据
     */
    private void getRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
    }


    @Override
    public void update() {
        updateRecentData();
    }
}
