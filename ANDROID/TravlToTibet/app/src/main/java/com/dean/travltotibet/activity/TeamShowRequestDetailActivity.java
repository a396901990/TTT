package com.dean.travltotibet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.dialog.TeamRequestCommentDialog;
import com.dean.travltotibet.fragment.TeamShowRequestCommentFragment;
import com.dean.travltotibet.fragment.TeamShowRequestDetailFragment;
import com.dean.travltotibet.model.Report;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserFavorites;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.ScreenUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
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

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private boolean isPersonal = false;

    static final int UPDATE_REQUEST = 0;

    private UserFavorites curUserFavorite;

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
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        updateWatch();
        initViewPage();
        initHeader();
        initBottom();
    }

    private void initHeader() {
        if (isPersonal) {
            setTitle("我的结伴");
        } else {
            setTitle("结伴详情");
            updateMenu();
        }
    }

    private void updateMenu() {
        if (TTTApplication.hasLoggedIn()) {
            BmobQuery<UserFavorites> query = new BmobQuery<UserFavorites>();
            query.addWhereEqualTo("userId", TTTApplication.getUserInfo().getUserId());
            query.addWhereEqualTo("type", UserFavorites.TEAM_REQUEST);
            query.addWhereEqualTo("typeObjectId", teamRequest.getObjectId());
            query.findObjects(this, new FindListener<UserFavorites>() {
                @Override
                public void onSuccess(List<UserFavorites> list) {
                    curUserFavorite = list.get(0);
                    invalidateOptionsMenu();
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }
    }

    private void initViewPage() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new ViewPageFragmentAdapter(getFragmentManager());

        // 为adapter添加数据
        mAdapter.add(TeamShowRequestDetailFragment.class, null, "");
        mAdapter.add(TeamShowRequestCommentFragment.class, null, "");
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
            teamRequest.increment("watch");
            if (this != null) {
                teamRequest.update(this, null);
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
        BaseCommentDialog dialogFragment = new TeamRequestCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), TeamRequestCommentDialog.class.getName());
    }

    @Override
    public void onCommentSuccess() {
        goCommentPage();
        TeamShowRequestCommentFragment articleCommentFragment = (TeamShowRequestCommentFragment) mAdapter.getFragment(1);
        if (articleCommentFragment != null) {
            articleCommentFragment.showNewComment();
        }
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
        } else if (id == R.id.action_favorite) {
            actionFavorite();
        } else if (id == R.id.action_report) {
            actionReport();
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionFavorite() {

        // 没登陆
        if (!TTTApplication.hasLoggedIn() ||  TTTApplication.getUserInfo() == null) {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.show(getFragmentManager(), LoginDialog.class.getName());
        } else {
            // 已经收藏，则取消收藏
            if (curUserFavorite != null) {
                curUserFavorite.delete(this, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.cancel_favorite_success), Toast.LENGTH_SHORT).show();
                        curUserFavorite = null;
                        invalidateOptionsMenu();
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
                        invalidateOptionsMenu();
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
                .content(getString(R.string.dialog_report_msg))
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
                .content(getString(R.string.delete_action_msg))
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

    private void deleteTeamRequest() {
        teamRequest.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
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

    private void actionEdit() {
        Intent intent = new Intent(this, TeamCreateRequestActivity.class);
        intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, teamRequest);
        startActivity(intent);
        finish();
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
        MenuItem favoriteItem = menu.findItem(R.id.action_favorite);
        MenuItem reportItem = menu.findItem(R.id.action_report);
        MenuItem editItem = menu.findItem(R.id.action_edit);
        MenuItem delItem = menu.findItem(R.id.action_del);

        if (isPersonal) {
            editItem.setVisible(true);
            delItem.setVisible(true);
            favoriteItem.setVisible(false);
            reportItem.setVisible(false);
        } else {
            favoriteItem.setVisible(true);
            reportItem.setVisible(true);
            editItem.setVisible(false);
            delItem.setVisible(false);

            if (curUserFavorite != null) {
                favoriteItem.setIcon(R.drawable.icon_favorite_red);
            } else {
                favoriteItem.setIcon(R.drawable.icon_favorite);
            }
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
        updateMenu();
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }
}
