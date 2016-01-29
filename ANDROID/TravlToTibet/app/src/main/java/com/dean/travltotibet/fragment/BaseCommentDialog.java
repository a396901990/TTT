package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.model.ScenicComment;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.RotateLoading;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.SystemUtil;

import java.net.MalformedURLException;
import java.net.URL;

import cn.bmob.v3.listener.SaveListener;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 1/20/16.
 * 选择旅行类型
 */
public class BaseCommentDialog extends LoginDialogFragment {

    private View contentLayout;

    private RatingBar ratingBar;

    private EditText commentView;

    private View submitContent;

    private TextView submitText;

    private RotateLoading submitProgressBar;

    private float currentRating;

    private Handler mHandle;

    private CommentCallBack commentCallBack;

    private static final float ENABLE_ALPHA = 1f;

    private static final float DISABLE_ALPHA = 0.3f;

    protected final static int SUBMIT_SUCCESS = 1;
    protected final static int SUBMIT_FAILURE = 2;
    protected final static int SUBMIT_ENABLE = 3;
    protected final static int SUBMIT_DISABLE = 4;
    protected final static int SUBMIT_BEGIN = 5;

    public static interface CommentCallBack {
        void onCommentSuccess();

        void onCommentFailed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                                getDialog().dismiss();
                                commentCallBack.onCommentSuccess();
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
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.around_comment_dialog_view, null);
        initLoginView(contentLayout);
        initRatingView();
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
        mHandle.sendEmptyMessage(SUBMIT_BEGIN);
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

    protected String getRatting() {
        return String.valueOf(ratingBar.getRating());
    }

    protected String getUserName() {
        String userName;
        if (TTTApplication.hasLoggedIn()) {
            userName = TTTApplication.getUserInfo().getUserName();
        } else {
            userName = getString(R.string.unknow_user);
        }
        return userName;
    }

    protected String getUserIcon() {
        String userIcon;
        if (TTTApplication.hasLoggedIn()) {
            userIcon = TTTApplication.getUserInfo().getUserIcon();
        } else {
            userIcon = "";
        }
        return userIcon;
    }

    protected Handler getHandle() {
        return mHandle;
    }


    public CommentCallBack getCommentCallBack() {
        return commentCallBack;
    }

    public void setCommentCallBack(CommentCallBack commentCallBack) {
        this.commentCallBack = commentCallBack;
    }
}