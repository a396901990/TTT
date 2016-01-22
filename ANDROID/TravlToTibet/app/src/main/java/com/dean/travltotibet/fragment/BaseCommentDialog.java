package com.dean.travltotibet.fragment;

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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.model.ScenicComment;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.SystemUtil;

import cn.bmob.v3.listener.SaveListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 1/20/16.
 * 选择旅行类型
 */
public class BaseCommentDialog extends DialogFragment {

    private View contentLayout;

    private RatingBar ratingBar;

    private EditText commentView;

    private View submitContent;

    private TextView submitText;

    private float currentRating;

    private Handler mHandle;

    private static final float ENABLE_ALPHA = 1f;

    private static final float DISABLE_ALPHA = 0.3f;

    protected final static int SUBMIT_SUCCESS = 1;
    protected final static int SUBMIT_FAILURE = 2;
    protected final static int SUBMIT_ENABLE = 3;
    protected final static int SUBMIT_DISABLE = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        if (getArguments() != null) {
            currentRating = getArguments().getFloat(IntentExtra.INTENT_AROUND_RATING);
        }

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
                    case SUBMIT_SUCCESS:
                        getDialog().dismiss();
                        Toast.makeText(getActivity(), getString(R.string.comment_success), Toast.LENGTH_SHORT).show();
                        break;
                    case SUBMIT_FAILURE:
                        getDialog().dismiss();
                        Toast.makeText(getActivity(), getString(R.string.comment_failed), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.around_comment_dialog_view, null);
        initLoginHeader();
        initRatingView();
        initCommentView();
        initSubmitView();
        return contentLayout;
    }

    private void initLoginHeader() {
        TextView profile_text = (TextView) contentLayout.findViewById(R.id.profile_text);
        profile_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkToLogin();
            }
        });
        ImageView profile_image = (ImageView) contentLayout.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkToLogin();
            }
        });
    }

    private void checkToLogin() {
        if (TTTApplication.hasLoggedIn()) {

        } else {
            DialogFragment dialogFragment = new LoginDialog();
            dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
        }
    }

    private void initSubmitView() {
        submitContent = contentLayout.findViewById(R.id.submit_content);
        submitText = (TextView) contentLayout.findViewById(R.id.submit_text);
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
                if (!TextUtils.isEmpty(s)) {
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

    private void initRatingView() {

        ratingBar = (RatingBar) contentLayout.findViewById(R.id.ratting_bar);
        ratingBar.setMax(5);
        ratingBar.setRating((int) currentRating);
        ratingBar.setStepSize(1);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                currentRating = rating;
            }
        });
    }

    public void submitCommit() {
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    protected String getComment() {
        return commentView.getText().toString();
    }

    protected String getRatting() {
        return String.valueOf(ratingBar.getRating());
    }

    protected String getUserName() {
        String userName;
        if (TTTApplication.hasLoggedIn()) {
            userName = LoginUtil.getInstance().getLastToken();
        } else {
            userName = getString(R.string.unknow_user);
        }
        return userName;
    }

    protected Handler getHandle() {
        return mHandle;
    }
}