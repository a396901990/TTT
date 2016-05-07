package com.dean.travltotibet.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.FlowLayout;
import com.dean.travltotibet.ui.tagview.Tag;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.SearchFilterManger;

/**
 * Created by DeanGuo on 5/3/16.
 */
public class TeamSearchDialog extends SearchDialog implements View.OnClickListener {

    private View contentLayout;

    private ViewGroup.LayoutParams layoutParams;

    FlowLayout curYearMonthFlowLayout, nextYearMonthFlowLayout,routeFlowLayout, typeFlowLayout;

    @Override
    public void onClick(View v) {
        String viewTag = (String) v.getTag();
        final String viewSymbol = ((TextView) v).getText().toString();

        Tag tag = new Tag(viewSymbol);
        tag.isDeletable = true;

        // month
        if (SearchFilterManger.SEARCH_MONTH.equals(viewTag)) {
            tag.layoutColor = TTTApplication.getMyColor(R.color.month_color);
            tag.setType(viewTag);
        }
        // type
        else if (SearchFilterManger.SEARCH_TYPE.equals(viewTag)) {
            tag.layoutColor = TTTApplication.getMyColor(R.color.type_color);
            tag.setType(SearchFilterManger.SEARCH_TYPE);
        }
        // route
        else if (SearchFilterManger.SEARCH_ROUTE.equals(viewTag)) {
            tag.layoutColor = TTTApplication.getMyColor(R.color.route_color);
            tag.setType(SearchFilterManger.SEARCH_ROUTE);
        }

        SearchFilterManger.addTagForTeamFilter(tag);
        getSearchCallBack().onFinished();
        getDialog().dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.team_search_filter_dialog_view, null);
        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        initCurYearMonthView();
        initNextYearMonthView();
        initHotTypeView();
        initHotRouteView();
        return contentLayout;
    }

    private void initCurYearMonthView() {
        TextView curYearText = (TextView) contentLayout.findViewById(R.id.cur_year_text);
        curYearMonthFlowLayout = (FlowLayout) contentLayout.findViewById(R.id.cur_year_month_flow_layout);

        // 如果为1月，则不显示明年
        if (DateUtil.getCurMonth() == 1) {
            curYearText.setVisibility(View.GONE);
            curYearMonthFlowLayout.setVisibility(View.GONE);
            return;
        }

        curYearText.setText(String.format(Constants.MONTH_TITLE, DateUtil.getCurYearS()));

        curYearMonthFlowLayout.removeAllViews();
        String[] months = TTTApplication.getMyResources().getStringArray(R.array.hot_month);
        for (String month : months) {
            // 大于等于当前月份显示为今年
            int m = Integer.parseInt(month.split("月")[0]);
            if (m >= DateUtil.getCurMonth()) {
                curYearMonthFlowLayout.addView(addItem(month, SearchFilterManger.SEARCH_MONTH), layoutParams);
            }
        }
    }

    private void initNextYearMonthView() {
        TextView nextYearText = (TextView) contentLayout.findViewById(R.id.next_year_text);
        nextYearText.setText(String.format(Constants.MONTH_TITLE, String.valueOf(DateUtil.getCurYear() + 1)));

        nextYearMonthFlowLayout = (FlowLayout) contentLayout.findViewById(R.id.next_year_month_flow_layout);
        nextYearMonthFlowLayout.removeAllViews();
        String[] months = TTTApplication.getMyResources().getStringArray(R.array.hot_month);
        for (String month : months) {
            // 小于当前月份显示为下一年
            int m = Integer.parseInt(month.split("月")[0]);
            if (m < DateUtil.getCurMonth()) {
                nextYearMonthFlowLayout.addView(addItem(month, SearchFilterManger.SEARCH_MONTH), layoutParams);
            }
        }
    }

    private View addItem(String name, String tag) {
        TextView itemView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_textview_item_view, null, false);

        itemView.setText(name);
        itemView.setTag(tag);

        itemView.setOnClickListener(this);
        return itemView;
    }

    private void initHotRouteView() {
        routeFlowLayout = (FlowLayout) contentLayout.findViewById(R.id.hot_route_flow_layout);
        routeFlowLayout.removeAllViews();
        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_routes);
        for (String route : routes) {
            routeFlowLayout.addView(addItem(route, SearchFilterManger.SEARCH_ROUTE), layoutParams);
        }
    }

    private void initHotTypeView() {
        typeFlowLayout = (FlowLayout) contentLayout.findViewById(R.id.hot_type_flow_layout);
        typeFlowLayout.removeAllViews();
        String[] types = TTTApplication.getMyResources().getStringArray(R.array.hot_type);
        for (String type : types) {
            typeFlowLayout.addView(addItem(type, SearchFilterManger.SEARCH_TYPE), layoutParams);
        }
    }

}