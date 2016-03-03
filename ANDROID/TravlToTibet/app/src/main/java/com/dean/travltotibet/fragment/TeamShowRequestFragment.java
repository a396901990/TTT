package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.TeamShowRequestCommentActivity;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.TravelType;

/**
 * Created by DeanGuo on 2/24/16.
 */
public class TeamShowRequestFragment extends Fragment {

    private View root;

    private TeamRequest teamRequest;

    private TeamShowRequestCommentActivity teamShowRequestActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.team_show_request_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        teamShowRequestActivity = (TeamShowRequestCommentActivity) getActivity();
        teamRequest = teamShowRequestActivity.getTeamRequest();
        initTimeContent();
        initDestinationContent();
        initTravelTypeContent();
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

    // 旅行类型
    private void initTravelTypeContent() {
        TextView travelType = (TextView) root.findViewById(R.id.type_text);
        travelType.setText(TravelType.getTravelText(teamRequest.getTravelType()));
    }

    // 目的地
    private void initDestinationContent() {
        TextView destinationText = (TextView) root.findViewById(R.id.destination_text);
        destinationText.setText(teamRequest.getDestination());
    }

    // 日期
    private void initTimeContent() {
        TextView startDateText = (TextView) root.findViewById(R.id.start_date);
        startDateText.setText(teamRequest.getStartDate());

        TextView endDateText = (TextView) root.findViewById(R.id.end_date);
        endDateText.setText(teamRequest.getEndDate());
    }

}
