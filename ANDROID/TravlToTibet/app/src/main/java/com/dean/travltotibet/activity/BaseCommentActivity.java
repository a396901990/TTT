package com.dean.travltotibet.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.model.Comment;

import cn.bmob.v3.BmobObject;

/**
 *  需要显示回复的activity必须继承BaseCommentActivity
 */
public abstract class BaseCommentActivity extends  BaseActivity implements BaseCommentDialog.CommentCallBack  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCommentSuccess(Comment comment) {

    }

    @Override
    public void onCommentFailed() {

    }

    abstract public BmobObject getObj();

    private LoadingBackgroundManager loadingBackgroundManager;

    public void initLoadingBackground() {
        ViewGroup contentView = (ViewGroup) this.findViewById(R.id.content_view);
        loadingBackgroundManager = new LoadingBackgroundManager(this, contentView);
    }

    public LoadingBackgroundManager getLoadingBackgroundManager() {
        return loadingBackgroundManager;
    }

    public void setLoadingBackgroundManager(LoadingBackgroundManager loadingBackgroundManager) {
        this.loadingBackgroundManager = loadingBackgroundManager;
    }
}
