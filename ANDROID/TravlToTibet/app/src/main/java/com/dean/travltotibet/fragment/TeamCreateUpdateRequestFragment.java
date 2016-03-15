package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamCreateRequestActivity;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.dialog.TeamMakeContactDialog;
import com.dean.travltotibet.dialog.TeamMakeDateDialog;
import com.dean.travltotibet.dialog.TeamMakeDestinationDialog;
import com.dean.travltotibet.dialog.TeamMakeTravelTypeDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.Flag;
import com.dean.travltotibet.util.IntentExtra;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 2/23/16.
 */
public class TeamCreateUpdateRequestFragment extends Fragment {

    private View root;

    private TeamCreateRequestActivity mActivity;

    private final static int TEXT_MAX_LIMIT = 666;

    private final static int TEXT_MIN_LIMIT = 10;

    private int PASS_DATE = 1 << 0; // 0

    private int PASS_DESTINATION = 1 << 1; // 10

    private int PASS_TYPE = 1 << 2; // 100

    private int PASS_CONTACT = 1 << 3; // 1000

    private int PASS_CONTENT = 1 << 4; // 10000

    private Flag filed = new Flag();

    private TeamRequest teamRequest;

    EditText contentEdit;

    private boolean isUpdate = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.team_create_update_request_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (TeamCreateRequestActivity) this.getActivity();
        teamRequest = mActivity.getTeamRequest();

        initTravelDestinationContent();
        initTravelTypeContent();
        initTravelDateContent();

        initContactContent();
        initContentContent();

