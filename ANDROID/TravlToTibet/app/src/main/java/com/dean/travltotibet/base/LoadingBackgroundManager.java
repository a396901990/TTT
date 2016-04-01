package com.dean.travltotibet.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 4/1/16.
 */
public class LoadingBackgroundManager {

    private View loadingView;

    private View loadingProgressView;

    private View loadingNoResultView;

    private Context context;

    private LoadingRetryCallBack loadingRetryCallBack;

    public static interface LoadingRetryCallBack {
        public void retry();
    }

    public LoadingBackgroundManager(Context context, ViewGroup parentView) {
        this.context = context;
        initView();
        parentView.addView(loadingView);
    }

    private void initView() {
        loadingView = LayoutInflater.from(context).inflate(R.layout.loading_background_view, null);
        loadingProgressView = loadingView.findViewById(R.id.loading_progress_view);
        loadingNoResultView = loadingView.findViewById(R.id.no_result_view);
        loadingNoResultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadingRetryCallBack != null) {
                    loadingRetryCallBack.retry();
                }
            }
        });
    }

    public void resetLoadingView() {
        loadingView.setVisibility(View.GONE);
        loadingProgressView.setVisibility(View.GONE);
        loadingNoResultView.setVisibility(View.GONE);
    }

    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
        loadingProgressView.setVisibility(View.VISIBLE);
        loadingNoResultView.setVisibility(View.GONE);
    }

    public void loadingSuccess() {
        loadingView.setVisibility(View.GONE);
        loadingProgressView.setVisibility(View.GONE);
        loadingNoResultView.setVisibility(View.GONE);
    }

    public void loadingFaild(String showText, LoadingRetryCallBack loadingRetryCallBack) {
        setLoadingRetryCallBack(loadingRetryCallBack);

        loadingView.setVisibility(View.VISIBLE);
        loadingProgressView.setVisibility(View.GONE);
        loadingNoResultView.setVisibility(View.VISIBLE);

        // 设置显示文字
        TextView noResultText = (TextView) loadingView.findViewById(R.id.no_result_text);
        if (noResultText != null && !TextUtils.isEmpty(showText)) {
            noResultText.setText(showText);
        }
    }

    public LoadingRetryCallBack getLoadingRetryCallBack() {
        return loadingRetryCallBack;
    }

    public void setLoadingRetryCallBack(LoadingRetryCallBack loadingRetryCallBack) {
        this.loadingRetryCallBack = loadingRetryCallBack;
    }
}
