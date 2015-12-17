package com.dean.travltotibet.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.ChangeColorIconWithTextView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 10/15/15.
 */
public class GuideFragment extends Fragment
        implements
        ViewPager.OnPageChangeListener, View.OnClickListener {

    private RouteActivity mActivity;
    private ViewPager mViewPager;
    private List<Fragment> mTabs;
    private FragmentPagerAdapter mAdapter;

    private List<ChangeColorIconWithTextView> mTabIndicator;


    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_fragment_layout, container, false);
        return root;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (RouteActivity) getActivity();
        mViewPager = (ViewPager) root.findViewById(R.id.id_viewpager);
        mTabs = new ArrayList<Fragment>();
        mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

        initDate();

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initDate() {

        mTabs.add(RouteGuideFragment.newInstance());
        mTabs.add(new TabFragment());
        mTabs.add(new TabFragment());
        mTabs.add(new TabFragment());

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mTabs.get(arg0);
            }
        };

        initTabIndicator();

    }

    /**
     * 获取plan bundle 包括date start end
     */
    private Bundle getPlanBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(IntentExtra.INTENT_DATE, mActivity.getPlanDate());
        bundle.putString(IntentExtra.INTENT_START, mActivity.getPlanStart());
        bundle.putString(IntentExtra.INTENT_END, mActivity.getPlanEnd());

        return bundle;
    }

    private void initTabIndicator() {
        ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) root.findViewById(R.id.id_indicator_one);
        ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) root.findViewById(R.id.id_indicator_two);
        ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) root.findViewById(R.id.id_indicator_three);
        ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) root.findViewById(R.id.id_indicator_four);

        mTabIndicator.add(one);
        mTabIndicator.add(two);
        mTabIndicator.add(three);
        mTabIndicator.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f);
    }

    @Override
    public void onPageSelected(int arg0) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

        if (positionOffset > 0) {
            ChangeColorIconWithTextView left = mTabIndicator.get(position);
            ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        resetOtherTabs();

        switch (v.getId()) {
            case R.id.id_indicator_one:
                mTabIndicator.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_two:
                mTabIndicator.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_indicator_three:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_indicator_four:
                mTabIndicator.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;

        }

    }

    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator.size(); i++) {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }


}

