package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.FeedbackActivity;
import com.dean.travltotibet.activity.WelcomeActivity;
import com.dean.travltotibet.util.AppUtil;
import com.dean.travltotibet.util.Constants;

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

        initAppNameVersion();
        initSettingItemView();
    }

    private void initAppNameVersion() {
        TextView nameVersion = (TextView) root.findViewById(R.id.app_name_version);
        String name_version = String.format(Constants.NAME_VERSION, getString(R.string.version_name), AppUtil.getVersionName(getActivity()));
        nameVersion.setText(name_version);
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
