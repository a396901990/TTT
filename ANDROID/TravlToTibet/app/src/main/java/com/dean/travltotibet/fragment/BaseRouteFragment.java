package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.CustomProgress;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.ProgressUtil;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseRouteFragment extends Fragment {

    // 标志位，标志是否可见
    private boolean isVisible = false;
    // 标志位，标志是否初始化完成
    private boolean isPrepared = false;
    // 是否已被加载过一次，第二次就不再去请求数据了
    private boolean mHasLoadedOnce = false;

    private String currentPlan;

    /**
     * Fragment当前状态是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            if (getUserVisibleHint()) {
                isVisible = true;
            } else {
                isVisible = false;
            }
        }

        lazyLoad();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isPrepared = true;

        lazyLoad();
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected void lazyLoad() {

        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<Void, Void, Boolean>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        // 准备加载
                        currentPlan = ((RouteActivity) getActivity()).getPlanStart();
                        CustomProgress.show(getActivity(), false);
                        onLoadPrepared();
                    }

                    @Override
                    protected Boolean doInBackground(Void... params) {
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onLoading();
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean isSuccess) {
                        if (isSuccess) {
                            mHasLoadedOnce = true;
                            // 加载成功
                            onLoadFinished();
                            CustomProgress.dismissDialog();
                        } else {
                            // 加载失败
                            CustomProgress.dismissDialog();
                        }
                    }
                }.execute();
            }
        });
    }

    public boolean isLoaded() {
        return isPrepared && isVisible && mHasLoadedOnce;
    }

    protected abstract void onLoadPrepared();

    protected abstract void onLoading();

    protected abstract void onLoadFinished();

    /**
     * 跟新fragment路线
     */
    public abstract void updateRoute();

    /**
     * fragment菜单按钮
     */
    public abstract void fabBtnEvent();

    /**
     * 初始化menu
     * @param menu
     */
    public void initMenu(final FloatingActionMenu menu) {
        menu.removeAllMenuButtons();

        menu.showMenu(true);
        menu.hideMenuButton(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                menu.showMenuButton(true);
            }
        }, 700);
    }

    public void setCurrentPlan(String currentPlan, String end) {
        this.currentPlan = currentPlan + end;
    }

    public boolean isCurrentPlan(String planStart, String planEnd) {
        return currentPlan.equals(planStart + planEnd);
    }
}
