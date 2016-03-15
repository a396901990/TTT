package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.dialog.TeamMakeContactDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 2/24/16.
 */
public class TeamShowRequestDetailFragment extends Fragment {

    private View root;

    private TeamRequest teamRequest;

    private TeamShowRequestDetailActivity teamShowRequestDetailActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.team_show_request_detail_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        teamShowRequestDetailActivity = (TeamShowRequestDetailActivity) getActivity();
        teamRequest = teamShowRequestDetailActivity.getTeamRequest();
        initPlanContent();
        initContactContent();
        initHeaderView();
        initContentContent();
    }

    private void initHeaderView() {
        TextView mUserName = (TextView) root.findViewById(R.id.user_name);
        View mUserGender = root.findViewById(R.id.user_gender);
        CircleImageView mUserIcon = (CircleImageView) root.findViewById(R.id.user_icon);
        TextView mPublishTime = (TextView) root.findViewById(R.id.publish_time);

        // publish time
        String createTime = DateUtil.getTimeGap(teamRequest.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        mPublishTime.setText(createTime);
        // user name
        mUserName.setText(teamRequest.getUserName());
        if (UserInfo.MALE.equals(teamRequest.getUserGender())) {
            mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
            mUserGender.setBackgroundResource(R.drawable.male_gender_view);
        } else {
            mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
            mUserGender.setBackgroundResource(R.drawable.female_gender_view);
        }
        // user icon
        if (!TextUtils.isEmpty(teamRequest.getUserIcon())) {
            Picasso.with(getActivity()).load(teamRequest.getUserIcon()).error(R.drawable.gray_profile).into(mUserIcon);
        } else {
            mUserIcon.setImageResource(R.drawable.gray_profile);
        }
    }

    private void initPlanContent() {
        View hintText = root.findViewById(R.id.plan_hint_text);
        hintText.setVisibility(View.GONE);

        // date
        TextView travelDate = (TextView) root.findViewById(R.id.travel_date);
        travelDate.setText(teamRequest.getDate());

        // destination
        TextView travelDestnation = (TextView) root.findViewById(R.id.destination_text);
        travelDestnation.setText(teamRequest.getDestination());

        // type
        TextView travelType = (TextView) root.findViewById(R.id.type_text);
        travelType.setText(teamRequest.getType());
    }

    // 联系方式
    private void initContactContent() {
        View hintText = root.findViewById(R.id.contact_hint_text);
        hintText.setVisibility(View.GONE);

        setContact(teamRequest.getContactPhone(), TeamMakeContactDialog.PHONE);
        setContact(teamRequest.getContactQQ(), TeamMakeContactDialog.QQ);
        setContact(teamRequest.getContactWeChat(), TeamMakeContactDialog.WECHAT);
    }

    private void setContact(String contact, int contactType) {

        switch (contactType) {
            case TeamMakeContactDialog.PHONE:
                TextView phoneText = (TextView) root.findViewById(R.id.phone_text);
                setCopyView(phoneText);
                if (!TextUtils.isEmpty(contact)) {
                    phoneText.setText(contact);
                } else {
                    phoneText.setText(getString(R.string.contact_default_text));
                }
                break;
            case TeamMakeContactDialog.QQ:
                TextView qqText = (TextView) root.findViewById(R.id.qq_text);
                setCopyView(qqText);
                if (!TextUtils.isEmpty(contact)) {
                    qqText.setText(contact);
                } else {
                    qqText.setText(getString(R.string.contact_default_text));
                }
                break;
            case TeamMakeContactDialog.WECHAT:
                TextView wechatText = (TextView) root.findViewById(R.id.wechat_text);
                setCopyView(wechatText);
                if (!TextUtils.isEmpty(contact)) {
                    wechatText.setText(contact);
                } else {
                    wechatText.setText(getString(R.string.contact_default_text));
                }
                break;
        }
    }

    // 内容
    private void initContentContent() {
        TextView content = (TextView) root.findViewById(R.id.content_text);
        content.setText(teamRequest.getContent());
    }

    private void setCopyView (final TextView textView) {
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                cmb.setText(textView.getText().toString());
                Toast.makeText(getActivity(), getString(R.string.copy_success), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
}
