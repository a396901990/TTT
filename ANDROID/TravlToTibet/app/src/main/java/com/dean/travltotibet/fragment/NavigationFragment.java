package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AboutSettingActivity;
import com.dean.travltotibet.activity.FeedbackActivity;
import com.dean.travltotibet.util.SystemUtil;

import org.json.JSONArray;

import java.util.HashMap;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by DeanGuo on 12/09/15.
 */
public class NavigationFragment extends Fragment {

    private boolean isNewVersion = false;

    private View lastVersionView;
    private View newVersionView;

    private View versionCheckView;  // 版本更新视图
    private View feedbackView;      // 反馈视图
    private View rateView;          // 评价视图
    private View shareView;         // 分享视图
    private View aboutView;         // 关于视图

    private View root;

    public NavigationFragment() {
    }

    public static NavigationFragment newInstance() {
        NavigationFragment fragment = new NavigationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.navigation_layout, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lastVersionView = root.findViewById(R.id.version_check_last);
        newVersionView = root.findViewById(R.id.version_check_new);

        initSettingItemView();
        updateVersionCheck(getActivity());
    }

    private void initSettingItemView() {
        // 版本更新视图
        versionCheckView = root.findViewById(R.id.setting_version_check);
        versionCheckView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUpdateAgent.forceUpdate(getActivity());
            }
        });
        // 反馈视图
        feedbackView = root.findViewById(R.id.setting_feedback);
        feedbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到反馈activity
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
        // 评价视图
        rateView = root.findViewById(R.id.setting_rate);
        rateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dialogFragment = new LoginDialog();
                dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
            }
        });
        // 分享视图
        shareView = root.findViewById(R.id.setting_share);
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        // 关于视图
        aboutView = root.findViewById(R.id.setting_about);
        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutSettingActivity.class));
            }
        });
    }

    private void share() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle("我是分享标题");  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片

        //网络图片的url：所有平台
        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://github.com/a396901990");   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl("https://github.com/a396901990");  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(getActivity());
    }

    public void updateVersionCheck(final Context mContext) {

        final int currentVersion = SystemUtil.getAppVersionCode(mContext);

        BmobQuery bmobQuery;
        (bmobQuery = new BmobQuery("AppVersion")).addWhereEqualTo("platform", "Android");
        bmobQuery.order("-version_i");
        bmobQuery.findObjects(mContext, new FindCallback() {
            @Override
            public void onFailure(int i, String s) {
                // 已经是最后版本
                lastVersionView.setVisibility(View.VISIBLE);
                newVersionView.setVisibility(View.INVISIBLE);
                versionCheckView.setClickable(false);
            }

            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (jsonArray.length() > 0) {
                    UpdateResponse updateInfo = new UpdateResponse(mContext, jsonArray.optJSONObject(0));
                    if (updateInfo.version_i > currentVersion) {

                        // 发现新版本
                        lastVersionView.setVisibility(View.INVISIBLE);
                        newVersionView.setVisibility(View.VISIBLE);
                        versionCheckView.setClickable(true);
                    }
                }
            }
        });
    }

}
