package com.dean.travltotibet.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ChartPagerAdapter;
import com.dean.travltotibet.adapter.WelcomePageAdapter;
import com.dean.travltotibet.fragment.WelcomePageFragment;
import com.dean.travltotibet.util.SystemUtil;


/**
 * Created by DeanGuo on 11/7/15.
 */
public class WelcomeActivity extends Activity {

    public static final String KEY_SHOWN = "welcome.shown";

    static final int TOTAL_PAGES = 4;

    ViewPager pager;
    PagerAdapter pagerAdapter;
    LinearLayout circles;
    Button btnDone;
    ImageButton btnPre;
    ImageButton btnNext;
    boolean isOpaque = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.welcome_layout);

        initButton();
        initViewPager();
        buildCircles();
    }

    private void initButton() {
        btnPre = ImageButton.class.cast(findViewById(R.id.btn_pre));
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() - 1, true);
            }
        });

        btnNext = ImageButton.class.cast(findViewById(R.id.btn_next));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });

        btnDone = Button.class.cast(findViewById(R.id.done));
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishTour();
            }
        });

    }

    private void initViewPager() {
        pager = (ViewPager) findViewById(R.id.pager);

        final WelcomePageAdapter adapter = new WelcomePageAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == TOTAL_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                WelcomePageFragment fragment = (WelcomePageFragment) adapter.getItem(position);
                fragment.setAnim();

                setIndicator(position);
                // 第一页
                if (position == 0) {
                    btnPre.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnDone.setVisibility(View.GONE);
                }
                // 倒数第二页
                else if (position == TOTAL_PAGES - 2) {
                    btnPre.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    btnDone.setVisibility(View.VISIBLE);
                }
                // 中间页
                else if (position < TOTAL_PAGES - 2) {
                    btnPre.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnDone.setVisibility(View.GONE);
                }
                // 最后一页
                else if (position == TOTAL_PAGES - 1) {
                    finishTour();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.post(new Runnable() {
            @Override
            public void run() {
                WelcomePageFragment fragment = (WelcomePageFragment) adapter.getItem(0);
                fragment.setAnim();
            }
        });

    }

    private void buildCircles() {
        circles = LinearLayout.class.cast(findViewById(R.id.circles));

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);

        for (int i = 0; i < TOTAL_PAGES - 1; i++) {
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.ic_checkbox_blank_circle_white_18dp);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }

        setIndicator(0);
    }

    private void setIndicator(int index) {
        if (index < TOTAL_PAGES) {
            for (int i = 0; i < TOTAL_PAGES - 1; i++) {
                ImageView circle = (ImageView) circles.getChildAt(i);
                if (i == index) {
                    circle.setAlpha(1f);
//                    circle.setColorFilter(getResources().getColor(R.color.white));
                } else {
                    circle.setAlpha(0.3f);
//                    circle.setColorFilter(getResources().getColor(R.color.opaque));
                }
            }
        }
    }

    public static boolean hasShown(String appVersion) {
        return appVersion != null && appVersion.equals(TTTApplication.getSharedPreferences().getString(KEY_SHOWN, null));
    }

    private void saveVersion() {
        String currentAppVersion = SystemUtil.getAppVersion(this);
        TTTApplication.getSharedPreferences().edit().putString(KEY_SHOWN, currentAppVersion).commit();
    }

    public void finishTour() {
        saveVersion();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pager != null) {
            pager.clearOnPageChangeListeners();
        }
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }
}
