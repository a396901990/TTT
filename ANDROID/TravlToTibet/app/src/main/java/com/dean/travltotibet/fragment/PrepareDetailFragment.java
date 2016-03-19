package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dean.travltotibet.R;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.model.PrepareInfo;
import com.dean.travltotibet.util.Constants;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 11/8/15.
 */
public class PrepareDetailFragment extends Fragment {

    private View root;

    private String mRoute;

    private String mType;

    private InfoType mInfoType;

    private WebView mWebView;

    private View loadingView;

    public PrepareDetailFragment(InfoType infoType, String route, String type) {
        this.mInfoType = infoType;
        this.mRoute = route;
        this.mType = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.prepare_detail_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        loadingView.setVisibility(View.VISIBLE);

        BmobQuery<PrepareInfo> query = new BmobQuery<>();
        query.addQueryKeys(InfoType.INFO_COLUMN.get(mInfoType));
        query.addWhereEqualTo("route", mRoute);
        query.addWhereEqualTo("travelType", mType);
        query.findObjects(getActivity(), new FindListener<PrepareInfo>() {
            @Override
            public void onSuccess(List<PrepareInfo> list) {
                PrepareInfo prepareFile = list.get(0);
                if (prepareFile != null) {
                    String url = InfoType.getInfoResult(mInfoType, prepareFile);
                    // Log.e("url", url);
                    if (!TextUtils.isEmpty(url)) {
                        mWebView.loadUrl(url);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                if (mWebView == null || loadingView == null) {
                    return;
                }
                mWebView.setVisibility(View.GONE);
                loadingView.setVisibility(View.GONE);
            }
        });
    }

    public class SimpleWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!isVisible())
            {
                super.onPageStarted(view, url, favicon);
                return;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!isVisible())
            {
                super.onPageFinished(view, url);
                return;
            }
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy()
    {
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
