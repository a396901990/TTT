package com.dean.travltotibet.model;

import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 7/13/15.
 */
public class Place
{
    private String name;
    
    private String height;
    
    private String mileage;

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * @return the height
     */
    public String getHeight()
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight( String height )
    {
        this.height = height;
    }
    
    /**
     * @param height the height to set
     */
    public void setHeight( double height )
    {
        this.height = Constants.NAME_HEIGHT + (int)height + "M";
    }

    /**
     * @return the mileage
     */
    public String getMileage()
    {
        return mileage;
    }

    /**
     * @param mileage the mileage to set
     */
    public void setMileage( String mileage )
    {
        this.mileage = mileage;
    }
    
    public void setMileage( double mileage )
    {
        this.mileage = Constants.NAME_MILEAGE + "G" + (int) mileage;
    }
}
