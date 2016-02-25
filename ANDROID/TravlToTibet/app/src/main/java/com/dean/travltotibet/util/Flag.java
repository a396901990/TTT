package com.dean.travltotibet.util;

public class Flag
{
    private int value;

    public boolean isSet( int mask )
    {
        return (value & mask) == mask;
    }

    public void set( int mask )
    {
        value = value | mask;
    }

    public void clear( int mask )
    {
        value = value & ~mask;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue( int value )
    {
        this.value = value;
    }
}
