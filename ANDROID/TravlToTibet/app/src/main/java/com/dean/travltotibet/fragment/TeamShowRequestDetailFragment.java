package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.TeamShowRequestActivity;
import com.dean.travltotibet.model.TeamRequest;

/**
 * Created by DeanGuo on 2/24/16.
 */
public class TeamShowRequestDetailFragment extends Fragment {

    private View root;

    private TeamRequest teamRequest;

    private TeamShowRequestActivity teamShowRequestActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.team_show_request_detail_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        teamShowRequestActivity = (TeamShowRequestActivity) getActivity();
        teamRequest = teamShowRequestActivity.getTeamRequest();
        initTitleContent();
        initContactContent();
        initContentContent();
    }

    // 标题
    private void initTitleContent() {
        TextView title = (TextView) root.findViewById(R.id.title_text);
        title.setText(teamRequest.getTitle());
    }

    // 联系方式
    private void initContactContent() {
        TextView contact = (TextView) root.findViewById(R.id.contact_text);
        contact.setText(teamRequest.getContact());
    }

    // 内容
    private void initContentContent() {
        TextView content = (TextView) root.findViewById(R.id.content_text);
        content.setText(teamRequest.getContact());
    }
}
