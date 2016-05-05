package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AboutSettingActivity;
import com.dean.travltotibet.activity.FeedbackActivity;
import com.dean.travltotibet.activity.UserFavoriteActivity;
import com.dean.travltotibet.activity.UserPublishActivity;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.util.AppUtil;
import com.dean.travltotibet.util.MarketUtils;
import com.dean.travltotibet.util.ScreenUtil;
import com.dean.travltotibet.util.SystemUtil;


import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.AppVersion;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by DeanGuo on 12/09/15.
 */
public class NavigationFragment extends LoginFragment {

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

        initProfileView();
        initSettingItemView();
        updateVersionCheck(getActivity());
    }

    private void initProfileView() {
        initLoginView(root);

        // my publish
        View myPublish = root.findViewById(R.id.my_publish);
        myPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (TTTApplication.hasLoggedIn()) {
                    Intent intent = new Intent(getActivity(), UserPublishActivity.class);
                    startActivity(intent);
                } else {
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }
            }
        });

        View myFavorite = root.findViewById(R.id.my_favorite);
        myFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (TTTApplication.hasLoggedIn()) {
                    Intent intent = new Intent(getActivity(), UserFavoriteActivity.class);
                    startActivity(intent);
                } else {
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }
            }
        });

    }

    private void initSettingItemView() {

        // 反馈视图
        View feedbackView = root.findViewById(R.id.setting_feedback);
        feedbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到反馈activity
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });

        // 评价视图
        View rateView = root.findViewById(R.id.setting_rate);
        rateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppMark();
            }
        });
        // 分享视图
        View shareView = root.findViewById(R.id.setting_share);
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        // 关于视图
        View aboutView = root.findViewById(R.id.setting_about);
        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutSettingActivity.class));
            }
        });
    }

    private void openAppMark() {
        MarketUtils.launchAppDetail(AppUtil.getPackageName(getActivity()), "");
    }

    private void share() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(getString(R.string.app_name));  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText(getString(R.string.share_msg)+getString(R.string.share_link));  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片

        //网络图片的url：所有平台
        oks.setImageUrl("http://a3.qpic.cn/psb?/V116glj830opeH/RXN5yBz4MDlpet7Y9Wyk*11Oa16SSf5Ou5gRd6Ep7W8!/b/dHMBAAAAAAAA&bo=AAIAAgACAAIDCSw!&rf=viewer_4");//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(getString(R.string.share_link));   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl(getString(R.string.share_link));  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(getActivity());
    }

    public void updateVersionCheck(final Context mContext) {

        // 版本更新视图
        final View versionCheckView = root.findViewById(R.id.setting_version_check);
        versionCheckView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUpdateAgent.forceUpdate(getActivity());
            }
        });

        final View lastVersionView = root.findViewById(R.id.version_check_last);
        final View newVersionView = root.findViewById(R.id.version_check_new);

        final int currentVersion = SystemUtil.getAppVersionCode(mContext);

        BmobQuery<AppVersion> appVersionBmobQuery = new BmobQuery<AppVersion>();
        appVersionBmobQuery.addWhereEqualTo("platform", "Android");
        appVersionBmobQuery.order("-version_i");
        appVersionBmobQuery.findObjects(mContext, new FindListener<AppVersion>() {
            @Override
            public void onSuccess(List<AppVersion> apps) {
                if (apps.size() > 0) {
                    UpdateResponse updateInfo = new UpdateResponse(mContext, apps.get(0));
                    if (updateInfo.version_i > currentVersion) {

                        // 发现新版本
                        lastVersionView.setVisibility(View.INVISIBLE);
                        newVersionView.setVisibility(View.VISIBLE);
                        versionCheckView.setClickable(true);
                    } else {
                        lastVersionView.setVisibility(View.VISIBLE);
                        newVersionView.setVisibility(View.INVISIBLE);
                        versionCheckView.setClickable(false);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                // 已经是最后版本
                lastVersionView.setVisibility(View.VISIBLE);
                newVersionView.setVisibility(View.INVISIBLE);
                versionCheckView.setClickable(false);
            }
        });
    }

}
