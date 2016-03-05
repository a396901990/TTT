package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.CommentListAdapter;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.model.Comment;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundCommentFragment extends AroundBaseFragment implements BaseCommentDialog.CommentCallBack {

    private View root;

    private CommentListAdapter commentListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_comment_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCommentView();
    }

    private void initFloatBtn() {
        FloatingActionButton mFab = getAroundActivity().getFloatingBtn();
        mFab.setVisibility(View.VISIBLE);
        mFab.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_edit, TTTApplication.getMyColor(R.color.white)));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goComment();
            }
        });
    }

    private void initCommentView() {
        ListView listView = (ListView) root.findViewById(R.id.comment_list_view);
        commentListAdapter = new CommentListAdapter(getActivity());
        listView.setAdapter(commentListAdapter);
        getCommentData();
    }

    public void goComment() {
    }

    public void getCommentData() {

    }

    public void update() {
        getCommentData();
    }

    public void setComments(ArrayList<Comment> mComments) {
        View noResultView = root.findViewById(R.id.no_result_content);

        // 无数据
        if (mComments == null || mComments.size() == 0) {
            noResultView.setVisibility(View.VISIBLE);
        }
        // 有数据
        else {
            noResultView.setVisibility(View.GONE);
        }
        commentListAdapter.setData(mComments);
    }

    @Override
    public void onTabChanged() {
        initFloatBtn();
    }

    @Override
    public void onCommentSuccess() {
        update();
    }

    @Override
    public void onCommentFailed() {

    }
}