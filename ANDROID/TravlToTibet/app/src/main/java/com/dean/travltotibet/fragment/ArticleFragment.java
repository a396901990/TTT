package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ArticleActivity;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.SystemUtil;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleFragment extends Fragment {

    private View root;

    private WebView mWebView;

    private ArticleActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.article_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (ArticleActivity) this.getActivity();

        mWebView = (WebView) root.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new SimpleWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        // 初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mActivity.getLoadingBackgroundManager().showLoadingView();
        String url = mActivity.getArticle().getUrl();
        mWebView.loadUrl(url);
    }

    public class SimpleWebViewClient extends WebViewClient {

        boolean isError;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!isVisible()) {
                super.onPageStarted(view, url, favicon);
                return;
            }
            isError = false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!isVisible()) {
                super.onPageFinished(view, url);
                return;
            }

            if (!isError) {
                mActivity.getLoadingBackgroundManager().loadingSuccess();
            } else {
                mActivity.getLoadingBackgroundManager().loadingFaild(getString(R.string.network_no_result), new LoadingBackgroundManager.LoadingRetryCallBack() {
                    @Override
                    public void retry() {
                        initData();
                    }
                });
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            isError = true;
        }
    }

    @Override
    public void onDestroy() {
        mWebView.loadUrl(Constants.EMPTY_HTML_CONTENT);
        mWebView.clearFormData();
        mWebView.clearHistory();
        mWebView.clearMatches();
        // when set android:noHistory="true",
        // It will not remain in the activity stack for the task, so the user will not be able to return to it
        // mWebView.destroy();
        mWebView = null;
        super.onDestroy();
    }

}
