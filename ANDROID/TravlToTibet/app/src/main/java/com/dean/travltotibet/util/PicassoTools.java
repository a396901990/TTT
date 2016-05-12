package com.dean.travltotibet.util;

import com.dean.travltotibet.TTTApplication;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

public class PicassoTools
{
    private static Picasso picasso = null;
    private static LruCache lruCache = null;

    private static LruCache getCache()
    {
        if (lruCache == null)
            lruCache = new LruCache(TTTApplication.getContext());
        return lruCache;
    }

    public static Picasso getPicasso()
    {
        if (picasso == null)
            picasso = new Picasso.Builder(TTTApplication.getContext()).memoryCache(getCache()).build();
        return picasso;
    }

    public static void clearCache()
    {
        getCache().clear();
    }
}