package com.dean.travltotibet.util;

import android.content.Context;
import android.util.Log;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.HotelInfo;
import com.dean.travltotibet.model.RoadInfo;
import com.dean.travltotibet.model.ScenicInfo;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;

import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 5/11/16.
 */
public class MoveOldLogicUtil {

    /**
     * 配合老逻辑手工添加
     */
    public static void moveToArticle(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.ARTICLE_COMMENT);
//        query.setSkip(100);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (final Comment comment : list) {
                    final String id = comment.getType_object_id();

                    final BmobRelation relation = new BmobRelation();
                    relation.add(comment);

                    Article article = new Article();
                    article.setObjectId(id);
                    article.setReplyComments(relation);
                    article.update(context, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Log.e("articleId", id);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public static void moveToHotel(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.HOTEL_COMMENT);
//        query.setSkip(100);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (final Comment comment : list) {
                    final String id = comment.getType_object_id();

                    final BmobRelation relation = new BmobRelation();
                    relation.add(comment);

                    HotelInfo hotelInfo = new HotelInfo();
                    hotelInfo.setObjectId(id);
                    hotelInfo.setReplyComments(relation);
                    hotelInfo.update(context, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Log.e("hotelId", id);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public static void moveToScenic(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.SCENIC_COMMENT);
//        query.setSkip(100);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (final Comment comment : list) {
                    final String id = comment.getType_object_id();

                    final BmobRelation relation = new BmobRelation();
                    relation.add(comment);

                    ScenicInfo scenicInfo = new ScenicInfo();
                    scenicInfo.setObjectId(id);
                    scenicInfo.setReplyComments(relation);
                    scenicInfo.update(context, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Log.e("scenicId", id);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public static void moveToRoadInfo(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.ROAD_INFO_COMMENT);
//        query.setSkip(100);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (final Comment comment : list) {
                    final String id = comment.getType_object_id();

                    final BmobRelation relation = new BmobRelation();
                    relation.add(comment);

                    RoadInfo roadInfo = new RoadInfo();
                    roadInfo.setObjectId(id);
                    roadInfo.setReplyComments(relation);
                    roadInfo.update(context, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Log.e("roadInfoId", id);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public static void moveToTeamRequest(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.TEAM_REQUEST_COMMENT);
        query.setSkip(400);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (final Comment comment : list) {
                    final String id = comment.getType_object_id();

                    final BmobRelation relation = new BmobRelation();
                    relation.add(comment);

                    TeamRequest teamRequest = new TeamRequest();
                    teamRequest.setObjectId(id);
                    teamRequest.setReplyComments(relation);
                    teamRequest.update(context, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Log.e("teamRequestId", id);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * bmob更新数据bug，有时会重置number变量
     * @param context
     */
    public static void changeTeamRequestComment(final Context context) {
        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.setSkip(100);
        query.findObjects(context, new FindListener<TeamRequest>() {
            @Override
            public void onSuccess(List<TeamRequest> list) {
                for (final TeamRequest t : list) {
                    if (t.getWatch() == 0) {
                        Random random = new Random();
                        int watch = random.nextInt(80) + 80;
                        t.setWatch(watch);
                        t.update(context);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
