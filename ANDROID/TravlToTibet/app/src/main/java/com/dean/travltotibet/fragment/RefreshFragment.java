package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.CustomProgress;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class RefreshFragment extends Fragment {

    public final static int PREPARE_LOADING = 0;

    public final static int ON_LOADING = 1;

    public final static int LOADING_SUCCESS = 2;

    public final static int LOADING_ERROR = 3;

    public final static int ON_LOADING_MORE = 4;

    public final static int LOADING_MORE_SUCCESS = 5;

    public final static int LOADING_MORE_ERROR = 6;

    public static final int STATE_REFRESH = 999;// 下拉刷新
    public static final int STATE_MORE = 998;// 加载更多

    public abstract void update();

    public abstract void refresh();

    public abstract void prepareLoading();

    public abstract void onLoading();

    public abstract void LoadingSuccess();

    public abstract void LoadingError();

    public abstract void onLoadingMore();

    public abstract void LoadingMoreSuccess();

    public abstract void LoadingMoreError();

    private Handler mHandle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case PREPARE_LOADING:
                        prepareLoading();
                        break;
                    case ON_LOADING:
                        onLoading();
                        break;
                    case LOADING_SUCCESS:
                        LoadingSuccess();
                        break;
                    case LOADING_ERROR:
                        LoadingError();
                        break;
                    case ON_LOADING_MORE:
                        onLoadingMore();
                        break;
                    case LOADING_MORE_SUCCESS:
                        LoadingMoreSuccess();
                        break;
                    case LOADING_MORE_ERROR:
                        LoadingMoreError();
                        break;
                }
            }
        };
    }

    public void toDo(int message, long delayed) {
        if (getActivity() != null) {
            mHandle.sendEmptyMessageDelayed(message, delayed);
        }
    }

}
