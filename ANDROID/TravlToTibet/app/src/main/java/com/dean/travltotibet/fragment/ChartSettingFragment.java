package com.dean.travltotibet.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.ChartSettingListAdapter;
import com.dean.travltotibet.ui.NormalDialog;
import com.dean.travltotibet.util.PointManager;
import com.mobeta.android.dslv.DragSortListView;

/**
 * Created by DeanGuo on 11/5/15.
 */
public class ChartSettingFragment extends ListFragment {

    private ChartSettingListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart_setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        DragSortListView list = (DragSortListView) getListView();
        list.setDividerHeight(0);
        list.setBackgroundResource(R.color.white_background);

        mAdapter = new ChartSettingListAdapter(list);
        setListAdapter(mAdapter);
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

                    PointManager.setAllPoints(mAdapter.getAllPoints());
                    PointManager.setCurrentPoints(mAdapter.getSelectedPoints());
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
            PointManager.setAllPoints(mAdapter.getAllPoints());
            PointManager.setCurrentPoints(mAdapter.getSelectedPoints());

            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
        // 重置按钮
        else if (item.getItemId() == R.id.action_reset) {
            final NormalDialog mDialog = new NormalDialog(getActivity(), R.style.Transparent_Dialog);
            // 对话框视图
            mDialog.setTitle(getString(R.string.reset_dialog_title));
            mDialog.setMsg(getString(R.string.reset_dialog_msg));
            mDialog.setOKListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.resetToDefault();
                    mDialog.dismiss();
                }
            });
            mDialog.setCancelListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            mDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chart_setting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        PointManager.setAllPoints(mAdapter.getAllPoints());
        PointManager.setCurrentPoints(mAdapter.getSelectedPoints());
        super.onDestroy();
    }
}
