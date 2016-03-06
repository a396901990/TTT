package com.dean.travltotibet.fragment;

import android.app.Activity;
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
import com.dean.travltotibet.activity.TeamShowRequestActivity;
import com.dean.travltotibet.dialog.TeamMakeDateDialog;
import com.dean.travltotibet.dialog.TeamMakeDestinationDialog;
import com.dean.travltotibet.dialog.TeamMakeTravelTypeDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Flag;
import com.dean.travltotibet.util.IntentExtra;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.dean.travltotibet.R.id.contact_edit_text;

/**
 * Created by DeanGuo on 2/23/16.
 */
public class TeamCreateUpdateRequestFragment extends Fragment {

    private View root;

    private TeamCreateRequestActivity mActivity;

    private int PASS_DATE = 1 << 0; // 0

    private int PASS_DESTINATION = 1 << 2; // 100

    private int PASS_TYPE = 1 << 3; // 1000

    private int PASS_TITLE = 1 << 4; // 10000

    private int PASS_CONTACT = 1 << 5; // 100000

    private int PASS_CONTENT = 1 << 6; // 1000000

    private Flag filed = new Flag();

    private TeamRequest teamRequest;

    EditText titleEdit;
    EditText contactEdit;
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

        initTitleContent();
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
            // title
            if (!TextUtils.isEmpty(teamRequest.getTitle())) {
                titleEdit.setText(teamRequest.getTitle());
            }
            // contact
            if (!TextUtils.isEmpty(teamRequest.getContact())) {
                contactEdit.setText(teamRequest.getContact());
            }
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

    // 标题
    private void initTitleContent() {
        titleEdit = (EditText) root.findViewById(R.id.title_edit_text);
        titleEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
        titleEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filed.set(PASS_TITLE);
                teamRequest.setTitle(titleEdit.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // 联系方式
    private void initContactContent() {
        contactEdit = (EditText) root.findViewById(contact_edit_text);
        contactEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filed.set(PASS_CONTACT);
                teamRequest.setContact(contactEdit.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // 内容
    private void initContentContent() {
        contentEdit = (EditText) root.findViewById(R.id.content_edit_text);
        contentEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filed.set(PASS_CONTENT);
                teamRequest.setContent(contentEdit.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
        if (checkIsOk()) {
            final View loadingView = root.findViewById(R.id.loading_content_view);
            loadingView.setVisibility(View.VISIBLE);

            if (isUpdate) {
                teamRequest.setIsPass(true);
                teamRequest.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), TeamShowRequestActivity.class);
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
                teamRequest.setComments(0);
                teamRequest.setWatch(0);
                teamRequest.setIsPass(true);
                teamRequest.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                        mActivity.finish();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                        mActivity.finish();
                    }
                });
            }
        }
    }

    private boolean checkIsOk() {
        if (!filed.isSet(PASS_DATE)) {
            Toast.makeText(getActivity(), "请设置出行日期", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_DESTINATION)) {
            Toast.makeText(getActivity(), "请设置目的地", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_TYPE)) {
            Toast.makeText(getActivity(), "请设置旅行方式", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_TITLE)) {
            Toast.makeText(getActivity(), "请设置标题", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_CONTACT)) {
            Toast.makeText(getActivity(), "请设置联系方式", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_CONTENT)) {
            Toast.makeText(getActivity(), "请设置内容", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

}
