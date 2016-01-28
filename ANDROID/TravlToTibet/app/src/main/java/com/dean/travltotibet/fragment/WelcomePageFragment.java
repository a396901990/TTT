package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import com.dean.travltotibet.R;

public class WelcomePageFragment extends Fragment {

    final static String LAYOUT_ID = "layoutId";

    final static String ANIM_TYPE = "animType";

    public final static int ANIM_ROTATE = 1;

    public final static int ANIM_ALPHA = 2;

    public final static int ANIM_TRANSLATE = 3;

    public final static int NO_ANIM = 0;

    private View root;

    private View anim_view;

    private int animType;

    public static WelcomePageFragment newInstance(int layoutId, int animType) {
        WelcomePageFragment pane = new WelcomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_ID, layoutId);
        bundle.putInt(ANIM_TYPE, animType);
        pane.setArguments(bundle);
        return pane;
    }

    private boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            if (getUserVisibleHint()) {
                isVisible = true;
            } else {
                isVisible = false;
            }
        }

        if (isVisible) {
            setAnim();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        animType = getArguments().getInt(ANIM_TYPE, 0);
        anim_view = root.findViewById(R.id.anim_icon);
        setAnim();
    }

    private void setAnim() {
        Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                anim_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_alpha);
// new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setInterpolator(new DecelerateInterpolator());
//        rotateAnimation.setDuration(1000);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                anim_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation translateAnimation = new TranslateAnimation(0.1f, 100.0f,0.1f,100.0f);
        //设置动画时间
        translateAnimation.setDuration(1000);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (NO_ANIM == animType) {
            return;
        } else if (ANIM_ALPHA == animType) {
            anim_view.startAnimation(alphaAnimation);
//            ViewGroup animViews = (ViewGroup) anim_view;
//            if (animViews.getChildCount() > 0) {
//                for (int i=0; i< animViews.getChildCount(); i++) {
//                    View view = animViews.getChildAt(i);
//                    view.startAnimation(alphaAnimation);
//                }
//            } else {

//            }
        } else if (ANIM_ROTATE == animType) {
            anim_view.startAnimation(rotateAnimation);
        } else if (ANIM_TRANSLATE == animType) {
            anim_view.startAnimation(translateAnimation);
        }
    }
}