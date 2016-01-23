package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.CommentListAdapter;
import com.dean.travltotibet.model.Comment;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundCommentFragment extends Fragment {

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

    public void setComments(ArrayList<Comment> mComments) {
        commentListAdapter.setData(mComments);
    }
}