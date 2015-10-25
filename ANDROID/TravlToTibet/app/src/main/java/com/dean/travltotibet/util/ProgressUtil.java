/**
 * Copyright 2014, FMR LLC.
 * All Rights Reserved.
 * Fidelity Confidential Information
 */

package com.dean.travltotibet.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;

public class ProgressUtil
{
    private static ProgressDialog sProgressDialog;

    public static void setLastShown( ProgressDialog dialog )
    {
        if (sProgressDialog != dialog)
        {
            dismissLast();
        }
        sProgressDialog = dialog;
    }

    public static void dismissLast()
    {
        if (sProgressDialog != null)
        {
            if (sProgressDialog.isShowing())
            {
                sProgressDialog.dismiss();
            }
            sProgressDialog = null;
        }
    }

    public static void dissmissLast( Context context )
    {
        if (sProgressDialog != null)
        {
            Context oldContext = sProgressDialog.getContext();
            if (oldContext instanceof ContextWrapper)
            {
                oldContext = ((ContextWrapper) oldContext).getBaseContext();
            }

            if (oldContext == context)
            {
                dismissLast();
            }
        }
    }
}
