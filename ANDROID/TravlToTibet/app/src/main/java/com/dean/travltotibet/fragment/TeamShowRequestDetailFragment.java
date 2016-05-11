package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ImagePickerActivity;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.adapter.ImagePickAdapter;
import com.dean.travltotibet.dialog.TeamMakeContactDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.HorizontalItemDecoration;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 2/24/16.
 */
public class TeamShowRequestDetailFragment extends Fragment {

    private View root;

    private TeamRequest teamRequest;

    private TeamShowRequestDetailActivity teamShowRequestDetailActivity;

    private ImagePickAdapter imagePickAdapter;

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
        initImageContent();
    }

    private void initImageContent() {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.picker_image_list_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new HorizontalItemDecoration(ScreenUtil.dip2px(getActivity(), 2)));
        recyclerView.setHasFixedSize(true);
        imagePickAdapter = new ImagePickAdapter(getActivity());
        imagePickAdapter.setAddImageListener(new ImagePickAdapter.AddImageListener() {
            @Override
            public void onAddImage() {
                Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
                intent.putExtra(IntentExtra.INTENT_IMAGE_SELECTED, imagePickAdapter.getData().size());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(imagePickAdapter);

        if (teamRequest.getImageFile() == null) {
            recyclerView.setVisibility(View.GONE);
            return;
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            imagePickAdapter.setImageFile(teamRequest.getImageFile());
            imagePickAdapter.addData(teamRequest.getImageFile().getImageUrls(getActivity()));
        }
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
        if (teamRequest != null && !TextUtils.isEmpty(teamRequest.getDate())) {
            travelDate.setText(teamRequest.getDate());
        } else {
            travelDate.setText("");
        }

        // destination
        TextView travelDestination = (TextView) root.findViewById(R.id.destination_text);
        if (teamRequest != null && !TextUtils.isEmpty(teamRequest.getDestination())) {
            travelDestination.setText(teamRequest.getDestination());
        } else {
            travelDestination.setText("");
        }

        // type
        TextView travelType = (TextView) root.findViewById(R.id.type_text);
        if (teamRequest != null && !TextUtils.isEmpty(teamRequest.getType())) {
            travelType.setText(teamRequest.getType());
        } else {
            travelType.setText("");
        }
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
        TextView content = (TextView) root.findViewById(R.id.message_text);
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
