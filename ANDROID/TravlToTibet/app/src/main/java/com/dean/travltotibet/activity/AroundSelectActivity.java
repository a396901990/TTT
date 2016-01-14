package com.dean.travltotibet.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundSelectActivity extends BaseActivity {

    private String routeName;

    private String aroundType;

    private String aroundBelong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_select_view);

        Intent intent = getIntent();
        if (intent != null) {
            routeName = intent.getStringExtra(IntentExtra.INTENT_ROUTE);
            aroundType = intent.getStringExtra(IntentExtra.INTENT_AROUND_TYPE);
            aroundBelong = intent.getStringExtra(IntentExtra.INTENT_AROUND_BELONG);
        }

        initView();
    }

    private void initView() {
        View hiddenView = findViewById(R.id.hidden_top_content);
        hiddenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnimation();
            }
        });

        View showView = findViewById(R.id.show_bottom_content);
        showView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
    }

    public String getRouteName() {
        return routeName;
    }

    public String getAroundType() {
        return aroundType;
    }

    public String getAroundBelong() {
        return aroundBelong;
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public void onBackPressed() {
        finishWithAnimation();
    }

    public void finishWithAnimation() {

        Animation pushDownOut = AnimationUtils.loadAnimation(this, R.anim.push_down_out);
        pushDownOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//
        final View showView = findViewById(R.id.show_bottom_content);
        showView.startAnimation(pushDownOut);
        showView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                showView.setVisibility(View.INVISIBLE);
                finish();
                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_out);
            }
        }, 200);
    }
}
