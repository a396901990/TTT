package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dean.travltotibet.util.ProgressUtil;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseRouteFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isVisible) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected void lazyLoad() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //显示加载进度对话框
                ProgressUtil.setLastShown(ProgressDialog.show(getActivity(), null, null));
                onLoadPrepared();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                    onLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccess) {
                if (isSuccess) {
                    // 加载成功
                    onLoadFinished();
                } else {
                    // 加载失败
                }
                //关闭对话框
                ProgressUtil.dismissLast();
                //DialogUtil.hideDialogForLoading();
            }
        }.execute();
    }

    protected abstract void onLoadPrepared();

    protected abstract void onLoading();

    protected abstract void onLoadFinished();

    /**
     * 跟新fragment路线
     */
    public abstract void updateRoute();

}
