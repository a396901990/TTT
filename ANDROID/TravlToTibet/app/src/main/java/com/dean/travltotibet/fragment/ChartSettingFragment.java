package com.dean.travltotibet.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ChartSettingSelectedListAdapter;
import com.dean.travltotibet.adapter.ChartSettingUnSelectedListAdapter;
import com.dean.travltotibet.model.PointCheck;
import com.dean.travltotibet.ui.customScrollView.InsideScrollDragSoftListView;
import com.dean.travltotibet.ui.customScrollView.InsideScrollListView;
import com.dean.travltotibet.ui.chart.PointManager;

import java.util.List;

/**
 * Created by DeanGuo on 11/5/15.
 */
public class ChartSettingFragment extends Fragment implements PointCheck.PointCheckChangedListener {

    private View root;

    private ChartSettingSelectedListAdapter mSelectedAdapter;
    private InsideScrollDragSoftListView mSelectedList;

    private ChartSettingUnSelectedListAdapter mUnSelectedAdapter;
    private InsideScrollListView mUnSelectedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.chart_setting_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mSelectedList = (InsideScrollDragSoftListView) root.findViewById(R.id.selected_list);
        mSelectedAdapter = new ChartSettingSelectedListAdapter(this);
        mSelectedList.setAdapter(mSelectedAdapter);

        mUnSelectedList = (InsideScrollListView) root.findViewById(R.id.unselected_list);
        mUnSelectedAdapter = new ChartSettingUnSelectedListAdapter(this);
        mUnSelectedList.setAdapter(mUnSelectedAdapter);
    }

    @Override
    public void setToSelectedList(PointCheck pointCheck) {
        // 移除
        List<PointCheck> mUnselectedData = mUnSelectedAdapter.getData();
        mUnselectedData.remove(pointCheck);
        mUnSelectedAdapter.notifyDataSetChanged();

        // 添加
        List<PointCheck> mSelectedData = mSelectedAdapter.getData();
        pointCheck.setIsChecked(true);
        mSelectedData.add(pointCheck);
        mSelectedAdapter.notifyDataSetChanged();
    }

    @Override
    public void setToUnselectedList(PointCheck pointCheck) {
        // 移除
        List<PointCheck> mSelectedData = mSelectedAdapter.getData();
        mSelectedData.remove(pointCheck);
        mSelectedAdapter.notifyDataSetChanged();

        // 添加
        List<PointCheck> mUnselectedData = mUnSelectedAdapter.getData();
        pointCheck.setIsChecked(false);
        mUnselectedData.add(0, pointCheck);
        mUnSelectedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 设置返回按钮监听
         */
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    PointManager.setAllPoints(mSelectedAdapter.getAllPoints());
                    PointManager.setCurrentPoints(mSelectedAdapter.getSelectedPoints());
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getActivity().finish();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 结束
        if (item.getItemId() == android.R.id.home) {
            PointManager.setAllPoints(TTTApplication.getMyResources().getStringArray(R.array.default_all_points));
            PointManager.setCurrentPoints(mSelectedAdapter.getSelectedPoints());

            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chart_setting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void reset() {
        new com.afollestad.materialdialogs.MaterialDialog.Builder(getActivity())
                .title(getString(R.string.reset_dialog_title))
                .content(getString(R.string.reset_dialog_msg))
                .positiveText(getString(R.string.cancel_btn))
                .negativeText(getString(R.string.ok_btn))
                .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                .callback(new com.afollestad.materialdialogs.MaterialDialog.Callback() {
                    @Override
                    public void onPositive(com.afollestad.materialdialogs.MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(com.afollestad.materialdialogs.MaterialDialog dialog) {
                        mSelectedAdapter.resetToDefault();
                        mUnSelectedAdapter.resetToDefault();
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    @Override
    public void onDestroy() {
        PointManager.setAllPoints(TTTApplication.getMyResources().getStringArray(R.array.default_all_points));
        PointManager.setCurrentPoints(mSelectedAdapter.getSelectedPoints());
        super.onDestroy();
    }

    public ChartSettingSelectedListAdapter getmSelectedAdapter() {
        return mSelectedAdapter;
    }

    public InsideScrollDragSoftListView getmSelectedList() {
        return mSelectedList;
    }

    public ChartSettingUnSelectedListAdapter getmUnSelectedAdapter() {
        return mUnSelectedAdapter;
    }

    public InsideScrollListView getmUnSelectedList() {
        return mUnSelectedList;
    }
}
