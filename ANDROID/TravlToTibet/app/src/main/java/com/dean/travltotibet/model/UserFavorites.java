package com.dean.travltotibet.model;

import android.content.Context;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 3/16/16.
 */
public class UserFavorites extends BmobObject{

    public  final static String ARTICLE = "article";
    public  final static String TEAM_REQUEST = "teamRequest";

    private String userId;
    private String userName;
    private String type_object_id;
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType_object_id() {
        return type_object_id;
    }

    public void setType_object_id(String type_object_id) {
        this.type_object_id = type_object_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addFavorite(final Context context, String favoriteType, String favoriteId) {
        UserFavorites userFavorites = new UserFavorites();
        userFavorites.setType_object_id(favoriteId);
        userFavorites.setType(favoriteType);
        if (TTTApplication.hasLoggedIn() && TTTApplication.getUserInfo() != null) {
            userFavorites.setUserId(TTTApplication.getUserInfo().getUserId());
            userFavorites.setUserName(TTTApplication.getUserInfo().getUserName());
        }
        userFavorites.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, context.getString(R.string.favorite_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, context.getString(R.string.action_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
