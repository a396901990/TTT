package com.dean.travltotibet.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.CustomDialog;
import com.dean.travltotibet.ui.sliderview.SliderLayout;
import com.dean.travltotibet.ui.sliderview.SliderTypes.BurnsSliderView;
import com.dean.travltotibet.ui.sliderview.SliderTypes.DefaultSliderView;
import com.dean.travltotibet.ui.sliderview.SliderTypes.TextSliderView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;

import java.util.HashMap;

/**
 * Created by DeanGuo on 12/17/15.
 */
public class ScenicDetailDialog extends DialogFragment {

    private View contentLayout;
    private Scenic mScenic;

    private SliderLayout mDefaultIndicator;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.scenic_detail_dialog_fragment, null);

        if (getArguments() != null) {
            mScenic = (Scenic) getArguments().getSerializable(IntentExtra.INTENT_SCENIC);
        }

        initContentView();

        // 创建对话框，设置标题，布局，关闭响应
        CustomDialog dialog = new CustomDialog(getActivity());
        dialog.setCustomContentView(contentLayout);
        dialog.hideTitleContent();
        return dialog;
    }

    private void initContentView() {

        // 景点详细介绍
        TextView scenicDetail = (TextView) contentLayout.findViewById(R.id.scenic_detail);
        scenicDetail.setText(mScenic.getScenic_overview());

        // 景点名字
        TextView scenicName = (TextView) contentLayout.findViewById(R.id.scenic_name);
        scenicName.setText(mScenic.getScenic_name());

        // slider view
        mDefaultIndicator = (SliderLayout) contentLayout.findViewById(R.id.slider);

        // 获取需要显示的图片url
        String[] urls = mScenic.getScenic_pic().split(Constants.URL_MARK);

        // 设置图片
        for (String url : urls) {
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            textSliderView.image(url);
            mDefaultIndicator.addSlider(textSliderView);
        }
    }

    @Override
    public void onStop() {
        mDefaultIndicator.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPause() {
        mDefaultIndicator.stopAutoCycle();
        super.onPause();
    }

    @Override
    public void onResume() {
        mDefaultIndicator.startAutoCycle();
        super.onResume();
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
