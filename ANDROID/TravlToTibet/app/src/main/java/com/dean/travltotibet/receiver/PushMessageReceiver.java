package com.dean.travltotibet.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.dean.travltotibet.util.NotificationHelper;
import com.google.gson.Gson;

import cn.bmob.push.PushConstants;

/**
 * Created by DeanGuo on 2/25/16.
 */
public class PushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            String message = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);

            Gson gson = new Gson();
            NotificationHelper.Message msg = gson.fromJson(message, NotificationHelper.Message.class);
            String alert = msg.getAlert();
            String type = msg.getType();

            // 默认为article
            if (TextUtils.isEmpty(type)) {
                NotificationHelper.notifyArticle(context, alert);
            } else {
                // message
                if (NotificationHelper.MESSAGE_TYPE.equals(type)) {
                    NotificationHelper.notifyMessage(context, alert);
                }
                // 默认为article
                else {
                    NotificationHelper.notifyArticle(context, alert);
                }
            }

//            Toast.makeText(context, "客户端收到推送内容：" + message, Toast.LENGTH_SHORT).show();
        }
    }
}