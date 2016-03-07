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
public abstract class BaseHomeFragment extends Fragment {

    public final static int PREPARE_LOADING = 0;

    public final static int ON_LOADING = 1;

    public final static int LOADING_SUCCESS = 2;

    public final static int LOADING_ERROR = 3;

    public abstract void update();

    public abstract void refresh();

    public abstract void prepareLoading();

    public abstract void onLoading();

    public abstract void LoadingSuccess();

    public abstract void LoadingError();

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
                }
            }
        };
    }

    public void beginTodo(int message, long delayed) {
        mHandle.sendEmptyMessageDelayed(message, delayed);
    }

}
