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
import com.dean.travltotibet.model.UserFavorites;
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
 * last update 5.15
 * last update 5.12
 * last update 5.9
 */
public class MoveOldLogicUtil {

    /**
     * 配合老逻辑手工添加
     */
    public static void moveCommentForArticle(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.ARTICLE_COMMENT);
        query.order("-createdAt"); // 最新的放前面
        query.setLimit(20);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (final Comment comment : list) {
                    final String id = comment.getType_object_id();

                    final BmobRelation relation = new BmobRelation();
                    relation.add(comment);

                    Article article = new Article();
                    article.setReplyComments(relation);
                    article.update(context, id, new UpdateListener() {
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

    public static void moveCommentForHotel(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.HOTEL_COMMENT);
        query.order("-createdAt"); // 最新的放前面
        query.setLimit(10);
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

    public static void moveCommentForScenic(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.SCENIC_COMMENT);
        query.order("-createdAt"); // 最新的放前面
        query.setLimit(20);
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

    public static void moveCommentForRoadInfo(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.ROAD_INFO_COMMENT);
        query.order("-createdAt"); // 最新的放前面
        query.setLimit(20);
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

    public static void moveCommentForTeamRequest(final Context context) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("type", Comment.TEAM_REQUEST_COMMENT);
        query.order("-createdAt"); // 最新的放前面
        query.setLimit(20);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (final Comment comment : list) {
                    final String id = comment.getType_object_id();

                    final BmobRelation relation = new BmobRelation();
                    relation.add(comment);

                    TeamRequest teamRequest = new TeamRequest();
                    teamRequest.setReplyComments(relation);
                    teamRequest.update(context, id, new UpdateListener() {
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
     * 用户发布的TeamRequest移动到user中
     */
    public static void moveTeamRequestToUser(final Context context) {
        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.order("-createdAt"); // 最新的放前面
        query.setLimit(5);
        query.include("user");
//        query.setSkip(100);
        query.findObjects(context, new FindListener<TeamRequest>() {
            @Override
            public void onSuccess(List<TeamRequest> list) {
                for (final TeamRequest t : list) {
                    if (t.getUserId() != null) {
                        BmobUser.loginByAccount(TTTApplication.getContext(), t.getUserId(), LoginUtil.DEFAULT_PASSWORD, new LogInListener<UserInfo>() {

                            @Override
                            public void done(final UserInfo user, BmobException e) {
                                if (user != null) {

                                    final BmobRelation relation = new BmobRelation();
                                    TeamRequest teamRequest = new TeamRequest();
                                    relation.add(teamRequest);
                                    user.setTeamRequest(relation);
                                    user.update(context, t.getObjectId(), new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.e("userId", user.getUserName());
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {

                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 手工将旧逻辑数据添加到user中
     */
    public static void moveToFavorites(final Context context) {
        BmobQuery<UserFavorites> query = new BmobQuery<UserFavorites>();
        query.order("-createdAt"); // 最新的放前面
        query.addWhereEqualTo("type", UserFavorites.TEAM_REQUEST);
        query.setLimit(5);
        query.findObjects(context, new FindListener<UserFavorites>() {
            @Override
            public void onSuccess(List<UserFavorites> list) {
                for (UserFavorites f : list) {
                    String userId = f.getUserId();
                    final String id = f.getTypeObjectId();
                    BmobUser.loginByAccount(TTTApplication.getContext(), userId, LoginUtil.DEFAULT_PASSWORD, new LogInListener<UserInfo>() {

                        @Override
                        public void done(final UserInfo user, BmobException e) {
                            if (user != null) {

                                TeamRequest teamRequest = new TeamRequest();
                                teamRequest.setObjectId(id);
                                BmobRelation relation = new BmobRelation();
                                relation.add(teamRequest);
                                user.setTeamFavorite(relation);
                                user.update(context, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        Log.e("userId", user.getUserName());
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {

                                    }
                                });
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

}
