package com.dean.travltotibet.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.InfoConfirmDialog;
import com.dean.travltotibet.fragment.InfoHeaderFragment;
import com.dean.travltotibet.fragment.InfoPrepareFragment;
import com.dean.travltotibet.fragment.TravelTypeDialog;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.InfoScrollView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ScreenUtil;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class InfoActivity extends Activity {

    private String route;
    private String routeName;
    private String routeType;

    private InfoHeaderFragment headerFragment;
    private InfoPrepareFragment prepareFragment;

    private ImageView returnBtn;
    private ImageView typeBtn;

    private View headerBar;
    private View headerBackground;
    private InfoScrollView scrollView;

    public interface ScrollViewListener {

        void onScrollChanged(InfoScrollView scrollView, int x, int y, int oldx, int oldy);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_view);

        if (getIntent() != null) {
            route = getIntent().getStringExtra(Constants.INTENT_ROUTE);
            routeName = getIntent().getStringExtra(Constants.INTENT_ROUTE_NAME);
            routeType = getIntent().getStringExtra(Constants.INTENT_ROUTE_TYPE);
        }
        headerFragment = (InfoHeaderFragment) getFragmentManager().findFragmentById(R.id.info_header_fragment);
        prepareFragment = (InfoPrepareFragment) getFragmentManager().findFragmentById(R.id.info_prepare_fragment);

        initHeaderBar();
        initScrollView();
        initGoButton();
    }

    private void initScrollView() {

        // header高度
        final int headerHeight = ScreenUtil.dip2px(this, 200);
        // 透明度比率
        final float alphaRate = (float)1 / (float)500;

        scrollView = (InfoScrollView) findViewById(R.id.scroll_view);
        scrollView.setScrollListener(new ScrollViewListener() {
            @Override
            public void onScrollChanged(InfoScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y <= headerHeight) {
                    headerBackground.setAlpha(y * alphaRate);
                }
            }
        });
    }

    /**
     * 初始化header左右两侧按钮
     */
    private void initHeaderBar() {
        headerBar = this.findViewById(R.id.info_header_actionbar);
        headerBackground = findViewById(R.id.header_actionbar_background);

        headerBackground.setAlpha(0);
        returnBtn = (ImageView)this.findViewById(R.id.header_return_btn);
        typeBtn = (ImageView) this.findViewById(R.id.header_travel_type);

        // 设置按钮文字和按钮图片
        typeBtn.setImageDrawable(TravelType.getWhiteTypeImageSrc(getRouteType()));

        // 设置监听
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TravelTypeDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE, getRoute());
                bundle.putString(Constants.INTENT_ROUTE_NAME, getRouteName());
                bundle.putString(Constants.INTENT_FROM_WHERE, TravelTypeDialog.FROM_SECOND);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), TravelTypeDialog.class.getName());
            }
        });
    }

    /**
     * 初始化出发按钮
     */
    private void initGoButton() {
        Button go = (Button) this.findViewById(R.id.goButton);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new InfoConfirmDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE, getRoute());
                bundle.putString(Constants.INTENT_ROUTE_NAME, getRouteName());
                bundle.putString(Constants.INTENT_ROUTE_TYPE, getRouteType());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), InfoConfirmDialog.class.getName());
            }
        });
    }

    public void updateType(String routeType) {
        // 更新routeType
        setRouteType(routeType);
        // 更新图标
        typeBtn.setImageDrawable(TravelType.getWhiteTypeImageSrc(getRouteType()));

        // update headerFragment
        if (headerFragment.isAdded()) {
            headerFragment.updateType(routeType);
        }

        // update prepareFragment
        if (prepareFragment.isAdded()) {
            prepareFragment.updateType(routeType);
        }
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


}
