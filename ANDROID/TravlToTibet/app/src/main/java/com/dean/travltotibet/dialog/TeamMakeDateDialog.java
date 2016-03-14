package com.dean.travltotibet.dialog;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.util.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DeanGuo on 3/5/16.
 * 逻辑太屎，只为功能
 */
public class TeamMakeDateDialog extends DialogFragment {

    private final static int NO_SURE_LIMIT = 12;

    private View contentLayout;

    private TravelDateCallback dateCallback;

    private TextView startDateTextView;

    private TextView endDateTextView;

    private EditText notSureEditText;

    private String dateText;

    private String startDateText, endDateText;

    public static interface TravelDateCallback {
        void dateChanged(String type);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.team_create_date_dialog_view, null);
        startDateTextView = (TextView) contentLayout.findViewById(R.id.start_date_text);
        endDateTextView = (TextView) contentLayout.findViewById(R.id.end_date_text);
        initTimeContent();
        initNotSureTimeContent();
        initCommitBtn();
        return contentLayout;
    }

    private void initCommitBtn() {
        View commit = contentLayout.findViewById(R.id.commit_btn);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 精确时间
                if (!TextUtils.isEmpty(startDateText)) {
                    if (!TextUtils.isEmpty(endDateText)) {
                        dateText = String.format(Constants.TEAM_REQUEST_DAY, startDateText, endDateText);
                    } else {
                        Toast.makeText(getActivity(), "请设置结束日期", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (!TextUtils.isEmpty(endDateText) && TextUtils.isEmpty(startDateText)) {
                    Toast.makeText(getActivity(), "请设置起始日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(dateText)) {
                    dateCallback.dateChanged(dateText);
                    getDialog().dismiss();
                } else {
                    getDialog().dismiss();
                }
            }
        });
    }

    private void initNotSureTimeContent() {
        notSureEditText = (EditText) contentLayout.findViewById(R.id.not_sure_edit_view);
        notSureEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(NO_SURE_LIMIT)});
        notSureEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    dateText = notSureEditText.getText().toString().trim();
                    startDateTextView.setText(getString(R.string.team_make_date_setting_text));
                    startDateText = "";
                    endDateTextView.setText(getString(R.string.team_make_date_setting_text));
                    endDateText = "";
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // 日期
    private void initTimeContent() {
        View startBtn = contentLayout.findViewById(R.id.start_date_btn);
        View endBtn = contentLayout.findViewById(R.id.end_date_btn);

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
            startDateText = String.format(Constants.DATE_Y_M_D, year, monthOfYear + 1, dayOfMonth);
            startDateTextView.setText(startDateText);

            notSureEditText.setText("");

            // 如果起始日期大于结束日期
            if (!TextUtils.isEmpty(endDateText)) {
                DateFormat fmt =new SimpleDateFormat(Constants.YYYY_MM_DD_POINT);
                try {
                    Date startDateTime = fmt.parse(startDateText);
                    Date endDateTime = fmt.parse(endDateText);

                    if (startDateTime.getTime() >= endDateTime.getTime()) {
                        Toast.makeText(getActivity(), "起始日期应小于结束日期", Toast.LENGTH_SHORT).show();
                        startDateText = "";
                        startDateTextView.setText(getString(R.string.team_make_date_setting_text));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDateText = String.format(Constants.DATE_Y_M_D, year, monthOfYear + 1, dayOfMonth);
            endDateTextView.setText(endDateText);

            notSureEditText.setText("");

            // 如果结束日期大于起始日期
            if (!TextUtils.isEmpty(startDateText)) {
                DateFormat fmt =new SimpleDateFormat(Constants.YYYY_MM_DD_POINT);
                try {
                    Date startDateTime = fmt.parse(startDateText);
                    Date endDateTime = fmt.parse(endDateText);

                    if (startDateTime.getTime() >= endDateTime.getTime()) {
                        Toast.makeText(getActivity(), "结束日期应大于起始日期", Toast.LENGTH_SHORT).show();
                        endDateText = "";
                        endDateTextView.setText(getString(R.string.team_make_date_setting_text));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void setDateCallback(TravelDateCallback dateCallback) {
        this.dateCallback = dateCallback;
    }
}