package com.dean.travltotibet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.ArticleCommentDialog;
import com.dean.travltotibet.fragment.ArticleFragment;
import com.dean.travltotibet.fragment.BaseCommentDialog;
import com.dean.travltotibet.fragment.ArticleCommentFragment;
import com.dean.travltotibet.fragment.RouteChartFragment;
import com.dean.travltotibet.fragment.RouteDetailFragment;
import com.dean.travltotibet.fragment.RouteMapFragment;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by DeanGuo on 2/17/16.
 */
public class ArticleActivity extends BaseActivity implements BaseCommentDialog.CommentCallBack {

    public static final String FROM_HOME = "from_home";

    public static final String FROM_NOTIFICATION = "from_notification";

    private String launchFrom;

    private Article mArticle;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

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
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
//        setTitle(mArticle.getTitle());
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        updateWatch();
        initBtn();
        initViewPage();
    }

    private void initViewPage() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new ViewPageFragmentAdapter(getFragmentManager());

        // 为adapter添加数据
        mAdapter.add(ArticleFragment.class, null, "");
        mAdapter.add(ArticleCommentFragment.class, null, "");
        mPager.setAdapter(mAdapter);

        mPager.setOffscreenPageLimit(0);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    goArticlePage();
                } else {
                    goCommentPage();
                }
            }
        });
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
                if (mPager.getCurrentItem() == 0) {
                    goCommentPage();
                } else if (mPager.getCurrentItem() == 1) {
                    goArticlePage();
                }
            }
        });
    }

    private void goCommentPage() {
        TextView switchBtn = (TextView) findViewById(R.id.comment_switch_icon);
        switchBtn.setText("原文");
        mPager.setCurrentItem(1, true);
    }

    private void goArticlePage() {
        TextView switchBtn = (TextView) findViewById(R.id.comment_switch_icon);
        switchBtn.setText("评论");
        mPager.setCurrentItem(0, true);
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
    public void onCommentSuccess() {
        goCommentPage();
        ArticleCommentFragment articleCommentFragment = (ArticleCommentFragment) mAdapter.getFragment(1);
        if (articleCommentFragment != null) {
            articleCommentFragment.updateComment();
        }
    }

    @Override
    public void onCommentFailed() {

    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    public Article getArticle() {
        return mArticle;
    }
}
