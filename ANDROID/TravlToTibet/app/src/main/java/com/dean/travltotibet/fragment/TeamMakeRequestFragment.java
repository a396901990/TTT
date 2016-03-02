package com.dean.travltotibet.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.TeamMakeRequestActivity;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.Flag;

import java.util.Calendar;

import cn.bmob.v3.listener.SaveListener;

import static com.dean.travltotibet.R.id.contact_edit_text;
import static com.dean.travltotibet.R.id.title_edit_text;

/**
 * Created by DeanGuo on 2/23/16.
 */
public class TeamMakeRequestFragment extends Fragment {

    private View root;

    private TeamMakeRequestActivity mActivity;

    private int PASS_START_TIME = 1 << 0; // 0

    private int PASS_END_TIME = 1 << 1; // 10

    private int PASS_DESTINATION = 1 << 2; // 100

    private int PASS_TYPE = 1 << 3; // 1000

    private int PASS_TITLE = 1 << 4; // 10000

    private int PASS_CONTACT = 1 << 5; // 100000

    private int PASS_CONTENT = 1 << 6; // 1000000

    private Flag filed = new Flag();

    private TeamRequest teamRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.team_make_request_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (TeamMakeRequestActivity) this.getActivity();
        teamRequest = new TeamRequest();

        initTimeContent();
        initDestinationContent();
        initTravelTypeContent();
        initTitleContent();
        initContactContent();
        initContentContent();
    }

    // 标题
    private void initTitleContent() {
        final EditText titleEdit = (EditText) root.findViewById(title_edit_text);
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
        final EditText contactEdit = (EditText) root.findViewById(contact_edit_text);
        contactEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filed.set(PASS_CONTACT);
                teamRequest.setTitle(contactEdit.getText().toString());
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
        final EditText contentEdit = (EditText) root.findViewById(R.id.content_edit_text);
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
    private void initDestinationContent() {
        View destinationBtn = root.findViewById(R.id.destination_btn);
        destinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeDestinationDialog dialogFragment = new TeamMakeDestinationDialog();
                dialogFragment.setTravelDestinationCallback(new TeamMakeDestinationDialog.TravelDestinationCallback() {
                    @Override
                    public void travelDestinationChanged(String destination) {
                        setDestination(destination);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeTravelTypeDialog.class.getName());
            }
        });
    }

    private void setTravelType(String type) {
        TextView travelType = (TextView) root.findViewById(R.id.type_text);
        travelType.setText(TravelType.getTravelText(type));
        filed.set(PASS_TYPE);
        teamRequest.setTravelType(type);
    }

    private void setDestination(String destination) {
        TextView destinationText = (TextView) root.findViewById(R.id.destination_text);
        destinationText.setText(destination);
        filed.set(PASS_DESTINATION);
        teamRequest.setDestination(destination);
    }

    // 日期
    private void initTimeContent() {
        View startBtn = root.findViewById(R.id.start_date_btn);
        View endBtn = root.findViewById(R.id.end_date_btn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dlg = new DatePickerDialog(getActivity(), startDateSetListener, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dlg.setTitle("设置出发日期");
                dlg.show();
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dlg = new DatePickerDialog(getActivity(), endDateSetListener, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dlg.setTitle("设置结束日期");
                dlg.show();
            }
        });
    }

    public DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView startDateText = (TextView) root.findViewById(R.id.start_date);
            String startDate = String.format(Constants.DATE_Y_M_D, year, monthOfYear + 1, dayOfMonth);
            startDateText.setText(startDate);
            filed.set(PASS_START_TIME);
            teamRequest.setStartDate(startDate);
        }
    };

    public DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView endDateText = (TextView) root.findViewById(R.id.end_date);
            String endDate = String.format(Constants.DATE_Y_M_D, year, monthOfYear + 1, dayOfMonth);
            endDateText.setText(endDate);
            filed.set(PASS_END_TIME);
            teamRequest.setEndDate(endDate);
        }
    };

    // 提交请求
    public void commitRequest() {
        if (checkIsOk()) {
            final View loadingView = root.findViewById(R.id.loading_content_view);
            loadingView.setVisibility(View.VISIBLE);
            teamRequest.setComments(0);
            teamRequest.setWatch(0);
            teamRequest.save(getActivity(), new SaveListener() {
                @Override
                public void onSuccess() {
                    loadingView.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int code, String msg) {
                    loadingView.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean checkIsOk() {
        if (!filed.isSet(PASS_START_TIME)) {
            Toast.makeText(getActivity(), "请设置起始日期", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_END_TIME)) {
            Toast.makeText(getActivity(), "请设置终止日期", Toast.LENGTH_SHORT).show();
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
