package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.dean.travltotibet.R;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ScreenUtil;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by DeanGuo on 3/12/16.
 */
public class ShowHtmlDialogFragment extends DialogFragment {

    private View root;

    private WebView mWebView;

    private View loadingView;

    private String url;

    private String shareUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.show_html_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWebView = (WebView) root.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new SimpleWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        loadingView = root.findViewById(R.id.loading_content_view);

        if (!TextUtils.isEmpty(shareUrl)) {
            initShareBtn();
        } else {
            initDismissBtn();
        }
        // 初始化数据
        initData();
    }

    private void initShareBtn() {
        View bottomView = root.findViewById(R.id.bottom_content_view);
        Button bottomTextView = (Button) root.findViewById(R.id.commit_btn);
        bottomTextView.setText("分享");
        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
            }
        });
    }

    private void initDismissBtn() {
        View bottomView = root.findViewById(R.id.bottom_content_view);
        Button bottomTextView = (Button) root.findViewById(R.id.commit_btn);
        bottomTextView.setText("知道了");
        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (getDialog() != null) {
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        //window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(getActivity(), 330));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, ScreenUtil.dip2px(getActivity(), 400));
    }

    /**
     * 初始化数据
     */
    private void initData() {
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

    public void setUrl(String url) {
        this.url = url;
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

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
