package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.ArticleCommentDialog;
import com.dean.travltotibet.fragment.ArticleCommentFragment;
import com.dean.travltotibet.fragment.BaseCommentDialog;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 2/21/16.
 */
public class ArticleCommentActivity extends BaseActivity implements BaseCommentDialog.CommentCallBack {

    private Article mArticle;

    private ArticleCommentFragment mArticleCommentFragment;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.article_comment_view);

        if (getIntent() != null) {
            mArticle = (Article) getIntent().getSerializableExtra(IntentExtra.INTENT_ARTICLE);
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(mArticle.getTitle());
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        mArticleCommentFragment = (ArticleCommentFragment) getFragmentManager().findFragmentById(R.id.article_comment_fragment);
        initFloatBtn();
    }


    private void initFloatBtn() {
        mFab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        mFab.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_edit, TTTApplication.getMyColor(R.color.white)));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAction();
            }
        });
    }

    private void commentAction() {
        BaseCommentDialog dialogFragment = new ArticleCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), ArticleCommentDialog.class.getName());
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

    public FloatingActionButton getFab() {
        return mFab;
    }
}
