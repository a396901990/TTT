package com.dean.travltotibet.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AboutSettingActivity;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.activity.FeedbackActivity;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.UserFavoriteActivity;
import com.dean.travltotibet.activity.UserNotificationActivity;
import com.dean.travltotibet.activity.UserPublishActivity;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.model.UserMessage;
import com.dean.travltotibet.util.AppUtil;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.MarketUtils;
import com.dean.travltotibet.util.ScreenUtil;
import com.dean.travltotibet.util.SystemUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;


import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.AppVersion;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by DeanGuo on 12/09/15.
 */
public class NavigationFragment extends LoginFragment {

    private HomeActivity homeActivity;

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
        homeActivity = (HomeActivity) getActivity();
        initProfileView();
        initSettingItemView();
        updateVersionCheck(getActivity());
    }

    @Override
    public void onResume() {
        checkNotification();
        super.onResume();
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
                    // 可能修改数据，所以退出时需要刷新
                    Intent intent = new Intent(getActivity(), UserPublishActivity.class);
                    startActivityForResult(intent, BaseActivity.UPDATE_REQUEST);
                } else {
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }
            }
        });

        // my favorite
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

        // notification
        View myNotification = root.findViewById(R.id.my_notification);
        myNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (TTTApplication.hasLoggedIn()) {
                    Intent intent = new Intent(getActivity(), UserNotificationActivity.class);
                    startActivity(intent);
                } else {
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }
            }
        });

        // logout
        View logout = root.findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }

                if (TTTApplication.hasLoggedIn()) {
                    new MaterialDialog.Builder(getActivity())
                            .title(getString(R.string.logout_dialog_title))
                            .positiveText(getString(R.string.ok_btn))
                            .negativeText(getString(R.string.cancel_btn))
                            .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                            .callback(new MaterialDialog.Callback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    LoginUtil.getInstance().logout();
                                    checkNotification();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .build()
                            .show();
                }
            }
        });

    }

    private void checkNotification() {
        if (getActivity() == null || root == null) {
            return;
        }

        if (!TTTApplication.hasLoggedIn()) {
            resetNotification();
        } else {
            BmobQuery<UserMessage> query = new BmobQuery<>();
            query.addWhereRelatedTo("UserMessage", new BmobPointer(TTTApplication.getUserInfo()));
            query.addWhereEqualTo("status", UserMessage.UNREAD_STATUS);
            query.findObjects(getActivity(), new FindListener<UserMessage>() {
                @Override
                public void onSuccess(List<UserMessage> list) {
                    if (list.size() > 0) {
                        hasNewNotification();
                    } else {
                        resetNotification();
                    }
                }

                @Override
                public void onError(int i, String s) {
                    resetNotification();
                }
            });
        }
    }

    private void hasNewNotification() {
        ImageView notificationIcon = (ImageView) root.findViewById(R.id.my_notification_icon);
        IconicsDrawable iconicsDrawable = new IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_notifications).color(TTTApplication.getMyColor(R.color.light_red)).sizeDp(18);
        notificationIcon.setImageDrawable(iconicsDrawable);
    }

    public void resetNotification() {
        ImageView notificationIcon = (ImageView) root.findViewById(R.id.my_notification_icon);
        IconicsDrawable iconicsDrawable = new IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_notifications).color(TTTApplication.getMyColor(R.color.white)).sizeDp(18);
        notificationIcon.setImageDrawable(iconicsDrawable);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 只在UPDATE_REQUEST操作后刷新(Publish)
        if (requestCode == BaseActivity.UPDATE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (homeActivity != null) {
                    homeActivity.updateCommunityFragment();
                }
            }
        }
    }
}
