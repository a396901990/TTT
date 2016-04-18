package com.dean.travltotibet.base;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseLoadingFragment extends Fragment {

    public final static int PREPARE_LOADING = 0;

    public final static int ON_LOADING = 1;

    public final static int LOADING_SUCCESS = 2;

    public final static int LOADING_ERROR = 3;

    public final static int ON_LOADING_MORE = 4;

    public final static int LOADING_MORE_SUCCESS = 5;

    public final static int LOADING_MORE_ERROR = 6;

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

    public void prepareLoading() {
        if (getActivity() == null){
            return;
        }
    }

    public void onLoading() {
        if (getActivity() == null){
            return;
        }
    }

    public void LoadingSuccess() {
        if (getActivity() == null){
            return;
        }
    }

    public void LoadingError() {
        if (getActivity() == null){
            return;
        }
    }

    public void onLoadingMore() {
        if (getActivity() == null){
            return;
        }
    }

    public void LoadingMoreSuccess() {
        if (getActivity() == null){
            return;
        }
    }

    public void LoadingMoreError() {
        if (getActivity() == null){
            return;
        }
    }

    public void toDo(int message, long delayed) {
        if (getActivity() != null && mHandle != null) {
            mHandle.sendEmptyMessageDelayed(message, delayed);
        }
    }

}
