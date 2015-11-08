package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.FeedbackActivity;
import com.dean.travltotibet.activity.WelcomeActivity;

/**
 * Created by DeanGuo on 11/7/15.
 */
public class AboutSettingFragment extends Fragment {

    private View welcomeView;         // 分享视图
    private View contactUsView;         // 关于视图

    private View root;

    public AboutSettingFragment() {
    }

    public static AboutSettingFragment newInstance() {
        AboutSettingFragment fragment = new AboutSettingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.about_setting_fragment_layout, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSettingItemView();
    }

    private void initSettingItemView() {

        // 欢迎界面
        welcomeView = root.findViewById(R.id.about_welcome);
        welcomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WelcomeActivity.class));
            }
        });
        // 联系我们
        contactUsView = root.findViewById(R.id.about_contact_us);
        contactUsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
