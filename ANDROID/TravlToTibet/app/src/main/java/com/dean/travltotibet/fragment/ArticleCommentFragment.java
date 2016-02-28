package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ArticleActivity;
import com.dean.travltotibet.adapter.CommonCommentListAdapter;
import com.dean.travltotibet.model.ArticleComment;
import com.dean.travltotibet.model.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleCommentFragment extends Fragment {

    private View root;

    private ArticleActivity mActivity;

    private CommonCommentListAdapter commentListAdapter;

    private ArrayList<Comment> mComments;

    private final static int HOT_COMMENT = 0;

    private final static int NEW_COMMENT = 1;

    private int currentTab = 0;

    private View loadingView;

    private View noResultView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.article_comment_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (ArticleActivity) this.getActivity();
        loadingView = root.findViewById(R.id.loading_content_view);
        noResultView = root.findViewById(R.id.no_result_content);
        noResultView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);

        initCommentView();
        initBtn();
    }

    private void initBtn() {
        View hotComment = root.findViewById(R.id.hot_comment);
        View newComment = root.findViewById(R.id.new_comment);

        hotComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHotComment();
            }
        });

        newComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewComment();
            }
        });
    }

    public void showHotComment() {

        currentTab = HOT_COMMENT;

        final View hotLine = root.findViewById(R.id.hot_line);
        final View newLine = root.findViewById(R.id.new_line);
        hotLine.setVisibility(View.VISIBLE);
        newLine.setVisibility(View.GONE);
        Collections.sort(mComments, Comment.likeComparator);
        setComments();
    }

    public void showNewComment() {

        currentTab = NEW_COMMENT;

        final View hotLine = root.findViewById(R.id.hot_line);
        final View newLine = root.findViewById(R.id.new_line);
        newLine.setVisibility(View.VISIBLE);
        hotLine.setVisibility(View.GONE);
        Collections.sort(mComments, Comment.timeComparator);
        setComments();
    }

    private void initCommentView() {
        ListView listView = (ListView) root.findViewById(R.id.comment_list_view);
        commentListAdapter = new CommonCommentListAdapter(getActivity());
        listView.setAdapter(commentListAdapter);
        getCommentData();
    }

    public void getCommentData() {

        mComments = new ArrayList<>();

        BmobQuery<ArticleComment> query = new BmobQuery<>();
        query.addWhereEqualTo("article_id", mActivity.getArticle().getObjectId());
        query.findObjects(getActivity(), new FindListener<ArticleComment>() {
            @Override
            public void onSuccess(List<ArticleComment> list) {

                loadingView.setVisibility(View.GONE);

                for (ArticleComment articleComment : list) {
                    Comment comment = articleComment;
                    Log.e("getCreatedAt: ", comment.getCreatedAt());
                    mComments.add(comment);
                }
                setCommentTab();
            }

            @Override
            public void onError(int i, String s) {
                loadingView.setVisibility(View.GONE);
                setCommentTab();
            }
        });
    }

    public void setComments() {

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

    public void updateComment() {
        // 更新后显示最新评论
        currentTab = NEW_COMMENT;
        getCommentData();
    }

    public void setCommentTab() {
        switch (currentTab) {
            case HOT_COMMENT:
                showHotComment();
                break;
            case NEW_COMMENT:
                showNewComment();
                break;
            default:
                break;
        }
    }
}
