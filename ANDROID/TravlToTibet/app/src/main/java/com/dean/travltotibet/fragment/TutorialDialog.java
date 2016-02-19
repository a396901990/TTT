package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 9/23/15.
 * 选择旅行类型
 */
public class TutorialDialog extends DialogFragment {

    public static final String INFO_GUIDE = "INFO_GUIDE";

    public static final String ROUTE_GUIDE = "ROUTE_GUIDE";

    public static final String CONFIRM_GUIDE = "CONFIRM_GUIDE";

    private View contentLayout;

    private String guideFrom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            guideFrom = getArguments().getString(IntentExtra.INTENT_GUIDE_FROM);
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (INFO_GUIDE.equals(guideFrom)) {
            contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.tutorial_info_view, null);
        } else if (ROUTE_GUIDE.equals(guideFrom)) {
            contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.tutorial_route_view, null);
        } else if (CONFIRM_GUIDE.equals(guideFrom)) {
            contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.tutorial_confirm_view, null);
        }
        initGuideView();

        return contentLayout;
    }

    private void initGuideView() {
        contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
                sharedPreferences.edit().putString(guideFrom, guideFrom).commit();

                getDialog().dismiss();
            }
        });
    }

    public static boolean hasShown(String guideFrom) {
        SharedPreferences preferences = TTTApplication.getSharedPreferences();
        if (INFO_GUIDE.equals(guideFrom)) {
            if (INFO_GUIDE.equals(preferences.getString(INFO_GUIDE, ""))) {
                return true;
            }
        } else if (ROUTE_GUIDE.equals(guideFrom)) {
            if (ROUTE_GUIDE.equals(preferences.getString(ROUTE_GUIDE, ""))) {
                return true;
            }
        } else if (CONFIRM_GUIDE.equals(guideFrom)) {
            if (CONFIRM_GUIDE.equals(preferences.getString(CONFIRM_GUIDE, ""))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0f;
        window.setAttributes(lp);

    }

}