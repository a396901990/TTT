package com.dean.travltotibet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.AnswerCommentDialog;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.dialog.TeamRequestCommentDialog;
import com.dean.travltotibet.fragment.AnswerCommentFragment;
import com.dean.travltotibet.fragment.TeamShowRequestCommentFragment;
import com.dean.travltotibet.model.AnswerInfo;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.Report;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.model.UserMessage;
import com.dean.travltotibet.ui.like.LikeButton;
import com.dean.travltotibet.ui.like.OnLikeListener;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.ScreenUtil;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 5/11/16.
 */
public class AnswerDetailActivity extends BaseCommentActivity {

    private AnswerInfo answerInfo;

    private boolean isPersonal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.answer_detail_view);
        EventBus.getDefault().register(this);

        if (getIntent() != null) {
            answerInfo = (AnswerInfo) getIntent().getSerializableExtra(IntentExtra.INTENT_ANSWER);
            isPersonal = getIntent().getBooleanExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, false);
        }
        if (answerInfo == null) {
            finish();
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator();

        updateWatch();
        initHeader();
        initBottom();
    }

    @Override
    public BmobObject getObj() {
        return answerInfo;
    }

    private void initHeader() {
        // title
        if (isPersonal) {
            setTitle("我的答案");
        } else {
            setTitle("答案详情");
        }
    }

    private void refresh() {
        AnswerCommentFragment fragment = (AnswerCommentFragment) getFragmentManager().findFragmentById(R.id.comment_fragment);
        if (fragment != null && fragment.isAdded()) {
            fragment.onRefresh();
        }
    }

    private void updateLike() {
        try {
            answerInfo.increment("like");
            if (this != null) {
                answerInfo.update(this, null);
            }
        } catch (Exception e) {
            // finish();
        }
    }

    private void updateWatch() {
        try {
            final int watch = answerInfo.getWatch().intValue();
            answerInfo.increment("watch");
            answerInfo.update(this, new UpdateListener() {
                @Override
                public void onSuccess() {
                    answerInfo.setWatch(watch+1);
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });

        } catch (Exception e) {
            // finish();
        }
    }

    private void initBottom() {

        // like
        LikeButton likeBtn = (LikeButton) findViewById(R.id.like_button);
        likeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                updateWatch();
                updateLike();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

        // comment
        View commentBtn = findViewById(R.id.comment_btn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAction();
            }
        });
    }

    /**
     * 回复操作
     */
    private void commentAction() {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        BaseCommentDialog dialogFragment = new AnswerCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), AnswerCommentDialog.class.getName());
    }

    @Override
    public void onCommentSuccess(final Comment comment) {
        // 将评论添加到当前team request的关联中
        final BmobRelation commentRelation = new BmobRelation();
        commentRelation.add(comment);
        answerInfo.setReplyComments(commentRelation);
        answerInfo.update(getApplication(), new UpdateListener() {
            @Override
            public void onSuccess() {
                refresh();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

//        // 发送新通知，成功后加入用户关联中
//        final UserInfo targetUser = answerInfo.getUser();
//        final UserMessage message = new UserMessage();
//        message.setStatus(UserMessage.UNREAD_STATUS);
//        message.setMessage(answerInfo.getContent());
//        message.setMessageTitle(UserMessage.TEAM_REQUEST_TITLE);
//        message.setType(UserMessage.TEAM_REQUEST_TYPE);
//        message.setTypeObjectId(answerInfo.getObjectId());
//        message.setSendUser(TTTApplication.getUserInfo());
//        message.save(this, new SaveListener() {
//            @Override
//            public void onSuccess() {
//                BmobRelation messageRelation = new BmobRelation();
//                messageRelation.add(message);
//                targetUser.setUserMessage(messageRelation);
//                targetUser.update(getApplication());
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
    }

    @Override
    public void onCommentFailed() {
        super.onCommentFailed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_team_request_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 结束
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (id == R.id.action_edit) {
            actionEdit();
        } else if (id == R.id.action_del) {
            actionDel();
        } else if (id == R.id.action_report) {
            actionReport();
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionReport() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.dialog_report_title))
                .positiveText(getString(R.string.ok_btn))
                .negativeText(getString(R.string.cancel_btn))
                .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        reportAction();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    private void actionDel() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.delete_action_title))
                .positiveText(getString(R.string.ok_btn))
                .negativeText(getString(R.string.cancel_btn))
                .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        deleteTeamRequest();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    private void actionEdit() {
        Intent intent = new Intent(this, TeamCreateRequestActivity.class);
        intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, answerInfo);
        startActivityForResult(intent, UPDATE_REQUEST);
        setResult(RESULT_OK);
        finish();
    }

    private void deleteTeamRequest() {

        answerInfo.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 举报
    private void reportAction() {
        new Report().addReport(this, Report.REPORT_TEAM_REQUEST, answerInfo.getObjectId(), "", answerInfo.getUserName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                BmobQuery<AnswerInfo> query = new BmobQuery<AnswerInfo>();
                query.getObject(this, answerInfo.getObjectId(), new GetListener<AnswerInfo>() {

                    @Override
                    public void onSuccess(AnswerInfo object) {
                        answerInfo = object;
                    }

                    @Override
                    public void onFailure(int code, String arg0) {
                    }
                });
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem reportItem = menu.findItem(R.id.action_report);
        MenuItem editItem = menu.findItem(R.id.action_edit);
        MenuItem delItem = menu.findItem(R.id.action_del);

        if (isPersonal) {
            editItem.setVisible(true);
            delItem.setVisible(true);
            reportItem.setVisible(false);
        } else {
            reportItem.setVisible(false);
            editItem.setVisible(false);
            delItem.setVisible(false);
        }

        return true;
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    public AnswerInfo getAnswerInfo() {
        return answerInfo;
    }

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }
}
