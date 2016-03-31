package com.dean.travltotibet.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by DeanGuo on 3/31/16.
 */
public class LoadingManager {

    private ProgressDialog mProgressDialog;

    private Handler mHandle;

    private Context mContext;

    private LoadingSuccessCallBack loadingSuccessCallBack;

    private LoadingFailedCallBack loadingFailedCallBack;

    private final static int SHOW_DIALOG = 0;
    private final static int DISMISS_DIALOG_SUCCESS = 1;
    private final static int DISMISS_DIALOG_FAILURE = 2;
    private final static int SUBMIT_SUCCESS = 3;
    private final static int SUBMIT_FAILURE = 4;

    public static interface LoadingSuccessCallBack {
        public void afterLoadingSuccess();
    }

    public static interface LoadingFailedCallBack {
        public void afterLoadingFailed();
    }

    public LoadingManager(Context context) {
        mContext = context;
        mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case SHOW_DIALOG:
                        if (mProgressDialog == null || !mProgressDialog.isShowing()) {
                            mProgressDialog = new ProgressDialog(mContext);
                            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            mProgressDialog.setMessage("正在提交...");
                            mProgressDialog.setCancelable(false);
                            mProgressDialog.show();
                        }
                        break;
                    case DISMISS_DIALOG_SUCCESS:
                        if (mContext != null && mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        if (loadingSuccessCallBack != null) {
                            loadingSuccessCallBack.afterLoadingSuccess();
                        }
                        break;
                    case SUBMIT_SUCCESS:
                        mProgressDialog.setMessage("提交成功！");
                        mHandle.sendEmptyMessageDelayed(DISMISS_DIALOG_SUCCESS, 1000);
                        break;
                    case SUBMIT_FAILURE:
                        mProgressDialog.setMessage("提交失败！");
                        mHandle.sendEmptyMessageDelayed(DISMISS_DIALOG_FAILURE, 1000);
                        break;
                    case DISMISS_DIALOG_FAILURE:
                        if (mContext != null && mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        if (loadingFailedCallBack != null) {
                            loadingFailedCallBack.afterLoadingFailed();
                        }
                        break;
                }
            }
        };
    }

    public void showLoading() {
        if (mHandle != null) {
            mHandle.sendEmptyMessage(SHOW_DIALOG);
        }
    }

    public void loadingSuccess(LoadingSuccessCallBack loadingSuccessCallBack) {
        setLoadingSuccessCallBack(loadingSuccessCallBack);
        if (mHandle != null) {
            mHandle.sendEmptyMessage(SUBMIT_SUCCESS);
        }
    }

    public void loadingFailed(LoadingFailedCallBack loadingFailedCallBack) {
        setLoadingFailedCallBack(loadingFailedCallBack);
        if (mHandle != null) {
            mHandle.sendEmptyMessage(SUBMIT_FAILURE);
        }
    }

    public LoadingSuccessCallBack getLoadingSuccessCallBack() {
        return loadingSuccessCallBack;
    }

    public void setLoadingSuccessCallBack(LoadingSuccessCallBack loadingSuccessCallBack) {
        this.loadingSuccessCallBack = loadingSuccessCallBack;
    }

    public LoadingFailedCallBack getLoadingFailedCallBack() {
        return loadingFailedCallBack;
    }

    public void setLoadingFailedCallBack(LoadingFailedCallBack loadingFailedCallBack) {
        this.loadingFailedCallBack = loadingFailedCallBack;
    }
}