package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseRouteFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    /**
     * 跟新fragment路线
     */
    public abstract void updateRoute();

}
