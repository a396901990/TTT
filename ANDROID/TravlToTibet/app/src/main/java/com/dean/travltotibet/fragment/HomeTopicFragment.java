package com.dean.travltotibet.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.ArticleAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.model.Article;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 2/16/16.
 */
public class HomeTopicFragment extends BaseHomeFragment {

    private View root;
    private ArticleAdapter mAdapter;
    private ArrayList<Article> articles;
    private HomeActivity mActivity;
    private RecyclerView mRecyclerView;

    public HomeTopicFragment() {
    }

    public static HomeTopicFragment newInstance() {
        HomeTopicFragment fragment = new HomeTopicFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_topic_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();

        setUpList();
    }

    private void setUpList() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.article_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new ArticleAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        getArticles();
        updateData();
    }

    private void getArticles() {
        articles = new ArrayList<>();

        BmobQuery<Article> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(getActivity(), new FindListener<Article>() {
            @Override
            public void onSuccess(List<Article> list) {
                articles = (ArrayList<Article>) list;
                updateData();
            }

            @Override
            public void onError(int i, String s) {
                updateData();
            }
        });
    }

    /**
     * 更新recentRoutes数据
     */
    public void updateData() {
        View noResultView = root.findViewById(R.id.no_result_content);

        // 无数据
        if (articles == null || articles.size() == 0) {
            noResultView.setVisibility(View.VISIBLE);
        }
        // 有数据
        else {
            noResultView.setVisibility(View.GONE);
        }
        mAdapter.setData(articles);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void update() {
//        new refreshTask().execute();
    }

    @Override
    public void refresh() {
        new refreshTask().execute();
    }

    private class refreshTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            if (mActivity != null && mAdapter != null) {
                mActivity.startUpdate();
                mAdapter.clearData();
            }
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            getArticles();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mActivity.finishUpdate();

            super.onPostExecute(result);
        }
    }

}
