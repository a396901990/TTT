package com.dean.travltotibet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.dialog.TeamRequestCommentDialog;
import com.dean.travltotibet.fragment.TeamShowRequestCommentFragment;
import com.dean.travltotibet.model.Report;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserFavorites;
import com.dean.travltotibet.ui.like.LikeButton;
import com.dean.travltotibet.ui.like.OnLikeListener;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamShowRequestDetailActivity extends BaseCommentActivity {

    private TeamRequest teamRequest;

    private boolean isPersonal = false;

    private UserFavorites curUserFavorite;

    private LikeButton favoriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.team_show_request_view);
        EventBus.getDefault().register(this);

        if (getIntent() != null) {
            teamRequest = (TeamRequest) getIntent().getSerializableExtra(IntentExtra.INTENT_TEAM_REQUEST);
            isPersonal = getIntent().getBooleanExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, false);
        }
        if (teamRequest == null) {
            finish();
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator();

        favoriteBtn = (LikeButton) findViewById(R.id.favorite_button);
        updateWatch();
        initHeader();
        initBottom();
    }

    private void initHeader() {
        // title
        if (isPersonal) {
            setTitle("我的结伴");
        } else {
            setTitle("结伴详情");
            updateFavorite();
        }
    }

    private void refresh() {
        TeamShowRequestCommentFragment fragment = (TeamShowRequestCommentFragment) getFragmentManager().findFragmentById(R.id.comment_fragment);
        if (fragment != null && fragment.isAdded()) {
            fragment.onRefresh();
        }
    }

    private void updateFavorite() {
        if (TTTApplication.hasLoggedIn()) {
            BmobQuery<UserFavorites> query = new BmobQuery<UserFavorites>();
            query.addWhereEqualTo("userId", TTTApplication.getUserInfo().getUserId());
            query.addWhereEqualTo("type", UserFavorites.TEAM_REQUEST);
            query.addWhereEqualTo("typeObjectId", teamRequest.getObjectId());
            query.findObjects(this, new FindListener<UserFavorites>() {
                @Override
                public void onSuccess(List<UserFavorites> list) {
                    if (list!=null && list.size() > 0) {
                        curUserFavorite = list.get(0);
                        favoriteBtn.setLiked(true);
                    } else {
                        favoriteBtn.setLiked(false);
                    }
                }

                @Override
                public void onError(int i, String s) {
                    favoriteBtn.setLiked(false);
                }
            });
        }
    }

    private void updateWatch() {
        try {
            teamRequest.increment("watch");
            if (this != null) {
                teamRequest.update(this, null);
            }
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
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

        favoriteBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                actionFavorite(favoriteBtn);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                actionFavorite(favoriteBtn);
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

    private void commentAction() {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        BaseCommentDialog dialogFragment = new TeamRequestCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), TeamRequestCommentDialog.class.getName());
    }

    @Override
    public void onCommentSuccess() {
        refresh();
    }

    @Override
    public void onCommentFailed() {
    }

    @Override
    public BmobObject getObj() {
        return teamRequest;
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

    private void actionFavorite(final LikeButton likeButton) {

        // 没登陆
        if (!TTTApplication.hasLoggedIn() ||  TTTApplication.getUserInfo() == null) {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.show(getFragmentManager(), LoginDialog.class.getName());
            likeButton.setLiked(false);
        } else {
            // 已经收藏，则取消收藏
            if (curUserFavorite != null) {
                curUserFavorite.delete(this, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.cancel_favorite_success), Toast.LENGTH_SHORT).show();
                        curUserFavorite = null;
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.action_error), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            // 没有收藏则收藏
            else {
                curUserFavorite = new UserFavorites();
                curUserFavorite.setTypeObjectId(teamRequest.getObjectId());
                curUserFavorite.setType(UserFavorites.TEAM_REQUEST);
                curUserFavorite.setUserId(TTTApplication.getUserInfo().getUserId());
                curUserFavorite.setUserName(TTTApplication.getUserInfo().getUserName());
                curUserFavorite.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.favorite_success), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        curUserFavorite = null;
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.action_error), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

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
        intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, teamRequest);
        startActivityForResult(intent, UPDATE_REQUEST);
        setResult(RESULT_OK);
        finish();
    }

    private void deleteTeamRequest() {

        teamRequest.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                // 删除文件
                if (teamRequest.getImageFile() != null) {
                    teamRequest.getImageFile().deleteAll(getApplicationContext());
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

    // 举报
    private void reportAction() {
        new Report().addReport(this, Report.REPORT_TEAM_REQUEST, teamRequest.getObjectId(), teamRequest.getUserId(), teamRequest.getUserName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                BmobQuery<TeamRequest> query = new BmobQuery<TeamRequest>();
                query.getObject(this, teamRequest.getObjectId(), new GetListener<TeamRequest>() {

                    @Override
                    public void onSuccess(TeamRequest object) {
                        teamRequest = object;
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
            reportItem.setVisible(true);
            editItem.setVisible(false);
            delItem.setVisible(false);
        }

        return true;
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    public TeamRequest getTeamRequest() {
        return teamRequest;
    }

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        updateFavorite();
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }
}
