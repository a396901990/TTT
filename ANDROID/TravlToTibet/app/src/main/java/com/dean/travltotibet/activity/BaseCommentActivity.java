package com.dean.travltotibet.activity;

import android.os.Bundle;

import com.dean.travltotibet.dialog.BaseCommentDialog;

import cn.bmob.v3.BmobObject;

public class BaseCommentActivity extends  BaseActivity implements BaseCommentDialog.CommentCallBack  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCommentSuccess() {

    }

    @Override
    public void onCommentFailed() {

    }

    public BmobObject getObj() {
        return null;
    }
}
