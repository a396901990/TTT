package com.dean.travltotibet.fragment;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.activity.ArticleActivity;
import com.dean.travltotibet.adapter.CommentListAdapter;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.CommentReport;
import com.dean.travltotibet.model.HotelComment;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.IntentExtra;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 2/21/16.
 * 选择旅行类型
 */
public class CommentPupupDialog  extends DialogFragment {

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
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.comment_popup_view, null);

        setUpView();
        return contentLayout;
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
                reportAction();
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
        BaseCommentDialog dialogFragment = new ArticleCommentDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentExtra.INTENT_COMMENT, mComment);
        dialogFragment.setArguments(bundle);
        dialogFragment.setCommentCallBack((ArticleActivity) getActivity());
        dialogFragment.show(getFragmentManager(), ArticleCommentDialog.class.getName());

        getDialog().dismiss();
    }

    private void reportAction() {
        CommentReport commentReport = new CommentReport();
        commentReport.setReportId(mComment.getObjectId());
        commentReport.setReportUser(mComment.getUser_name());
        commentReport.setReportType(mCommentType);
        commentReport.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), getActivity().getString(R.string.report_success), Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), getActivity().getString(R.string.action_error), Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });

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