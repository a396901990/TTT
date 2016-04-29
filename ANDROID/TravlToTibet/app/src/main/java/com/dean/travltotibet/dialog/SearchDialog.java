package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
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
import com.dean.travltotibet.util.SearchFilterManger;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 3/8/16.
 */
public class SearchDialog extends DialogFragment implements View.OnClickListener {

    private View contentLayout;

    private ViewGroup.LayoutParams layoutParams;

    FlowLayout monthFlowLayout, routeFlowLayout, typeFlowLayout;

    ArrayList<Tag> tags = new ArrayList<>();

    private SearchCallBack searchCallBack;

    public static interface SearchCallBack {
        public void onFilter(ArrayList<Tag> tags);
    }

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
        searchCallBack.onFilter(tags);
        getDialog().dismiss();
    }

    public boolean hasSameTypeTags(ArrayList<Tag> tags, Tag tag) {
        for (Tag t : tags) {
            if (t.getType().equals(tag.getType())) {
                t.text = tag.text;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tags = SearchFilterManger.getTeamFilterTags();
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.search_filter_dialog_view, null);
        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        initHotMonthView();
        initHotTypeView();
        initHotRouteView();
        return contentLayout;
    }

    private void initHotMonthView() {
        monthFlowLayout = (FlowLayout) contentLayout.findViewById(R.id.hot_month_flow_layout);
        monthFlowLayout.removeAllViews();
        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_month);
        for (String route : routes) {
            monthFlowLayout.addView(addItem(route, SearchFilterManger.SEARCH_MONTH), layoutParams);
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

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public SearchCallBack getSearchCallBack() {
        return searchCallBack;
    }

    public void setSearchCallBack(SearchCallBack searchCallBack) {
        this.searchCallBack = searchCallBack;
    }

}