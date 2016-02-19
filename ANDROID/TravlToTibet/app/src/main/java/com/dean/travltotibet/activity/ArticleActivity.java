package com.dean.travltotibet.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.ArticleCommentDialog;
import com.dean.travltotibet.fragment.ArticleFragment;
import com.dean.travltotibet.fragment.BaseCommentDialog;
import com.dean.travltotibet.fragment.ArticleCommentFragment;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by DeanGuo on 2/17/16.
 */
public class ArticleActivity extends BaseActivity implements BaseCommentDialog.CommentCallBack{

    private Article mArticle;

    private ArticleFragment mArticleFragment;

    private ArticleCommentFragment mArticleCommentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.article_view);

        if (getIntent() != null) {
            mArticle = (Article) getIntent().getSerializableExtra(IntentExtra.INTENT_ARTICLE);
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(mArticle.getTitle());
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        mArticleCommentFragment = (ArticleCommentFragment) getFragmentManager().findFragmentById(R.id.article_comment_fragment);
        mArticleFragment = (ArticleFragment) getFragmentManager().findFragmentById(R.id.article_fragment);

        updateWatch();
        initBtn();
        setUpScrollView();
    }

    private void setUpScrollView() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE :
                        // 滑过文章视图时加载评论
                        View articleView = findViewById(R.id.article_fragment_content);
                        View commentView = findViewById(R.id.article_comment_fragment_content);

                        if (articleView.getMeasuredHeight() <= v.getScrollY() + v.getHeight()) {
                            // 第一次加载时刷新数据
                            if (commentView.getVisibility() != View.VISIBLE) {
                                commentView.setVisibility(View.VISIBLE);
                                commentView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mArticleCommentFragment.getCommentData();
                                    }
                                }, 1000);
                            }

                        }
                        break;
                    default :
                        break;
                }
                return false;
            }
        });

    }

    private void updateWatch() {
        mArticle.increment("watch");
        if (this != null) {
            mArticle.update(this, null);
        }
    }

    private void initBtn() {
        View sendBtn = findViewById(R.id.action_send);
        View commentBtn = findViewById(R.id.action_comment);
        View likeBtn = findViewById(R.id.action_like);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAction();
            }
        });

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAction();
            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAction();
            }
        });
    }

    private void commentAction() {
        BaseCommentDialog dialogFragment = new ArticleCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), ArticleCommentDialog.class.getName());
    }

    private void sendAction() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(getString(R.string.app_name));  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText(mArticle.getTitle());  //最多40个字符

        //网络图片的url：所有平台
        oks.setImageUrl(mArticle.getTitleImage());//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mArticle.getUrl());   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl(mArticle.getUrl());  //网友点进链接后，可以看到分享的详情

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
            Toast.makeText(getApplicationContext(), "您已经赞过啦，亲~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    public Article getArticle() {
        return mArticle;
    }

    @Override
    public void onCommentSuccess() {
        if (mArticleCommentFragment != null) {
            mArticleCommentFragment.updateComment();
        }
    }

    @Override
    public void onCommentFailed() {

    }
}
