package com.dean.travltotibet.dialog;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.calender.DatePickerController;
import com.dean.travltotibet.ui.calender.DayPickerView;
import com.dean.travltotibet.ui.calender.SimpleMonthAdapter;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.ScreenUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DeanGuo on 4/29/16.
 */
public class CalenderSelectedDialog extends DialogFragment implements DatePickerController {

    private View contentLayout;

    private TravelDateCallback dateCallback;

    private DayPickerView dayPickerView;

    private TextView okBtn;

    private Date firstDay, lastDay;

    public static interface TravelDateCallback {
        void dateChanged(String date, String month, int year);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.calender_fragment_layout, null);
        initView();
        return contentLayout;
    }

    private void initView() {
        dayPickerView = (DayPickerView) contentLayout.findViewById(R.id.pickerView);
        dayPickerView.setController(this);
        dayPickerView.setEnabled(true);

        okBtn = (TextView) contentLayout.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(getMDDate())) {
                    dateCallback.dateChanged(getMDDate(), getMonth(), getYear());
                }
                dismiss();
            }
        });
    }

    public String getMDDate() {
        String startDateText, endDateText;

        if (firstDay == null || lastDay == null) {
            return "";
        }

        // 不同年份显示方式不同
        if (firstDay.getTime() < lastDay.getTime()) {
            if (DateUtil.isSameYear(firstDay, lastDay)) {
                startDateText = DateUtil.formatDate(firstDay, Constants.M_D_CHINA);
                endDateText = DateUtil.formatDate(lastDay, Constants.M_D_CHINA);
            } else {
                startDateText = DateUtil.formatDate(firstDay, Constants.YYYY_M_D_POINT);
                endDateText = DateUtil.formatDate(lastDay, Constants.YYYY_M_D_POINT);
            }
        } else {
            if (DateUtil.isSameYear(firstDay, lastDay)) {
                startDateText = DateUtil.formatDate(lastDay, Constants.M_D_CHINA);
                endDateText = DateUtil.formatDate(firstDay, Constants.M_D_CHINA);
            } else {
                startDateText = DateUtil.formatDate(lastDay, Constants.YYYY_M_D_POINT);
                endDateText = DateUtil.formatDate(firstDay, Constants.YYYY_M_D_POINT);
            }
        }

        if (!TextUtils.isEmpty(startDateText) && !TextUtils.isEmpty(endDateText)) {
            String dateText = String.format(Constants.TEAM_REQUEST_DAY, startDateText, endDateText);
            return dateText;
        }

        return "";
    }


    public String getMonth() {

        StringBuffer month = new StringBuffer();

        int firstMonth, lastMonth;
        if (firstDay.getTime() < lastDay.getTime()) {
            firstMonth = Integer.parseInt(DateUtil.formatDate(firstDay, DateUtil.M));
            lastMonth = Integer.parseInt(DateUtil.formatDate(lastDay, DateUtil.M));
        } else {
            firstMonth = Integer.parseInt(DateUtil.formatDate(lastDay, DateUtil.M));
            lastMonth = Integer.parseInt(DateUtil.formatDate(firstDay, DateUtil.M));
        }

        // 4-6 : -4--5--6-
        if (firstMonth <= lastMonth) {
            for (int i = firstMonth; i <= lastMonth; i++) {
                String m = String.format(DateUtil.MONTH_MARK, i);
                month.append(m);
            }
        }
        // 2015.8-2016.1
        else {
            for (int i = firstMonth; i <= 12; i++) {
                String m = String.format(DateUtil.MONTH_MARK, i);
                month.append(m);
            }

            for (int i = 1; i <= lastMonth; i++) {
                String m = String.format(DateUtil.MONTH_MARK, i);
                month.append(m);
            }
        }

        return month.toString();
    }

    public int getYear() {

        int year;

        if (firstDay.getTime() < lastDay.getTime()) {
            year = Integer.parseInt(DateUtil.formatDate(lastDay, DateUtil.YYYY));
        } else {
            year = Integer.parseInt(DateUtil.formatDate(firstDay, DateUtil.YYYY));
        }

        return year;
    }

    public void setDateCallback(TravelDateCallback dateCallback) {
        this.dateCallback = dateCallback;
    }

    @Override
    public int getMaxYear() {
        return DateUtil.getCurYear() + 2;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
//        Log.e("tttttt", "y:"+year+"    m"+month+"    d"+day);
        okBtn.setText("完成");
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
//        Log.e("getFirst", "     "+selectedDays.getFirst().getDate() + "    "+selectedDays.getFirst().getDate().getYear());
//        Log.e("getLast", "     "+selectedDays.getLast().getDate());
        firstDay = selectedDays.getFirst().getDate();
        lastDay = selectedDays.getLast().getDate();
//        Log.e("day", "     "+selectedDays.getLast().getDate().getDay());
        int dayLength = DateUtil.daysBetween(firstDay, lastDay) + 1;
        okBtn.setText(String.format(Constants.DATE_OK_BTN, dayLength));
    }
}