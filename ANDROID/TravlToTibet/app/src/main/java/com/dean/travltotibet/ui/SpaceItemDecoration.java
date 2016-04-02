package com.dean.travltotibet.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DeanGuo on 4/1/16.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
    }
}
