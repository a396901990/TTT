package com.dean.travltotibet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.dialog.MomentCommentDialog;
import com.dean.travltotibet.fragment.MomentCommentFragment;
import com.dean.travltotibet.fragment.MomentDetailFragment;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.Moment;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.model.UserMessage;
import com.dean.travltotibet.ui.like.LikeButton;
import com.dean.travltotibet.ui.like.OnLikeListener;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.ScreenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 5/23/16.
 */
public class MomentDetailActivity extends BaseCommentActivity {

    private Moment moment;

    private boolean isPersonal = false;

    MomentDetailFragment momentDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.moment_detail_view);
        EventBus.getDefault().register(this);

        if (getIntent() != null) {
            moment = (Moment) getIntent().getSerializableExtra(IntentExtra.INTENT_MOMENT);
            isPersonal = getIntent().getBooleanExtra(IntentExtra.INTENT_IS_PERSONAL, false);
        }
        if (moment == null) {
            finish();
        }

        momentDetailFragment = (MomentDetailFragment) getFragmentManager().findFragmentById(R.id.detail_fragment);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator();

        updateWatch();
        initHeader();
        initBottom();
    }

    @Override
    public BmobObject getObj() {
        return moment;
    }

    private void initHeader() {
        // title
        if (isPersonal) {
            setTitle("我的直播");
        } else {
            setTitle("直播详情");
        }
    }

    private void refresh() {
        MomentCommentFragment fragment = (MomentCommentFragment) getFragmentManager().findFragmentById(R.id.comment_fragment);
        if (fragment != null && fragment.isAdded()) {
            fragment.onRefresh();
        }
    }

    private void updateWatch() {
        try {
            final int watch = moment.getWatch().intValue();
            moment.increment("watch");
            moment.update(this, new UpdateListener() {
                @Override
                public void onSuccess() {
                    moment.setWatch(watch+1);
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
        changeLikeStatus(moment, likeBtn);
        likeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (momentDetailFragment!=null && momentDetailFragment.isAdded()) {
                    momentDetailFragment.likeAction(moment);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (momentDetailFragment!=null && momentDetailFragment.isAdded()) {
                    momentDetailFragment.unlikeAction(moment);
                }
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

    private void changeLikeStatus(final Moment moment, LikeButton likeBtn) {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        String objectId = sharedPreferences.getString(moment.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            likeBtn.setLiked(false);
        } else {
            likeBtn.setLiked(true);
        }
    }

    /**
     * 回复操作
     */
    private void commentAction() {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        BaseCommentDialog dialogFragment = new MomentCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), MomentCommentDialog.class.getName());
    }

    @Override
    public void onCommentSuccess(final Comment comment) {

        // 将评论添加到当前team request的关联中
        final BmobRelation commentRelation = new BmobRelation();
        commentRelation.add(comment);
        moment.setReplyComments(commentRelation);
        moment.update(getApplication(), new UpdateListener() {
            @Override
            public void onSuccess() {
                refresh();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

        // 发送新通知，成功后加入用户关联中(云端逻辑)
        final UserInfo targetUser = moment.getUser();
        final UserMessage message = new UserMessage();
        message.setStatus(UserMessage.UNREAD_STATUS);
        message.setMessage(moment.getContent());
        message.setMessageTitle(UserMessage.MOMENT_REQUEST_TITLE);
        message.setType(UserMessage.MOMENT_TYPE);
        message.setTypeObjectId(moment.getObjectId());
        message.setSendUser(TTTApplication.getUserInfo());
        message.setReceiveUser(targetUser);
        message.save(this, new SaveListener() {
            @Override
            public void onSuccess() {

                AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
                String cloudCodeName = "sendUserMessageToUser";
                JSONObject params = new JSONObject();
                try {
                    params.put("username", targetUser.getUserId());
                    params.put("messageId", message.getObjectId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ace.callEndpoint(TTTApplication.getContext(), cloudCodeName, params, new CloudCodeListener() {
                    @Override
                    public void onSuccess(Object object) {
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    @Override
    public void onCommentFailed() {
        super.onCommentFailed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_moment, menu);
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
        }
        return super.onOptionsItemSelected(item);
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
                        deleteMoment();
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
        Intent intent = new Intent(this, MomentCreateActivity.class);
        intent.putExtra(IntentExtra.INTENT_MOMENT, moment);
        startActivityForResult(intent, UPDATE_REQUEST);
        setResult(RESULT_OK);
        finish();
    }

    private void deleteMoment() {

        moment.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                // 删除文件
                if (moment.getImageFile() != null) {
                    moment.getImageFile().deleteAll(getApplicationContext());
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                BmobQuery<Moment> query = new BmobQuery<Moment>();
                query.getObject(this, moment.getObjectId(), new GetListener<Moment>() {

                    @Override
                    public void onSuccess(Moment object) {
                        moment = object;
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
            editItem.setVisible(false);
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

    public Moment getMoment() {
        return moment;
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
