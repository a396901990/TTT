package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundCommentFragment extends Fragment {

    private View root;

    private AroundBaseActivity aroundActivity;

    RatingBar ratingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_comment_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        aroundActivity = (AroundBaseActivity) getActivity();
        initRatingView();
    }

    private void initRatingView() {

        ratingBar = (RatingBar) root.findViewById(R.id.ratting_bar);
        ratingBar.setMax(5);
        ratingBar.setNumStars(0);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                goComment(rating);
            }
        });
    }

    private void goComment(float rating) {
        if (true) {
            DialogFragment dialogFragment = new LoginDialog();
            dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
        } else {
            DialogFragment dialogFragment = new AroundCommentDialog();
            Bundle bundle = new Bundle();
            bundle.putFloat(IntentExtra.INTENT_AROUND_RATING, rating);
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getFragmentManager(), AroundCommentDialog.class.getName());
        }
    }

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Log.e("onEventMainThread:", "onEventMainThread收到了消息：" + event.token);
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        initRatingView();
    }
}