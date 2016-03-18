package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.AroundSelectAdapter;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundDialogFragment extends DialogFragment {

    private View root;

    private AroundSelectAdapter adapter;

    private String routeName;

    private boolean isForward;

    private String aroundType;

    private String aroundBelong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            routeName = getArguments().getString(IntentExtra.INTENT_ROUTE);
            aroundType = getArguments().getString(IntentExtra.INTENT_AROUND_TYPE);
            aroundBelong = getArguments().getString(IntentExtra.INTENT_AROUND_BELONG);
            isForward = getArguments().getBoolean(IntentExtra.INTENT_ROUTE_DIR);
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PopupDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_select_fragment_view, container, false);
        initView();
        return root;
    }

    private void initView() {

        adapter = new AroundSelectAdapter(getActivity());
        adapter.setData(routeName, aroundBelong, aroundType, isForward);

        TextView mTitle = (TextView) root.findViewById(R.id.around_select_title);
        mTitle.setText(AroundType.getAroundName(aroundType));

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.around_select_recycler);
        // 设置横向layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }
}
