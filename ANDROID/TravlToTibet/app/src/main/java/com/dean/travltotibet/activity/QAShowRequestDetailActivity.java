package com.dean.travltotibet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.QARequestDetailFragment;
import com.dean.travltotibet.fragment.QAnswerFragment;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.model.Report;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 5/3/16.
 */
public class QAShowRequestDetailActivity extends BaseActivity {

    private QARequest qaRequest;

    private boolean isPersonal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.q_a_show_request_view);
        EventBus.getDefault().register(this);

        if (getIntent() != null) {
            qaRequest = (QARequest) getIntent().getSerializableExtra(IntentExtra.INTENT_QA_REQUEST);
            isPersonal = getIntent().getBooleanExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, false);
        }
        if (qaRequest == null) {
            finish();
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator();

        updateWatch();
        initHeader();
    }

    private void initHeader() {
        // title
        if (isPersonal) {
            setTitle("我的问答");
        } else {
            setTitle("问题详情");
        }
    }

    public void refresh() {
        QAnswerFragment fragment = (QAnswerFragment) getFragmentManager().findFragmentById(R.id.answer_fragment);
        if (fragment != null && fragment.isAdded()) {
            fragment.onRefresh();
        }
    }

    private void updateWatch() {
        try {
            final int watch = qaRequest.getWatch().intValue();
            qaRequest.increment("watch");
            qaRequest.update(this, new UpdateListener() {
                @Override
                public void onSuccess() {
                    qaRequest.setWatch(watch+1);
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } catch (Exception e) {
            // finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qa_request_show, menu);
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
                        deleteQARequest();
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
        Intent intent = new Intent(this, QAShowRequestDetailActivity.class);
        intent.putExtra(IntentExtra.INTENT_QA_REQUEST, qaRequest);
        startActivityForResult(intent, UPDATE_REQUEST);
        setResult(RESULT_OK);
        finish();
    }

    private void deleteQARequest() {

        qaRequest.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
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
        new Report().addReport(this, Report.REPORT_QA_REQUEST, qaRequest.getObjectId(), "", qaRequest.getUserName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                BmobQuery<QARequest> query = new BmobQuery<QARequest>();
                query.getObject(this, qaRequest.getObjectId(), new GetListener<QARequest>() {

                    @Override
                    public void onSuccess(QARequest object) {
                        qaRequest = object;
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

    public QARequest getQaRequest() {
        return qaRequest;
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
