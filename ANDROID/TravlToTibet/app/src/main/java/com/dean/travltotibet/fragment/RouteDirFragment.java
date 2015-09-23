package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.activity.RouteInfoActivity;
import com.dean.travltotibet.ui.SquareView;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来控制route方向
 */
public class RouteDirFragment extends Fragment {

    private RouteInfoActivity routeInfoActivity;

    private View root;

    private View fromView;
    private View toView;

    private TextView fromText;
    private TextView toText;
    private ImageView rotateArrow;

    public RouteDirFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_dir_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        routeInfoActivity = (RouteInfoActivity) getActivity();

        fromView = root.findViewById(R.id.from_view);
        toView = root.findViewById(R.id.to_view);
        fromText = (TextView) root.findViewById(R.id.from_text);
        toText = (TextView) root.findViewById(R.id.to_text);
        rotateArrow = (ImageView) root.findViewById(R.id.arrow);

        Button btn_f = (Button) root.findViewById(R.id.btn_select_f);
        Button btn_r = (Button) root.findViewById(R.id.btn_select_r);

        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                intent.putExtra(Constants.INTENT_ROUTE_NAME, "XINZANG");
                intent.putExtra(Constants.INTENT_ROUTE_DIR, true);
                intent.putExtra(Constants.INTENT_ROUTE_PLAN_ID, 1);
                startActivity(intent);
            }
        });

        btn_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                intent.putExtra(Constants.INTENT_ROUTE_NAME, "XINZANG");
                intent.putExtra(Constants.INTENT_ROUTE_DIR, false);
                startActivity(intent);
            }
        });

        final Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.arrow_rotate);
        operatingAnim.setAnimationListener(new SwitchAnimationListener());

        rotateArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(operatingAnim);
            }
        });
    }

    /**
     * 交换动画
     */
    private class SwitchAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            // 交换两边文字
            String from = (String) fromText.getText();
            String to = (String) toText.getText();
            fromText.setText(to);
            toText.setText(from);

            // 播放动画
            fromView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.text_translate_left_anim));
            toView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.text_translate_right_anim));
        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
