package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.FeedbackActivity;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class HomeSettingFragment extends Fragment {

    private boolean isNewVersion = false;

    private View lastVersionView;
    private View newVersionView;

    private View versionCheckView;  // 版本更新视图
    private View feedbackView;      // 反馈视图
    private View rateView;          // 评价视图
    private View shareView;         // 分享视图
    private View aboutView;         // 关于视图

    private View root;

    public HomeSettingFragment() {
    }

    public static HomeSettingFragment newInstance() {
        HomeSettingFragment fragment = new HomeSettingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_setting_fragment_layout, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lastVersionView = root.findViewById(R.id.version_check_last);
        newVersionView = root.findViewById(R.id.version_check_new);

        updateVersionCheck();
        initSettingItemView();
    }

    private void initSettingItemView() {
        // 版本更新视图
        versionCheckView = root.findViewById(R.id.setting_version_check);
        versionCheckView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });
        // 分享视图
        shareView = root.findViewById(R.id.setting_share);
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // 关于视图
        aboutView = root.findViewById(R.id.setting_about);
        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void updateVersionCheck() {
        // 发现新版本
        if (isNewVersion) {
            lastVersionView.setVisibility(View.INVISIBLE);
            newVersionView.setVisibility(View.VISIBLE);
        }
        // 已经是最后版本
        else {
            lastVersionView.setVisibility(View.VISIBLE);
            newVersionView.setVisibility(View.INVISIBLE);
        }
    }

}
