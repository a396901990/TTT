package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AnswerDetailActivity;
import com.dean.travltotibet.model.AnswerInfo;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.PicassoTools;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 5/12/16.
 */
public class AnswerDetailFragment extends Fragment {

    private View root;

    private AnswerInfo answerInfo;

    private AnswerDetailActivity answerDetailActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.answer_detail_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        answerDetailActivity = (AnswerDetailActivity) getActivity();
        answerInfo = answerDetailActivity.getAnswerInfo();

        initContentContent();
    }

    private void initContentContent() {
        if (answerInfo == null) {
            return;
        }
        // 标题
        TextView title = (TextView) root.findViewById(R.id.title_text);
        title.setText(answerInfo.getQuestionTitle());

        // 内容
        TextView content = (TextView) root.findViewById(R.id.message_text);
        content.setText(answerInfo.getContent());

        // publish time
        TextView mPublishTime = (TextView) root.findViewById(R.id.publish_time);
        String createTime = DateUtil.getTimeGap(answerInfo.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        mPublishTime.setText(createTime);

        // user name
        TextView mUserName = (TextView) root.findViewById(R.id.user_name);

        mUserName.setText(answerInfo.getUserName());
        if (UserInfo.MALE.equals(answerInfo.getUserGender())) {
            mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
        } else {
            mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
        }

        // user icon
        CircleImageView mUserIcon = (CircleImageView) root.findViewById(R.id.user_icon);
        if (!TextUtils.isEmpty(answerInfo.getUserIcon())) {
            PicassoTools.getPicasso()
                    .load(answerInfo.getUserIcon())
                    .resizeDimen(R.dimen.profile_icon_size, R.dimen.profile_icon_size)
                    .centerInside()
                    .error(R.drawable.gray_profile)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .into(mUserIcon);
        } else {
            mUserIcon.setImageResource(R.drawable.gray_profile);
        }
    }
}
