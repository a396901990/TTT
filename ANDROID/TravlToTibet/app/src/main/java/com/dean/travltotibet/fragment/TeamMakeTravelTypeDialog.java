package com.dean.travltotibet.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ArticleCommentActivity;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.CommentReport;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.AnimUtil;
import com.dean.travltotibet.util.IntentExtra;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 2/24/16.
 * 选择旅行类型
 */
public class TeamMakeTravelTypeDialog extends DialogFragment {

    private View contentLayout;

    private TravelTypeCallback travelTypeCallback;

    public static interface TravelTypeCallback {
        public void travelTypeChanged(String type);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.team_make_travel_type_view, null);

        setUpView();
        return contentLayout;
    }

    private void setUpView() {
        // bike
        View bike = contentLayout.findViewById(R.id.travel_bike);
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(TravelType.BIKE);
            }
        });

        // hike
        View hike = contentLayout.findViewById(R.id.travel_hike);
        hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(TravelType.HIKE);
            }
        });

        // moto
        View moto = contentLayout.findViewById(R.id.travel_moto);
        moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(TravelType.MOTO);
            }
        });

        // car
        View car = contentLayout.findViewById(R.id.travel_car);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(TravelType.CAR);
            }
        });
    }

    protected void notifyItemClicked(final String type) {
        travelTypeCallback.travelTypeChanged(type);
        dismiss();
    }

    public void setTravelTypeCallback(TravelTypeCallback travelTypeCallback) {
        this.travelTypeCallback = travelTypeCallback;
    }

}