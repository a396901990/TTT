package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.CommentAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.model.Comment;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundCommentFragment extends Fragment {

    private View root;

    private RatingBar ratingBar;

    private RecyclerView mRecyclerView;

    private CommentAdapter mCommentAdapter;

    private ArrayList<Comment> mComments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_comment_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRatingView();
        initCommentView();
    }

    private void initCommentView() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.comment_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mCommentAdapter = new CommentAdapter(getActivity());
        mRecyclerView.setAdapter(mCommentAdapter);
        getCommentData();
    }

    private void initRatingView() {
        ratingBar = (RatingBar) root.findViewById(R.id.ratting_bar);
        ratingBar.setMax(5);
        ratingBar.setRating(0);
        ratingBar.setStepSize(1);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                goComment();
            }
        });
    }

    public void goComment() {
    }

    public void getCommentData() {

    }

    public void setComments(ArrayList<Comment> mComments) {
        this.mComments = mComments;
        mCommentAdapter.setData(mComments);
    }

    protected float getRating() {
        return ratingBar.getRating();
    }

}