        filedItem();
    }

    /**
     * 如果是修改操作，则填充数据
     */
    private void filedItem() {
        if (teamRequest != null) {
            isUpdate = true;
            // contact phone,qq,wechat
            setContact(teamRequest.getContactPhone(), TeamMakeContactDialog.PHONE);
            setContact(teamRequest.getContactQQ(), TeamMakeContactDialog.QQ);
            setContact(teamRequest.getContactWeChat(), TeamMakeContactDialog.WECHAT);

            // content
            if (!TextUtils.isEmpty(teamRequest.getContent())) {
                contentEdit.setText(teamRequest.getContent());
            }
            // type
            if (!TextUtils.isEmpty(teamRequest.getType())) {
                setTravelType(teamRequest.getType());
            }
            // date
            if (!TextUtils.isEmpty(teamRequest.getDate())) {
                setTravelDate(teamRequest.getDate());
            }
            // destination
            if (!TextUtils.isEmpty(teamRequest.getDestination())) {
                setTravelDestination(teamRequest.getDestination());
            }

        } else {
            teamRequest = new TeamRequest();
        }
    }

    // 联系方式
    private void initContactContent() {
        // phone
        View phoneBtn = root.findViewById(R.id.phone_content);
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeContactDialog dialogFragment = new TeamMakeContactDialog();
                dialogFragment.setContactType(TeamMakeContactDialog.PHONE);
                dialogFragment.setContactCallback(new TeamMakeContactDialog.ContactCallback() {
                    @Override
                    public void contactChanged(String contact) {
                        setContact(contact, TeamMakeContactDialog.PHONE);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeContactDialog.class.getName());
            }
        });

        // qq
        View qqBtn = root.findViewById(R.id.qq_content);
        qqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeContactDialog dialogFragment = new TeamMakeContactDialog();
                dialogFragment.setContactType(TeamMakeContactDialog.QQ);
                dialogFragment.setContactCallback(new TeamMakeContactDialog.ContactCallback() {
                    @Override
                    public void contactChanged(String contact) {
                        setContact(contact, TeamMakeContactDialog.QQ);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeContactDialog.class.getName());
            }
        });

        // wechat
        View wechatBtn = root.findViewById(R.id.wechat_content);
        wechatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeContactDialog dialogFragment = new TeamMakeContactDialog();
                dialogFragment.setContactType(TeamMakeContactDialog.WECHAT);
                dialogFragment.setContactCallback(new TeamMakeContactDialog.ContactCallback() {
                    @Override
                    public void contactChanged(String contact) {
                        setContact(contact, TeamMakeContactDialog.WECHAT);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeContactDialog.class.getName());
            }
        });
    }

    // 内容
    private void initContentContent() {
        contentEdit = (EditText) root.findViewById(R.id.content_edit_text);
        contentEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(TEXT_MAX_LIMIT)});
        contentEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTextLimitHint();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void updateTextLimitHint() {
        TextView textLimitHint = (TextView) root.findViewById(R.id.text_limit_hint);
        String hint = String.format(Constants.TEAM_REQUEST_CONTENT_LIMIT_HINT, contentEdit.getText().length(), TEXT_MAX_LIMIT);
        textLimitHint.setText(hint);
    }

    // 旅行类型
    private void initTravelTypeContent() {
        View typeBtn = root.findViewById(R.id.type_btn);
        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeTravelTypeDialog dialogFragment = new TeamMakeTravelTypeDialog();
                dialogFragment.setTravelTypeCallback(new TeamMakeTravelTypeDialog.TravelTypeCallback() {
                    @Override
                    public void travelTypeChanged(String type) {
                        setTravelType(type);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeTravelTypeDialog.class.getName());
            }
        });
    }

    // 目的地
    private void initTravelDestinationContent() {
        View destinationBtn = root.findViewById(R.id.destination_btn);
        destinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeDestinationDialog dialogFragment = new TeamMakeDestinationDialog();
                dialogFragment.setTravelDestinationCallback(new TeamMakeDestinationDialog.TravelDestinationCallback() {
                    @Override
                    public void travelDestinationChanged(String destination) {
                        setTravelDestination(destination);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeTravelTypeDialog.class.getName());
            }
        });
    }

    // 日期
    private void initTravelDateContent() {
        View dateBtn = root.findViewById(R.id.date_btn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeDateDialog dialogFragment = new TeamMakeDateDialog();
                dialogFragment.setDateCallback(new TeamMakeDateDialog.TravelDateCallback() {
                    @Override
                    public void dateChanged(String date) {
                        setTravelDate(date);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeDateDialog.class.getName());
            }
        });
    }

    private void setTravelDate(String date) {
        TextView travelDate = (TextView) root.findViewById(R.id.travel_date);
        travelDate.setText(date);
        filed.set(PASS_DATE);
        teamRequest.setDate(date);
    }

    private void setContact(String contact, int contactType) {

        switch (contactType) {
            case TeamMakeContactDialog.PHONE:
                TextView phoneText = (TextView) root.findViewById(R.id.phone_text);
                if (!TextUtils.isEmpty(contact)) {
                    phoneText.setText(contact);
                    teamRequest.setContactPhone(contact);
                } else {
                    phoneText.setText(getString(R.string.setting_phone_text));
                    teamRequest.setContactPhone("");
                }
                break;
            case TeamMakeContactDialog.QQ:
                TextView qqText = (TextView) root.findViewById(R.id.qq_text);
                if (!TextUtils.isEmpty(contact)) {
                    qqText.setText(contact);
                    teamRequest.setContactQQ(contact);
                } else {
                    qqText.setText(getString(R.string.setting_qq_text));
                    teamRequest.setContactQQ("");
                }
                break;
            case TeamMakeContactDialog.WECHAT:
                TextView wechatText = (TextView) root.findViewById(R.id.wechat_text);
                if (!TextUtils.isEmpty(contact)) {
                    wechatText.setText(contact);
                    teamRequest.setContactWeChat(contact);
                } else {
                    wechatText.setText(getString(R.string.setting_wechat_text));
                    teamRequest.setContactWeChat("");
                }
                break;
        }
    }

    private void setTravelType(String type) {
        TextView travelType = (TextView) root.findViewById(R.id.type_text);
        travelType.setText(type);
        filed.set(PASS_TYPE);
        teamRequest.setType(type);
    }

    private void setTravelDestination(String destination) {
        TextView travelDestination = (TextView) root.findViewById(R.id.destination_text);
        travelDestination.setText(destination);
        filed.set(PASS_DESTINATION);
        teamRequest.setDestination(destination);
    }

    // 提交请求
    public void commitRequest() {
        // set content value
        String contentString = contentEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(contentString) && contentString.length() >= TEXT_MIN_LIMIT) {
            filed.set(PASS_CONTENT);
            teamRequest.setContent(contentEdit.getText().toString().trim());
        } else {
            filed.clear(PASS_CONTENT);
        }

        if (checkIsOk()) {
            final View loadingView = root.findViewById(R.id.loading_content_view);
            loadingView.setVisibility(View.VISIBLE);

            if (isUpdate) {
                // 如果之前已经通过，则这次也通过
                if (TeamRequest.PASS_STATUS.equals(teamRequest.getStatus())) {
                    teamRequest.setStatus(TeamRequest.PASS_STATUS);
                }
                // 如果之前是审核或者未通过，这次是审核
                else {
                    teamRequest.setStatus(TeamRequest.WAIT_STATUS);
                }
                teamRequest.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), TeamShowRequestDetailActivity.class);
                        intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, teamRequest);
                        intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, true);
                        getActivity().startActivity(intent);
                        mActivity.finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                        mActivity.finish();
                    }
                });
            } else {
                teamRequest.setUserId(TTTApplication.getUserInfo().getUserId());
                teamRequest.setUserName(TTTApplication.getUserInfo().getUserName());
                teamRequest.setUserIcon(TTTApplication.getUserInfo().getUserIcon());
                teamRequest.setUserGender(TTTApplication.getUserInfo().getUserGender());
                teamRequest.setComments(0);
                teamRequest.setWatch(0);
                teamRequest.setStatus(TeamRequest.PASS_STATUS);
                teamRequest.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                        mActivity.setResult(mActivity.RESULT_OK);
                        mActivity.finish();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                        mActivity.setResult(mActivity.RESULT_CANCELED);
                        mActivity.finish();
                    }
                });
            }
        }
    }

    private boolean checkIsOk() {

        // 设置contact是否通过，如果不等于默认值则通过
        TextView phoneText = (TextView) root.findViewById(R.id.phone_text);
        TextView qqText = (TextView) root.findViewById(R.id.qq_text);
        TextView wechatText = (TextView) root.findViewById(R.id.wechat_text);
        if (getString(R.string.setting_phone_text).equals(phoneText.getText()) && getString(R.string.setting_qq_text).equals(qqText.getText()) && getString(R.string.setting_wechat_text).equals(wechatText.getText())) {
            filed.clear(PASS_CONTACT);
        } else {
            filed.set(PASS_CONTACT);
        }

        if (!filed.isSet(PASS_DATE)) {
            Toast.makeText(getActivity(), "请设置出行日期", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_DESTINATION)) {
            Toast.makeText(getActivity(), "请设置目的地", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_TYPE)) {
            Toast.makeText(getActivity(), "请设置旅行方式", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_CONTACT)) {
            Toast.makeText(getActivity(), "请设置联系方式", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_CONTENT)) {
            Toast.makeText(getActivity(), "内容不得少于10个字", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

}
