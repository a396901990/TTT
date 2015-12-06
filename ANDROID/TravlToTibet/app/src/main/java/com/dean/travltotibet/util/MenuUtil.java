package com.dean.travltotibet.util;

import android.content.Context;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.fab.FloatingActionButton;

/**
 * Created by DeanGuo on 12/6/15.
 */
public class MenuUtil {

    public static FloatingActionButton initFAB(Context context, String name, int icon) {
        final FloatingActionButton normalMap = new FloatingActionButton(context);
        normalMap.setButtonSize(com.dean.travltotibet.ui.fab.FloatingActionButton.SIZE_MINI);
        normalMap.setLabelText(name);
        normalMap.setImageResource(icon);
        normalMap.setColorNormal(TTTApplication.getMyColor(R.color.colorAccent));
        normalMap.setColorPressed(TTTApplication.getMyColor(R.color.colorAccentLight));

        return normalMap;
    }
}
