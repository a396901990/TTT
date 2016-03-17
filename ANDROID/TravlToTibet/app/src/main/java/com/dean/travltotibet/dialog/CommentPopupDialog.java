package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.Report;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 2/21/16.
 */
public class CommentPopupDialog extends DialogFragment {

    private View contentLayout;

    private Comment mComment;

    private String mCommentType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mComment = (Comment) getArguments().getSerializable(IntentExtra.INTENT_COMMENT);
            mCommentType = getArguments().getString(IntentExtra.INTENT_COMMENT_TYPE, "");
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.base_comment_popup_view, null);

        setUpView();
        return contentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ScreenUtil.dip2px(getActivity(), 260), WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void setUpView() {
        // reply
        View reply = contentLayout.findViewById(R.id.reply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyAction();
            }
        });

        // like
        View like = contentLayout.findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAction();
            }
        });

        // report
        View report = contentLayout.findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
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
        });

        // copy
        View copy = contentLayout.findViewById(R.id.copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyAction();
            }
        });

    }

    private void replyAction() {
        BaseCommentDialog dialogFragment = null;

        // 根据不同评论类型进行恢复
        if (Comment.ARTICLE_COMMENT.equals(mCommentType)) {
            dialogFragment = new ArticleCommentDialog();
        }
        else if (Comment.TEAM_REQUEST_COMMENT.equals(mCommentType)) {
            dialogFragment = new TeamRequestCommentDialog();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentExtra.INTENT_COMMENT, mComment);
        dialogFragment.setArguments(bundle);
        dialogFragment.setCommentCallBack((BaseCommentActivity) getActivity());
        dialogFragment.show(getFragmentManager(), BaseCommentDialog.class.getName());

        getDialog().dismiss();
    }

    private void reportAction() {
        new Report().addReport(getActivity(), Report.REPORT_COMMENT, mComment.getObjectId(), mComment.getUser_id(), mComment.getUser_name());
        getDialog().dismiss();
    }

    private void copyAction() {
        ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
        cmb.setText(mComment.getComment());
        Toast.makeText(getActivity(), getString(R.string.copy_success), Toast.LENGTH_LONG).show();
        getDialog().dismiss();
    }

    private void likeAction() {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        String objectId = sharedPreferences.getString(mComment.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            mComment.increment("like");
            mComment.update(getActivity(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    sharedPreferences.edit().putString(mComment.getObjectId(), mComment.getObjectId()).commit();
                }

                @Override
                public void onFailure(int i, String s) {
                }
            });
        } else {
            Toast.makeText(getActivity(), getString(R.string.text_commented), Toast.LENGTH_SHORT).show();
        }
        getDialog().dismiss();
    }

}