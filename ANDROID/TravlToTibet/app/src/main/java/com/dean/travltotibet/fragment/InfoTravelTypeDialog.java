package com.dean.travltotibet.fragment;

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

    public final static String FROM_FIRST = "from_first";
    public final static String FROM_SECOND = "from_itself";

    private View contentLayout;

    private String fromType;

    private ArrayList<RoutePlan> plans;

    private String route;
    private String routeName;

    private ImageView bike;
    private ImageView hike;
    private ImageView moto;
    private ImageView car;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            route = getArguments().getString(IntentExtra.INTENT_ROUTE);
            routeName = getArguments().getString(IntentExtra.INTENT_ROUTE_NAME);
            fromType = getArguments().getString(IntentExtra.INTENT_FROM_WHERE);
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.info_travel_type_dialog_layout, null);
        bike = (ImageView) contentLayout.findViewById(R.id.travel_bike_icon);
        hike = (ImageView) contentLayout.findViewById(R.id.travel_hike_icon);
        moto = (ImageView) contentLayout.findViewById(R.id.travel_moto_icon);
        car = (ImageView) contentLayout.findViewById(R.id.travel_car_icon);

        bike.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.BIKE, R.color.white));
        hike.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.HIKE, R.color.white));
        moto.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.MOTO, R.color.white));
        car.setImageDrawable(TravelType.getTypeImageSrcWithColor(TravelType.CAR, R.color.white));

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), TravelType.BIKE);
            }
        });

        hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), TravelType.HIKE);
            }
        });

        moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), TravelType.MOTO);
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), TravelType.CAR);
            }
        });

        return contentLayout;
    }

    public void getTravelTyle() {
//        String[] types = TTTApplication.getDbHelper().getPlanList();
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
                        // 如果是开始视图则关闭对话框进行跳转，如果是info视图则更新type类型
                        if (fromType.equals(FROM_FIRST)) {
                            // 跳转到InfoRouteActivity
                            Intent intent = new Intent(getActivity(), InfoActivity.class);
                            intent.putExtra(IntentExtra.INTENT_ROUTE, route);
                            intent.putExtra(IntentExtra.INTENT_ROUTE_NAME, routeName);
                            intent.putExtra(IntentExtra.INTENT_ROUTE_TYPE, type);
                            startActivity(intent);
                            dismiss();
                        } else if (fromType.equals(FROM_SECOND)) {
                            ((InfoActivity) getActivity()).updateType(type);
                            dismiss();
                        }
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