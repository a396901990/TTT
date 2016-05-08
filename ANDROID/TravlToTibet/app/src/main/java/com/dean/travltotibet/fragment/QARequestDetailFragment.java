package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.QAShowRequestDetailActivity;
import com.dean.travltotibet.dialog.AnswerDialog;
import com.dean.travltotibet.model.AnswerInfo;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.FlowLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 5/3/16.
 */
public class QARequestDetailFragment extends Fragment {

    private View root;

    private QARequest qaRequest;

    private QAShowRequestDetailActivity qaShowRequestDetailActivity;

    private TextView sameQuestionBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.q_a_show_request_detail_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        qaShowRequestDetailActivity = (QAShowRequestDetailActivity) getActivity();
        qaRequest = qaShowRequestDetailActivity.getQaRequest();

        initContentContent();
        initSameQuestionContent();

    }

    private void initSameQuestionContent() {
        sameQuestionBtn = (TextView) root.findViewById(R.id.same_question_btn);
        sameQuestionBtn.setTag(false);  // 默认没同问
        sameQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }

                // true : 已同问，则取消同问
                if ((Boolean) sameQuestionBtn.getTag()) {
                    cancelSameQuestionAction();
                }
                // false ：没同问，进行添加同问
                else {
                    addSameQuestionAction();
                }
            }
        });

        updateSameQuestionUsers();

        // answer
        View answerBtn = root.findViewById(R.id.answer_btn);
        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerAction();
            }
        });
    }

    private void answerAction() {
        if (ScreenUtil.isFastClick()) {
            return;
        }

        AnswerDialog dialogFragment = new AnswerDialog();
        dialogFragment.setQaRequest(qaRequest);
        dialogFragment.setAnswerCallBack(new AnswerDialog.AnswerCallBack() {
            @Override
            public void onAnswerSuccess(AnswerInfo answerInfo) {
                if (answerInfo != null) {
                    // 加入当前关联
                    BmobRelation answersRelation = new BmobRelation();
                    answersRelation.add(answerInfo);
                    qaRequest.setAnswers(answersRelation);
                    qaRequest.update(getActivity(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            // 刷新答案列表
                            if (qaShowRequestDetailActivity != null) {
                                qaShowRequestDetailActivity.refresh();
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onAnswerFailed() {

            }
        });
        dialogFragment.show(getFragmentManager(), AnswerDialog.class.getName());
    }

    private void addSameQuestionAction() {
        UserInfo userInfo = TTTApplication.getUserInfo();
        if (userInfo == null) {
            return;
        }

        BmobRelation sameQuestionRelation = new BmobRelation();
        sameQuestionRelation.add(userInfo);
        qaRequest.setQuestionUsers(sameQuestionRelation);
        qaRequest.update(getActivity(), new UpdateListener() {
            @Override
            public void onSuccess() {
                updateSameQuestionUsers();

                // 存入user中
                UserInfo userInfo = TTTApplication.getUserInfo();
                BmobRelation qaRelation = new BmobRelation();
                qaRelation.add(qaRequest);
                userInfo.setQAFavorite(qaRelation);
                userInfo.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.favorite_success), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.action_error), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private void cancelSameQuestionAction() {
        UserInfo userInfo = TTTApplication.getUserInfo();
        if (userInfo == null) {
            return;
        }

        BmobRelation sameQuestionRelation = new BmobRelation();
        sameQuestionRelation.remove(userInfo);
        qaRequest.setQuestionUsers(sameQuestionRelation);
        qaRequest.update(getActivity(), new UpdateListener() {
            @Override
            public void onSuccess() {
                updateSameQuestionUsers();

                UserInfo userInfo = TTTApplication.getUserInfo();
                BmobRelation qaRelation = new BmobRelation();
                qaRelation.remove(qaRequest);
                userInfo.setQAFavorite(qaRelation);
                userInfo.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.cancel_favorite), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.action_error), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private void updateSameQuestionContent(List<UserInfo> userInfos) {

        // reset
        FlowLayout flowLayout = (FlowLayout) root.findViewById(R.id.same_question_content_view);
        flowLayout.removeAllViews();
        sameQuestionBtn.setText("同问");
        sameQuestionBtn.setTag(false);

        if (userInfos == null) {
            return;
        }

        for (UserInfo userInfo : userInfos) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.flow_image_item_view, null, false);
            CircleImageView userView = (CircleImageView) itemView.findViewById(R.id.item_view);

            Picasso.with(getActivity())
                    .load(userInfo.getUserIcon())
                    .resizeDimen(R.dimen.image_pick_height, R.dimen.image_pick_height)
                    .centerInside()
                    .error(R.drawable.gray_profile)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .into(userView);

            flowLayout.addView(itemView);

            // 如果你同问了这个问题，改变按钮文字
            if (userInfo.getUserId().equals(TTTApplication.getUserInfo().getUserId())) {
                sameQuestionBtn.setText("已同问");
                sameQuestionBtn.setTag(true);
            }
        }
    }

    public void updateSameQuestionUsers () {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereRelatedTo("questionUsers", new BmobPointer(qaRequest));
        query.findObjects(getActivity(), new FindListener<UserInfo>() {

            @Override
            public void onSuccess(List<UserInfo> userInfos) {
                updateSameQuestionContent(userInfos);
            }

            @Override
            public void onError(int code, String msg) {
                updateSameQuestionContent(null);
            }
        });
    }

    private void initContentContent() {
        // 标题
        TextView title = (TextView) root.findViewById(R.id.title_text);
        title.setText(qaRequest.getTitle());

        // 内容
        TextView content = (TextView) root.findViewById(R.id.content_text);
        content.setText(qaRequest.getContent());

        // publish time
        TextView mPublishTime = (TextView) root.findViewById(R.id.publish_time);
        String createTime = DateUtil.getTimeGap(qaRequest.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        mPublishTime.setText(createTime);
    }
}
