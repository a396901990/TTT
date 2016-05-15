package com.dean.travltotibet.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import com.dean.travltotibet.R;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.dialog.RoadInfoCommentDialog;
import com.dean.travltotibet.fragment.RoadInfoCommentFragment;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.RoadInfo;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 4/9/16.
 */
public class RoadInfoDetailActivity extends BaseCommentActivity {

    private RoadInfo roadInfo;

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.road_info_show_detail_view);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        if (getIntent() != null) {
            roadInfo = (RoadInfo) getIntent().getSerializableExtra(IntentExtra.INTENT_ROAD);
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator();
        setTitle("路况详情");

        updateWatch();
        initBottom();
        initLoadingBackground();

    }

    public void gotoTop() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, 0);
            }
        });
    }


    private void refresh() {
        RoadInfoCommentFragment fragment = (RoadInfoCommentFragment) getFragmentManager().findFragmentById(R.id.comment_fragment);
        if (fragment != null && fragment.isAdded()) {
            fragment.onRefresh();
        }
    }

    private void updateWatch() {
        try {
            roadInfo.increment("watch");
            if (this != null) {
                roadInfo.update(this, null);
            }
        } catch (Exception e) {
            // finish();
        }
    }

    private void initBottom() {
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
                View commentView = findViewById(R.id.comment_content_view);
                scrollView = (ScrollView) findViewById(R.id.scroll_view);
                scrollView.smoothScrollTo(0, commentView.getTop());
            }
        });
    }

    private void commentAction() {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        BaseCommentDialog dialogFragment = new RoadInfoCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), RoadInfoCommentDialog.class.getName());
    }

    @Override
    public void onCommentSuccess(Comment comment) {
        // 将评论添加到当前road info的关联中
//        BmobRelation commentRelation = new BmobRelation();
//        commentRelation.add(comment);
//        roadInfo.setReplyComments(commentRelation);
//        roadInfo.update(getApplication(), new UpdateListener() {
//            @Override
//            public void onSuccess() {
//                refresh();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
        refresh();
    }

    @Override
    public void onCommentFailed() {
    }

    @Override
    public BmobObject getObj() {
        return roadInfo;
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    public RoadInfo getRoadInfo() {
        return roadInfo;
    }

}
