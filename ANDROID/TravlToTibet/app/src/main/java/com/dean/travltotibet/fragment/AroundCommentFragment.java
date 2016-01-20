package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundCommentFragment extends Fragment {

    private View root;

    private AroundBaseActivity aroundActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_comment_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aroundActivity = (AroundBaseActivity) getActivity();
        initContentView();
    }

    private void initContentView() {

        RatingBar ratingBar = (RatingBar) root.findViewById(R.id.ratting_bar);
        ratingBar.setMax(5);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                goComment(rating);
            }
        });
    }

    private void goComment(float rating) {
        DialogFragment dialogFragment = new AroundCommentDialog();
        Bundle bundle = new Bundle();
        bundle.putFloat(IntentExtra.INTENT_AROUND_RATING, rating);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), AroundCommentDialog.class.getName());
    }
}