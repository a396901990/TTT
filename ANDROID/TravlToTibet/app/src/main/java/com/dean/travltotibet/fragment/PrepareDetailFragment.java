package com.dean.travltotibet.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dean.greendao.PrepareDetail;
import com.dean.greendao.PrepareInfo;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.PrepareDetailActivity;
import com.dean.travltotibet.adapter.PrepareDetailAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.model.PrepareFile;
import com.dean.travltotibet.ui.CustomProgress;
import com.dean.travltotibet.ui.RotateLoading;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;
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

    private Dialog mProgressDialog;

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

        // 初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        showLoading();

        // 从PrepareInfo表中获取该路段的准备信息
        PrepareInfo prepareInfo = TTTApplication.getDbHelper().getPrepareInfo(mRoute, mType);
        if (prepareInfo == null) {
            return;
        }
        // 获取条目名字
        String prepareName = InfoType.getInfoResult(mInfoType, prepareInfo);
        Log.e("prepareName", prepareName);
        BmobQuery<PrepareFile> query = new BmobQuery<>();
        query.addWhereEqualTo("fileName", prepareName);
        query.findObjects(getActivity(), new FindListener<PrepareFile>() {
            @Override
            public void onSuccess(List<PrepareFile> list) {
                PrepareFile prepareFile = list.get(0);
                String url = prepareFile.getFile().getFileUrl(getActivity());
                Log.e("url", url);
                mWebView.loadUrl(url);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("onError", s);
                mWebView.loadUrl(Constants.EMPTY_HTML_CONTENT);
                dismissLoading();
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
            CustomProgress.dismissDialog();
             dismissLoading();
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

    private void showLoading( )
    {
        if (mProgressDialog == null || !mProgressDialog.isShowing())
        {
            mProgressDialog = new Dialog(super.getActivity(), R.style.Custom_Progress);
            mProgressDialog.setContentView(R.layout.progress_custom_layout);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle("");
            // 设置居中
            mProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = mProgressDialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            mProgressDialog.getWindow().setAttributes(lp);
            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            mProgressDialog.show();
        }
    }

    private void dismissLoading()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onPause()
    {
        dismissLoading();
        super.onPause();
    }


}
