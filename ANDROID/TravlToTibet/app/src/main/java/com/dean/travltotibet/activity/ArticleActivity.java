package com.dean.travltotibet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.ArticleCommentDialog;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.fragment.ArticleCommentFragment;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.util.CountUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by DeanGuo on 2/17/16.
 */
public class ArticleActivity extends BaseCommentActivity {

    public static final String FROM_HOME = "from_home";

    public static final String FROM_NOTIFICATION = "from_notification";

    private String launchFrom;

    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.article_view);

        if (getIntent() != null) {
            mArticle = (Article) getIntent().getSerializableExtra(IntentExtra.INTENT_ARTICLE);
            launchFrom = getIntent().getStringExtra(IntentExtra.INTENT_ARTICLE_FROM);
        }
        if (mArticle == null) {
            finish();
        }

        if (FROM_NOTIFICATION.equals(launchFrom)) {
            // 初始化share sdk
            ShareSDK.initSDK(this);
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        // 双击两次返回顶部
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isDoubleClick()) {
                    gotoArticle();
                }
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setHomeIndicator();

        updateWatch();
        initBtn();
        initLoadingBackground();

        // 统计文章
        if (!TextUtils.isEmpty(mArticle.getTitle())) {
            CountUtil.countArticle(this, mArticle.getTitle());
        }
    }

    private void updateWatch() {
        try {
            mArticle.increment("watch");
            if (this != null) {
                mArticle.update(this, null);
            }
        } catch (Exception e) {
            // finish();
        }
    }

    private void initBtn() {
        // 评论
        View commentBtn = findViewById(R.id.send_comment_btn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAction();
            }
        });

        // 切换按钮
        View switchBtn = findViewById(R.id.comment_switch_icon);
        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoComment();
            }
        });
    }

    private void gotoComment() {
        final View commentView = findViewById(R.id.comment_content_view);
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, commentView.getTop());
            }
        });
    }

    private void gotoArticle() {
        final View articleView = findViewById(R.id.article_content_view);
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, articleView.getTop());
            }
        });
    }

    private void refresh() {
        ArticleCommentFragment fragment = (ArticleCommentFragment) getFragmentManager().findFragmentById(R.id.comment_fragment);
        if (fragment != null && fragment.isAdded()) {
            fragment.onRefresh();
        }
    }

    private void commentAction() {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        BaseCommentDialog dialogFragment = new ArticleCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), ArticleCommentDialog.class.getName());
    }

    private void sendAction() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(mArticle.getTitle());  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText(mArticle.getTitle() + mArticle.getShareUrl());  //最多40个字符

        //网络图片的url：所有平台
        oks.setImageUrl(mArticle.getTitleImage());//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mArticle.getShareUrl());   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl(mArticle.getShareUrl());  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(this);
    }

    private void likeAction() {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        String objectId = sharedPreferences.getString(mArticle.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            mArticle.increment("like");
            mArticle.update(this, new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().putString(mArticle.getObjectId(), mArticle.getObjectId()).commit();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(getApplicationContext(), "点赞失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.text_commented), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // 结束
        if (id == android.R.id.home) {
            if (FROM_HOME.equals(launchFrom)) {
                finish();
            }
            else if (FROM_NOTIFICATION.equals(launchFrom)) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
        // 提交按钮
        if (id == R.id.action_like) {
            likeAction();
        }
        // 结束
        else if (id == R.id.action_share) {
            sendAction();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCommentSuccess(Comment comment) {
        // 将评论添加到当前team request的关联中
        BmobRelation commentRelation = new BmobRelation();
        commentRelation.add(comment);
        mArticle.setReplyComments(commentRelation);
        mArticle.update(getApplication(), new UpdateListener() {
            @Override
            public void onSuccess() {
                gotoComment();
                refresh();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    @Override
    public void onCommentFailed() {

    }

    @Override
    public BmobObject getObj() {
        return mArticle;
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    public Article getArticle() {
        return mArticle;
    }
}
