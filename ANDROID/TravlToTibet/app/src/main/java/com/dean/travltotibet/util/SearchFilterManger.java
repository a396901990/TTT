package com.dean.travltotibet.util;

import android.content.Context;

import com.dean.travltotibet.ui.tagview.Tag;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 4/29/16.
 */
public final class SearchFilterManger {

    public static final String SEARCH_MONTH = "month";
    public static final String SEARCH_TYPE = "type";
    public static final String SEARCH_ROUTE = "route";
    public static final String SEARCH_KEY_WORD = "key_work";
    public static final String SEARCH_QA = "qa";

    private static SearchFilterManger sInstance;

    private final Context mContext;

    private static ArrayList<Tag> teamFilterTags = new ArrayList<>();

    private static ArrayList<Tag> qaFilterTags = new ArrayList<>();

    private SearchFilterManger( final Context context )
    {
        this.mContext = context;
    }

    public static void init( final Context context )
    {
        sInstance = new SearchFilterManger(context);
    }

    public static void clear()
    {
        sInstance.teamFilterTags = null;
        sInstance.qaFilterTags = null;
    }

    public static ArrayList<Tag> getTeamFilterTags() {
        return teamFilterTags;
    }

    public static void setTeamFilterTags(ArrayList<Tag> teamFilterTags) {
        SearchFilterManger.teamFilterTags = teamFilterTags;
    }

    public static ArrayList<Tag> getQAFilterTags() {
        return qaFilterTags;
    }

    public static void setQAFilterTags(ArrayList<Tag> qaFilterTags) {
        SearchFilterManger.qaFilterTags = qaFilterTags;
    }

    public static boolean hasSameTypeTags(ArrayList<Tag> tags, Tag tag) {
        for (Tag t : tags) {
            if (t.getType().equals(tag.getType())) {
                t.text = tag.text;
                return true;
            }
        }
        return false;
    }

    public static void addTagForTeamFilter(Tag tag) {

        // 有同种类型，替换名字   没有则添加
        if (hasSameTypeTags(teamFilterTags, tag)) {
        } else {
            teamFilterTags.add(tag);
        }
    }

    public static void addTagForQAFilter(Tag tag) {

        // 有同种类型，替换名字   没有则添加
        if (hasSameTypeTags(qaFilterTags, tag)) {
        } else {
            qaFilterTags.add(tag);
        }
    }

    public static String getTeamTagTextWithType(String type) {
        for (Tag t : teamFilterTags) {
            if (t.getType().equals(type)) {
                return t.text;
            }
        }

        return "";
    }

    public static String getQATextWithType(String type) {
        for (Tag t : qaFilterTags) {
            if (t.getType().equals(type)) {
                return t.text;
            }
        }

        return "";
    }

    public static void removeTagForTeamFilter(Tag tag) {
        teamFilterTags.remove(tag);
    }

    public static void removeTagForQAFilter(Tag tag) {
        qaFilterTags.remove(tag);
    }
}
