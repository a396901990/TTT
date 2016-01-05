package com.dean.travltotibet.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.animation.OvershootInterpolator;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.fab.FloatingActionButton;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 12/6/15.
 */
public class MenuUtil {

    public static FloatingActionButton initFAB(Context context, String name, int icon) {
        final FloatingActionButton fab = new FloatingActionButton(context);
        fab.setButtonSize(com.dean.travltotibet.ui.fab.FloatingActionButton.SIZE_MINI);
        fab.setLabelText(name);
        fab.setImageResource(icon);
        fab.setColorNormal(TTTApplication.getMyColor(R.color.colorAccent));
        fab.setColorPressed(TTTApplication.getMyColor(R.color.colorAccentLight));

        return fab;
    }

    // 全屏按钮
    public static FloatingActionButton getFAB(Context context, String title, GoogleMaterial.Icon icon) {
        final FloatingActionButton fab = new FloatingActionButton(context);
        fab.setButtonSize(FloatingActionButton.SIZE_MINI);
        fab.setLabelText(title);
        fab.setImageDrawable(TTTApplication.getGoogleIconDrawable(icon, Color.WHITE));
        fab.setColorNormal(TTTApplication.getMyColor(R.color.colorAccent));
        fab.setColorPressed(TTTApplication.getMyColor(R.color.dark_green));

        return fab;
    }

    // 全屏按钮
    public static FloatingActionButton getFAB(Context context, String title, Drawable icon) {
        final FloatingActionButton fab = new FloatingActionButton(context);
        fab.setButtonSize(FloatingActionButton.SIZE_MINI);
        fab.setLabelText(title);
        fab.setImageDrawable(icon);
        fab.setColorNormal(TTTApplication.getMyColor(R.color.colorAccent));
        fab.setColorPressed(TTTApplication.getMyColor(R.color.dark_green));

        return fab;
    }

    public static void createCustomAnimation(final FloatingActionMenu mMenu) {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(mMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        final ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(mMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(mMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(mMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mMenu.isOpened()) {
                    mMenu.getMenuIconView().setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_close, Color.WHITE));
                } else {
                    mMenu.getMenuIconView().setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_menu, Color.WHITE));
                }
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        mMenu.setIconToggleAnimatorSet(set);
    }
}
