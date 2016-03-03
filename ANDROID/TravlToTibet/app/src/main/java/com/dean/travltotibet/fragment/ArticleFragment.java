package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ArticleCommentActivity;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleFragment extends Fragment {

    private View root;

    private WebView mWebView;

    private View loadingView;

    private ArticleCommentActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.article_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (ArticleCommentActivity) this.getActivity();

        mWebView = (WebView) root.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new SimpleWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        loadingView = root.findViewById(R.id.loading_content_view);

        // 初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String url = mActivity.getArticle().getUrl();
        loadingView.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);
    }

    public class SimpleWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!isVisible()) {
                super.onPageStarted(view, url, favicon);
                return;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!isVisible()) {
                super.onPageFinished(view, url);
                return;
            }
            loadingView.setVisibility(View.GONE);
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
