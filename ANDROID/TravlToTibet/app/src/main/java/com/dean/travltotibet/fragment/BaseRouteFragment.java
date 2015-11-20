package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.dean.travltotibet.ui.CustomProgress;
import com.dean.travltotibet.util.ProgressUtil;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseRouteFragment extends Fragment {

    public final static int LOADING_PREPARED = 0;
    public final static int LOADING = 1;
    public final static int LOADING_SUCCESS = 2;
    public final static int LOADING_FAILED = 3;

    private Handler mHandler;

    // 标志位，标志是否可见
    private boolean isVisible = false;
    // 标志位，标志是否初始化完成
    private boolean isPrepared = false;
    // 是否已被加载过一次，第二次就不再去请求数据了
    private boolean mHasLoadedOnce = false;

    // 是否是map视图
    private boolean isMapLoading = false;

    // map线程，控制map视图加载
    private MapLoadingThread mapThread;

    /**
     * Fragment当前状态是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }

        lazyLoad();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isPrepared = true;

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    // 准备加载
                    case LOADING_PREPARED:
                        CustomProgress.show(getActivity(), false);
                        //ProgressUtil.setLastShown(new ProgressDialog(getActivity(), android.R.style.Theme_Holo_Light_DialogWhenLarge).show(getActivity(), null, null));
                        onLoadPrepared();
                        break;

                    // 加载中
                    case LOADING:
                        onLoading();
                        break;

                    // 加载成功
                    case LOADING_SUCCESS:
                        //ProgressUtil.dismissLast();
                        CustomProgress.dismissDialog();
                        onLoadFinished();
                        break;

                    // 加载失败
                    case LOADING_FAILED:
                        ProgressUtil.dismissLast();
                        break;

                    default:
                        break;
                }
            }
        };
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
                        mHandler.sendEmptyMessage(LOADING_PREPARED);
                    }

                    @Override
                    protected Boolean doInBackground(Void... params) {
                        // 加载中
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(LOADING);
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean isSuccess) {
                        if (isSuccess) {
                            mHasLoadedOnce = true;
                            // 加载成功
                            mHandler.sendEmptyMessage(LOADING_SUCCESS);
                        } else {
                            // 加载失败
                            mHandler.sendEmptyMessage(LOADING_FAILED);
                        }
                    }
                }.execute();
            }
        });
    }

    private void mapLoading() {
        mapThread = new MapLoadingThread();
        synchronized (mapThread) {
            try {
                // 启动“线程”
                mapThread.start();
                // 主线程等待, 通过notify()唤醒
                mapThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void mapLoadingFinished() {
        synchronized (mapThread) {
            mapThread.notify();
        }
    }

    public boolean isLoaded() {
        return isPrepared && isVisible && mHasLoadedOnce;
    }

    public void setMapLoading(boolean isMapLoading) {
        this.isMapLoading = isMapLoading;
    }

    protected abstract void onLoadPrepared();

    protected abstract void onLoading();

    protected abstract void onLoadFinished();

    /**
     * 跟新fragment路线
     */
    public abstract void updateRoute();

    public class MapLoadingThread extends Thread {
        public void run() {
            // 死循环，不断运行
            while (true) ;
        }
    }
}
