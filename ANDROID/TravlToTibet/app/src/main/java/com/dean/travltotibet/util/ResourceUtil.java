package com.dean.travltotibet.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

import com.dean.travltotibet.R;

public class ResourceUtil
{
    private final Context mContext;

    private final Resources mResources;

    public int chart_mountain;

    public int chart_mountain_alpha;

    public int chart_mountain_shader;

    public int chart_line;

    public int chart_cross;

    public int chart_cross_dialog_alpha;

    public int chart_cross_text;

    public int chart_arrow;

    public int chart_label_text;

    public int chart_grid;

    public int chart_grid_alpha;

    public int chart_axis;

    public int chart_backgroud;

    public int indicator_backgroud;

    public int indicator_indicator;

    public int indicator_indicator_alpha;

    public int indicator_mountain;

    public int indicator_mountain_line;

    public int indicator_shadow;

    public int indicator_shadow_alpha;
    
    public int chart_text_paint;
    
    public int chart_text_rect_city_paint;
    
    public int chart_text_rect_town_paint;

    public int chart_text_rect_village_paint;
    
    public int chart_text_rect_mountain_paint;
    
    public int chart_text_rect_aplha;

    public ResourceUtil( Context context )
    {
        super();
        this.mContext = context;
        this.mResources = mContext.getResources();

        initColor();
    }

    private void initColor()
    {
        chart_mountain = mResources.getColor(R.color.light_sky_blue);
        chart_mountain_shader = mResources.getColor(R.color.light_sky_blue);
        chart_mountain_alpha = (int) (255 * 0.6);

        chart_line = mResources.getColor(R.color.light_sky_blue);

        chart_cross = mResources.getColor(R.color.orange_red);
        chart_cross_dialog_alpha = (int) (255 * 0.8);
        chart_cross_text = mResources.getColor(R.color.white);
        chart_arrow = mResources.getColor(R.color.orange_red);

        chart_label_text = mResources.getColor(R.color.blue);

        chart_grid = mResources.getColor(R.color.light_sky_blue);
        chart_grid_alpha = (int) (255 * 0.6);
        chart_axis = mResources.getColor(R.color.light_sky_blue);

        chart_backgroud = mResources.getColor(R.color.white_background);

        indicator_backgroud = mResources.getColor(R.color.light_sky_blue);
        indicator_mountain = mResources.getColor(R.color.white);
        indicator_mountain_line = mResources.getColor(R.color.white);

        indicator_indicator = mResources.getColor(R.color.white);
        indicator_indicator_alpha = (int) (255 * 0.5);

        indicator_shadow = mResources.getColor(R.color.white_gray);
        indicator_shadow_alpha = (int) (255 * 0.8);
        
        chart_text_paint = mResources.getColor(R.color.white);
        chart_text_rect_aplha = (int) (255 * 0.8);
        chart_text_rect_city_paint = mResources.getColor(R.color.brown);
        chart_text_rect_town_paint = mResources.getColor(R.color.teal);
        chart_text_rect_village_paint = mResources.getColor(R.color.lime);
        chart_text_rect_mountain_paint = mResources.getColor(R.color.blue_gray);
    }
    
    public String[] getStringArray( int id )
    {
        String[] array = null;
        try
        {
            array = mResources.getStringArray(id);
        }
        catch (NotFoundException e)
        {
        }
        return array;
    }

}
