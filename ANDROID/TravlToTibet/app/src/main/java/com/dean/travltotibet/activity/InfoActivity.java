package com.dean.travltotibet.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.BaseInfoFragment;
import com.dean.travltotibet.dialog.TutorialDialog;
import com.dean.travltotibet.dialog.InfoPlanConfirmDialog;
import com.dean.travltotibet.dialog.InfoTravelTypeDialog;
import com.dean.travltotibet.fragment.InfoScenicFragment;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class InfoActivity extends BaseActivity {

    private String route;
    private String routeName;
    private String routeType;

    private BaseInfoFragment headerFragment;
    private InfoScenicFragment scenicFragment;
    private BaseInfoFragment prepareFragment;

    private FloatingActionButton fab;

    private ImageView mMenuType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_view);
        if (getIntent() != null) {
            route = getIntent().getStringExtra(IntentExtra.INTENT_ROUTE);
            routeName = getIntent().getStringExtra(IntentExtra.INTENT_ROUTE_NAME);
            routeType = getIntent().getStringExtra(IntentExtra.INTENT_ROUTE_TYPE);
        }

        headerFragment = (BaseInfoFragment) getFragmentManager().findFragmentById(R.id.info_header_fragment);
        scenicFragment = (InfoScenicFragment) getFragmentManager().findFragmentById(R.id.info_scenic_fragment);
        prepareFragment = (BaseInfoFragment) getFragmentManager().findFragmentById(R.id.info_prepare_fragment);

        initView();
        initGoButton();

        initTutorialPage();
    }

    /**
     * 教程页面
     */
    private void initTutorialPage() {
        if (!TutorialDialog.hasShown(TutorialDialog.INFO_GUIDE)) {
            DialogFragment tutorialDialog = new TutorialDialog();
            Bundle bundle = new Bundle();
            bundle.putString(IntentExtra.INTENT_GUIDE_FROM, TutorialDialog.INFO_GUIDE);
            tutorialDialog.setArguments(bundle);
            tutorialDialog.show(getFragmentManager(), TutorialDialog.class.getName());
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator();

        // type btn(menu right)
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.menu_type, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.RIGHT;
        setCustomView(v, layoutParams);
        mMenuType = (ImageView) v.findViewById(R.id.header_menu_type);
        mMenuType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTypeDialog();
            }
        });

        // collapsing listener
        final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    updateMenu(true);
                } else {
                    updateMenu(false);
                }
            }
        });

        // set title
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(routeName);

        // set fab btn
        fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTypeDialog();
            }
        });

        // 更新图标
        updateMenu(false);
    }

    private void openTypeDialog() {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        DialogFragment dialogFragment = new InfoTravelTypeDialog();
        Bundle bundle = new Bundle();
        bundle.putString(IntentExtra.INTENT_ROUTE, getRoute());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), InfoTravelTypeDialog.class.getName());
    }

    /**
     * 初始化出发按钮
     */
    private void initGoButton() {

        Button go = (Button) this.findViewById(R.id.goButton);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                DialogFragment dialogFragment = InfoPlanConfirmDialog.getInstance();
                if (!dialogFragment.isAdded()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentExtra.INTENT_ROUTE, getRoute());
                    bundle.putString(IntentExtra.INTENT_ROUTE_NAME, getRouteName());
                    bundle.putString(IntentExtra.INTENT_ROUTE_TYPE, getRouteType());
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getFragmentManager(), InfoPlanConfirmDialog.class.getName());
                }
            }
        });

        // 点击移动时隐藏，否则显示
        final View bottomBtnContent = this.findViewById(R.id.bottom);
        NestedScrollView nestedScrollView = (NestedScrollView) this.findViewById(R.id.scroll_view);
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        bottomBtnContent.setVisibility(View.INVISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        bottomBtnContent.setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }
        });
    }

    public void updateType(String routeType) {
        // 更新routeType
        setRouteType(routeType);
        // 更新图标
        updateMenu(true);

        // onUpdate headerFragment
        if (headerFragment.isAdded()) {
            headerFragment.updateType(routeType);
        }
        // onUpdate scenicFragment
        if (scenicFragment.isAdded()) {
        }
        // onUpdate prepareFragment
        if (prepareFragment.isAdded()) {
            prepareFragment.updateType(routeType);
        }
    }

    public void updateMenu(boolean isShowMenu) {
        mMenuType.setVisibility(isShowMenu ? View.VISIBLE : View.GONE);
        mMenuType.setImageDrawable(TravelType.getActionBarImageSrc(getRouteType()));

        fab.setImageDrawable(TravelType.getActionBarImageSrc(getRouteType()));
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }
}
