package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.QAShowRequestDetailActivity;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.FlowLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 5/3/16.
 */
public class QAShowRequestDetailFragment extends Fragment {

    private View root;

    private QARequest qaRequest;

    private QAShowRequestDetailActivity qaShowRequestDetailActivity;

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
        initHeaderView();
        initTitleContent();
        initContentContent();
        updateSameQuestionUsers();
    }

    private void initSameQuestionContent(List<UserInfo> userInfos) {
        View sameQuestionContent = root.findViewById(R.id.same_question_content);
        if (userInfos != null && userInfos.size() > 0) {
            sameQuestionContent.setVisibility(View.VISIBLE);

            FlowLayout flowLayout = (FlowLayout) root.findViewById(R.id.same_question_content_view);
            flowLayout.removeAllViews();
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
            }

        } else {
            sameQuestionContent.setVisibility(View.GONE);
        }
    }

    public void updateSameQuestionUsers () {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereRelatedTo("questionUsers", new BmobPointer(qaRequest));
        query.findObjects(getActivity(), new FindListener<UserInfo>() {

            @Override
            public void onSuccess(List<UserInfo> userInfos) {
                initSameQuestionContent(userInfos);
            }

            @Override
            public void onError(int code, String msg) {
                initSameQuestionContent(null);
            }
        });
    }

    private void initHeaderView() {
        TextView mUserName = (TextView) root.findViewById(R.id.user_name);
        View mUserGender = root.findViewById(R.id.user_gender);
        CircleImageView mUserIcon = (CircleImageView) root.findViewById(R.id.user_icon);
        TextView mPublishTime = (TextView) root.findViewById(R.id.publish_time);

        // publish time
        String createTime = DateUtil.getTimeGap(qaRequest.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        mPublishTime.setText(createTime);
        // user name
        mUserName.setText(qaRequest.getUserName());
        if (UserInfo.MALE.equals(qaRequest.getUserGender())) {
            mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
            mUserGender.setBackgroundResource(R.drawable.male_gender_view);
        } else {
            mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
            mUserGender.setBackgroundResource(R.drawable.female_gender_view);
        }
        // user icon
        if (!TextUtils.isEmpty(qaRequest.getUserIcon())) {
            Picasso.with(getActivity()).load(qaRequest.getUserIcon()).error(R.drawable.gray_profile).into(mUserIcon);
        } else {
            mUserIcon.setImageResource(R.drawable.gray_profile);
        }
    }

    // 内容
    private void initContentContent() {
        TextView content = (TextView) root.findViewById(R.id.content_text);
        content.setText(qaRequest.getContent());
    }

    // 标题
    private void initTitleContent() {
        TextView content = (TextView) root.findViewById(R.id.title_text);
        content.setText(qaRequest.getTitle());
    }
}
