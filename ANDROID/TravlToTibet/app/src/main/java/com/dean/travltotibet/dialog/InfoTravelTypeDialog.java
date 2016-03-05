package com.dean.travltotibet.dialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.AnimUtil;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/23/15.
 * 选择旅行类型
 */
public class InfoTravelTypeDialog extends DialogFragment {

    private View contentLayout;

    private String route;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            route = getArguments().getString(IntentExtra.INTENT_ROUTE);
        } else {
            route = TravelType.BIKE;
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.info_travel_type_dialog_layout, null);

        setUpTypeIcon();

        return contentLayout;
    }

    private void setUpTypeIcon() {
        ImageView bike = (ImageView) contentLayout.findViewById(R.id.travel_bike_icon);
        ImageView hike = (ImageView) contentLayout.findViewById(R.id.travel_hike_icon);
        ImageView moto = (ImageView) contentLayout.findViewById(R.id.travel_moto_icon);
        ImageView car = (ImageView) contentLayout.findViewById(R.id.travel_car_icon);

        TextView bikeText = (TextView) contentLayout.findViewById(R.id.travel_bike_text);
        TextView hikeText = (TextView) contentLayout.findViewById(R.id.travel_hike_text);
        TextView motoText = (TextView) contentLayout.findViewById(R.id.travel_moto_text);
        TextView carText = (TextView) contentLayout.findViewById(R.id.travel_car_text);

        String types = TTTApplication.getDbHelper().getRoutePlanType(route);

        // bike
        if (types.contains(TravelType.BIKE)) {
            bike.setEnabled(true);
            bike.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.BIKE, R.color.white));
            bikeText.setText(getString(R.string.travel_type_bike));
            bike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemClicked(v, v.getTag(), TravelType.BIKE);
                }
            });
        } else {
            bike.setEnabled(false);
            bike.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.BIKE, R.color.half_light_gray));
            bikeText.setText(getString(R.string.no_support));
        }

        // hike
        if (types.contains(TravelType.HIKE)) {
            hike.setEnabled(true);
            hike.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.HIKE, R.color.white));
            hikeText.setText(getString(R.string.travel_type_hike));
            hike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemClicked(v, v.getTag(), TravelType.HIKE);
                }
            });
        } else {
            hike.setEnabled(false);
            hike.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.HIKE, R.color.half_light_gray));
            hikeText.setText(getString(R.string.no_support));
        }

        // moto
        if (types.contains(TravelType.MOTO)) {
            moto.setEnabled(true);
            moto.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.MOTO, R.color.white));
            motoText.setText(getString(R.string.travel_type_moto));

            moto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemClicked(v, v.getTag(), TravelType.MOTO);
                }
            });

        } else {
            moto.setEnabled(false);
            moto.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.MOTO, R.color.half_light_gray));
            motoText.setText(getString(R.string.no_support));
        }

        // CAR
        if (types.contains(TravelType.CAR)) {
            car.setEnabled(true);
            car.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.CAR, R.color.white));
            carText.setText(getString(R.string.travel_type_car));

            car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemClicked(v, v.getTag(), TravelType.CAR);
                }
            });
        } else {
            car.setEnabled(false);
            car.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.CAR, R.color.half_light_gray));
            carText.setText(getString(R.string.no_support));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.9f;
        window.setAttributes(lp);

    }

    protected void notifyItemClicked(final View item, final Object obj, final String type) {

        // 防止多次点击
        item.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = AnimUtil.shake(item, 3f);
                animator.setRepeatCount(0);

                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((InfoActivity) getActivity()).updateType(type);
                        dismiss();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                animator.start();

            }
        }, 200);
    }
}