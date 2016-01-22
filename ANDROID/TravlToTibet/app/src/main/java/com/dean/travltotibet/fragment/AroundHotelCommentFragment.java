package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AroundHotelCommentFragment extends AroundCommentFragment {

    private AroundBaseActivity aroundActivity;

    private Scenic mScenic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aroundActivity = (AroundBaseActivity) getActivity();
        mScenic = (Scenic) aroundActivity.getAroundObj();
    }

    @Override
    public void goComment() {
        super.goComment();
        DialogFragment dialogFragment = new AroundHotelCommentDialog();
        Bundle bundle = new Bundle();
        bundle.putFloat(IntentExtra.INTENT_AROUND_RATING, getRating());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), AroundHotelCommentDialog.class.getName());
    }
}