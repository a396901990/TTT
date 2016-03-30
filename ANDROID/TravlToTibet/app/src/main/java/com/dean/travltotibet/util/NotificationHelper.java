package com.dean.travltotibet.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ArticleActivity;
import com.dean.travltotibet.model.Article;
import com.google.gson.Gson;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 2/26/16.
 */
public class NotificationHelper {
    private static final String NOTIFICATION_TAG = "MessageHelper";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void notify( final Context context, String title, String message ,Intent intent)
    {
        final Bitmap picture = BitmapFactory.decodeResource(TTTApplication.getMyResources(), R.drawable.app_icon);

        final Notification.Builder builder = new Notification.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(picture)
                .setSmallIcon(R.drawable.app_icon)
                .setTicker(title)// 设置在status bar上显示的提示文字
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
                .setAutoCancel(true);

        notify(context, builder.build());
    }

    public static void notifyArticle( final Context context, String message ) {
        Gson gson = new Gson();
        Message msg = gson.fromJson(message, Message.class);
        String objId = msg.getAlert();

        BmobQuery<Article> query = new BmobQuery<>();
        query.addWhereContains("objectId", objId);
        query.findObjects(context, new FindListener<Article>() {
            @Override
            public void onSuccess(List<Article> list) {
                Article article = list.get(0);
                if (article == null) {
                    return;
                }
                Intent contentIntent = new Intent(TTTApplication.getInstance(), ArticleActivity.class);
                contentIntent.putExtra(IntentExtra.INTENT_ARTICLE, article);
                contentIntent.putExtra(IntentExtra.INTENT_ARTICLE_FROM, ArticleActivity.FROM_NOTIFICATION);

                NotificationHelper.notify(context, article.getTitle(), article.getSubTitle(), contentIntent);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify( final Context context, final Notification notification )
    {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR)
        {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        }
        else
        {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel( final Context context )
    {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR)
        {
            nm.cancel(NOTIFICATION_TAG, 0);
        }
        else
        {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }

    public class Message {

        private String alert;

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }
    }
}
