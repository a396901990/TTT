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
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.activity.UserNotificationActivity;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.model.UserMessage;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 2/26/16.
 */
public class NotificationHelper {
    public static final String NOTIFICATION_TAG = "MessageHelper";
    public static final String ARTICLE_TYPE = "article";
    public static final String MESSAGE_TYPE = "message";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void notify( final Context context, String title, String message ,Intent intent)
    {
        final Bitmap picture = BitmapFactory.decodeResource(TTTApplication.getMyResources(), R.drawable.app_notification_icon);

        final Notification.Builder builder = new Notification.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(picture)
                .setSmallIcon(R.drawable.app_notification_icon)
                .setTicker(title)// 设置在status bar上显示的提示文字
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
                .setAutoCancel(true);

        notify(context, builder.build());
    }

    public static void notifyArticle( final Context context, String articleId ) {

        BmobQuery<Article> query = new BmobQuery<>();
        query.addWhereContains("objectId", articleId);
        query.findObjects(context, new FindListener<Article>() {
            @Override
            public void onSuccess(List<Article> list) {
                if (list == null || list.size() == 0) {
                    return;
                }
                Article article = list.get(0);
                if (article == null) {
                    return;
                }
                Intent contentIntent = new Intent(TTTApplication.getInstance(), ArticleActivity.class);
                contentIntent.putExtra(IntentExtra.INTENT_ARTICLE, article);
                contentIntent.putExtra(IntentExtra.INTENT_LAUNCH_FROM, ArticleActivity.FROM_NOTIFICATION);

                NotificationHelper.notify(context, article.getTitle(), article.getSubTitle(), contentIntent);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    public static void notifyMessage( final Context context, String messageId ) {

        BmobQuery<UserMessage> query = new BmobQuery<>();
        query.addWhereContains("objectId", messageId);
        query.include("sendUser[userName]");
        query.findObjects(context, new FindListener<UserMessage>() {
            @Override
            public void onSuccess(List<UserMessage> list) {
                if (list == null || list.size() == 0) {
                    return;
                }
                UserMessage userMessage = list.get(0);
                if (userMessage == null) {
                    return;
                }
                Intent contentIntent = new Intent(TTTApplication.getInstance(), UserNotificationActivity.class);
                contentIntent.putExtra(IntentExtra.INTENT_LAUNCH_FROM, BaseActivity.FROM_NOTIFICATION);

                String title;
                if (userMessage.getSendUser() != null) {
                    title = userMessage.getSendUser().getUserName() + "  " + userMessage.getMessageTitle();
                } else {
                    title = userMessage.getMessageTitle();
                }
                NotificationHelper.notify(context, title, "", contentIntent);
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

        private String type;

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
