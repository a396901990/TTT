package com.dean.travltotibet.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dean.travltotibet.util.NotificationHelper;

import cn.bmob.push.PushConstants;

/**
 * Created by DeanGuo on 2/25/16.
 */
public class PushMessageReceiver extends BroadcastReceiver {

    private static final String NOTIFICATION_TAG = "MessageHelper";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            String message = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            NotificationHelper.notifyArticle(context, message);
//            Toast.makeText(context, "客户端收到推送内容：" + message, Toast.LENGTH_SHORT).show();
        }
    }
}