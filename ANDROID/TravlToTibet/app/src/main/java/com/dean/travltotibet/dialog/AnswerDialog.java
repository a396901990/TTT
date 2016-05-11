package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.LoginDialogFragment;
import com.dean.travltotibet.model.AnswerInfo;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.ui.RotateLoading;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 5/5/16.
 */
public class AnswerDialog extends LoginDialogFragment {

    private View contentLayout;

    private EditText commentView;

    private View submitContent;

    private TextView submitText;

    private RotateLoading submitProgressBar;

    private Handler mHandle;

    private AnswerCallBack answerCallBack;

    private QARequest qaRequest;

    AnswerInfo answerInfo;

    private static final float ENABLE_ALPHA = 1f;

    private static final float DISABLE_ALPHA = 0.3f;

    protected final static int SUBMIT_SUCCESS = 1;
    protected final static int SUBMIT_FAILURE = 2;
    protected final static int SUBMIT_ENABLE = 3;
    protected final static int SUBMIT_DISABLE = 4;
    protected final static int SUBMIT_BEGIN = 5;

    public static interface AnswerCallBack {
        void onAnswerSuccess(AnswerInfo answerInfo);

        void onAnswerFailed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PopupDialog);

        mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case SUBMIT_ENABLE:
                        submitContent.setEnabled(true);
                        submitText.setAlpha(ENABLE_ALPHA);
                        break;
                    case SUBMIT_DISABLE:
                        submitContent.setEnabled(false);
                        submitText.setAlpha(DISABLE_ALPHA);
                        break;
                    case SUBMIT_BEGIN:
                        submitContent.setEnabled(false);
                        submitProgressBar.start();
                        break;
                    case SUBMIT_SUCCESS:
                        submitProgressBar.stop();
                        submitContent.setEnabled(true);
                        submitText.setText(getString(R.string.comment_success));
                        submitContent.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (getDialog() != null) {
                                    getDialog().dismiss();
                                }
                                if (answerCallBack != null) {
                                    answerCallBack.onAnswerSuccess(answerInfo);
                                }
                            }
                        }, 1000);
                        break;
                    case SUBMIT_FAILURE:
                        submitProgressBar.stop();
                        submitContent.setEnabled(true);
                        submitText.setText(getString(R.string.comment_failed));
                        break;
                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.answer_dialog_view, null);
        initLoginView(contentLayout);
        initCommentView();
        initSubmitView();
        return contentLayout;
    }

    private void initSubmitView() {
        submitContent = contentLayout.findViewById(R.id.submit_content);
        submitText = (TextView) contentLayout.findViewById(R.id.submit_text);
        submitProgressBar = (RotateLoading) contentLayout.findViewById(R.id.submit_progress_bar);

        mHandle.sendEmptyMessage(SUBMIT_DISABLE);

        submitContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCommit();
            }
        });
    }

    private void initCommentView() {
        commentView = (EditText) contentLayout.findViewById(R.id.comment_view);
        commentView.requestFocus();
        commentView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    mHandle.sendEmptyMessage(SUBMIT_ENABLE);
                } else {
                    mHandle.sendEmptyMessage(SUBMIT_DISABLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void submitCommit() {
        if (TTTApplication.hasLoggedIn()) {
            mHandle.sendEmptyMessage(SUBMIT_BEGIN);
        } else {
            DialogFragment dialogFragment = new LoginDialog();
            dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
            return;
        }

        answerInfo = new AnswerInfo();
        if (qaRequest != null) {
            answerInfo.setQaRequest(qaRequest);
            answerInfo.setQuestionTitle(qaRequest.getTitle());
        }
        // 评论
        answerInfo.setContent(getComment());
        // 评分
        answerInfo.setLike(0);
        answerInfo.setUnlike(0);
        answerInfo.setStatus(AnswerInfo.PASS_STATUS);
        if (TTTApplication.getUserInfo() != null) {
            answerInfo.setUser(TTTApplication.getUserInfo());
            answerInfo.setUserIcon(TTTApplication.getUserInfo().getUserIcon());
            answerInfo.setUserGender(TTTApplication.getUserInfo().getUserGender());
            answerInfo.setUserName(TTTApplication.getUserInfo().getUserName());
        }

        answerInfo.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                getHandle().sendEmptyMessage(SUBMIT_SUCCESS);
            }

            @Override
            public void onFailure(int code, String msg) {
                getHandle().sendEmptyMessage(SUBMIT_FAILURE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }

    protected String getComment() {
        return commentView.getText().toString();
    }

    protected Handler getHandle() {
        return mHandle;
    }

    public void setAnswerCallBack(AnswerCallBack answerCallBack) {
        this.answerCallBack = answerCallBack;
    }

    public void setQaRequest(QARequest qaRequest) {
        this.qaRequest = qaRequest;
    }

}