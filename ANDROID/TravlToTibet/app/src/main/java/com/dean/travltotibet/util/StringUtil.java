/**
 * Copyright 2010, FMR LLC.
 * All Rights Reserved.
 * Fidelity Confidential Information
 */
package com.dean.travltotibet.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * String utilities
 */
public class StringUtil
{

    /** decimal formatter **/
    private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat(
        Constants.DECIMAL_FORMAT);

    /** decimal formatter **/
    private static final DecimalFormat STRING_INTEGER_FORMATTER = new DecimalFormat(
            Constants.STRING_INTEGER_FORMATTER);

    /** integer formatter **/
    private static final NumberFormat INTEGER_FORMATTER = NumberFormat
        .getIntegerInstance(Locale.US);

    /**
     * The index value when an element is not found in a list or array: {@code -1}.
     * This value is returned by methods in this class and can also be used in comparisons with values returned by
     * various method from {@link List}.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * format String
     *
     * @param argString
     *            String to be formatted
     * @return formtted String
     */
    public static String format( final String argString )
    {
        return format(argString, Constants.DECIMAL_FORMAT);
    }

    /**
     * format double
     *
     * @param argDouble
     *            double to be formatted
     * @return formtted String
     */
    public static String format( final double argDouble )
    {
        return DECIMAL_FORMATTER.format(argDouble);
    }

    public static String formatDoubleToInteger( final double argDouble )
    {
        return STRING_INTEGER_FORMATTER.format(argDouble);
    }

    public static String formatDoubleToFourInteger( final double argDouble )
    {
        return new DecimalFormat(Constants.FOUR_INTEGER_FORMATTER).format(argDouble);
    }

    /**
     * @param argString
     *            String to be formatted
     * @param argFormat
     *            Format pattern
     * @return formatted string
     */
    public static String format( final String argString, final String argFormat )
    {
        DecimalFormat formatter = new DecimalFormat(argFormat);
        try
        {
            Number n = formatter.parse(argString);
            return formatter.format(n);
        }
        catch (final ParseException ex)
        {
            return argString;
        }
    }

    /**
     * Parses a Double from the specified string
     *
     * @param argString
     *            String to be formatted
     * @return double value of specified string
     */
    public static Double parseDouble( final String argString )
    {
        if (argString == null)
        {
            return null;
        }

        try
        {
            return Double.valueOf(argString);
        }
        catch (final NumberFormatException ex)
        {
            try
            {
                return Double.valueOf(DECIMAL_FORMATTER.parse(argString).doubleValue());
            }
            catch (final NumberFormatException e1)
            {
                return null;
            }
            catch (ParseException e)
            {
                return null;
            }
        }
    }

    /**
     * Parses a double from the specified string
     *
     * @param argString
     *            specified string
     * @param defaultValue
     *            default value if parse fails
     * @return double value of specified string
     */
    public static double parseDouble(
        final String argString,
        final double defaultValue )
    {
        Double d = parseDouble(argString);
        if (d == null)
        {
            return defaultValue;
        }
        else
        {
            return d.doubleValue();
        }
    }

    /**
     * Parses an Integer from the specified string
     *
     * @param argString
     *            String to be converted to integer
     * @return integer value of specified string
     */
    public static Integer parseInt( final String argString )
    {
        if (argString == null)
        {
            return null;
        }

        try
        {
            return Integer.valueOf(argString);
        }
        catch (final NumberFormatException ex)
        {
            try
            {
                return Integer.valueOf(INTEGER_FORMATTER.parse(argString).intValue());
            }
            catch (final NumberFormatException e)
            {
                return null;
            }
            catch (ParseException e)
            {
                return null;
            }
        }
    }

    /**
     * Parses an integer from the specified string
     *
     * @param argString
     *            String to be converted to integer
     * @param defaultValue
     *            default value if parse fails
     * @return integer value of specified string
     */
    public static int parseInt( final String argString, final int defaultValue )
    {
        Integer i = parseInt(argString);
        if (i == null)
        {
            return defaultValue;
        }
        else
        {
            return i.intValue();
        }
    }

    /**
     * Parses a Float from the specified string
     *
     * @param argString
     *            String to be converted to float
     * @return float value of specified string
     */
    public static Float parseFloat( final String argString )
    {
        if (argString == null)
        {
            return null;
        }
        try
        {
            return Float.valueOf(argString);
        }
        catch (final NumberFormatException ex)
        {
            try
            {
                return Float.valueOf(DECIMAL_FORMATTER.parse(argString).floatValue());
            }
            catch (final ParseException e1)
            {
                return null;
            }
        }
    }

    /**
     * Parses a float from the specified string
     *
     * @param argString
     *            String to be converted to float
     * @param defaultValue
     *            default value if parse fails
     * @return float value of specified string
     */
    public static float parseFloat(
        final String argString,
        final float defaultValue )
    {
        Float f = parseFloat(argString);
        if (f == null)
        {
            return defaultValue;
        }
        else
        {
            return f.floatValue();
        }
    }

    /**
     * add strings
     *
     * @param args
     *            strings to add
     * @return string
     */
    public static String add( String... args )
    {
        if (args == null)
        {
            return null;
        }
        else
        {
            StringBuilder builder = new StringBuilder();

            for (String s : args)
            {
                if (s != null)
                {
                    builder.append(s);
                }
            }

            return builder.toString();
        }
    }

    /**
     * get special characters from web service.
     *
     * @param source
     *            data from web service.
     * @return string data support special characters.
     */
    public static String fromHtml( String source )
    {
        String result = "";
        if (null == source || "".equals(source))
        {
            return result;
        }
        Spanned spanned = Html.fromHtml(source);
        if (null != spanned)
        {
            result = spanned.toString();
        }
        return result;
    }

    /**
     * Helper method to join objects in an array together separated by...a
     * separator
     *
     * @param argArray
     *            The array of objects to join
     * @param argSeparator
     *            The character that will separate the joined elements
     * @param argStartIndex
     *            Where to start joining
     * @param argEndIndex
     *            Where to end joining
     * @return The joined objects
     */
    public static String join(
        final Object[] argArray,
        String argSeparator,
        final int argStartIndex,
        final int argEndIndex )
    {
        if (argArray == null)
        {
            return null;
        }
        if (argSeparator == null)
        {
            argSeparator = "";
        }

        int noOfItems = argEndIndex - argStartIndex;
        if (noOfItems <= 0)
        {
            return "";
        }

        StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = argStartIndex; i < argEndIndex; i++)
        {
            if (i > argStartIndex)
            {
                buf.append(argSeparator);
            }
            if (argArray[i] != null)
            {
                buf.append(argArray[i]);
            }
        }
        return buf.toString();
    }

    /**
     * <p>Returns either the passed in CharSequence, or if the CharSequence is
     * whitespace, empty ("") or {@code null}, the value of {@code defaultStr}.</p>
     *
     * @param <T> the specific kind of CharSequence
     * @param str the CharSequence to check, may be null
     * @param defaultStr  the default CharSequence to return
     *  if the input is whitespace, empty ("") or {@code null}, may be null
     * @return the passed in CharSequence, or the default
     */
    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr)
    {
        return TextUtils.isEmpty(str) ? defaultStr : str;
    }

    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(int[] array, int valueToFind)
    {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf( int[] array, int valueToFind, int startIndex )
    {
        if (array == null)
        {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0)
        {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++)
        {
            if (valueToFind == array[i])
            {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the index of the given object in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @return the index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(Object[] array, Object objectToFind)
    {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * <p>Finds the index of the given object in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @param startIndex  the index to start searching at
     * @return the index of the object within the array starting at the index,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf( Object[] array, Object objectToFind, int startIndex )
    {
        if (array == null)
        {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0)
        {
            startIndex = 0;
        }
        if (objectToFind == null)
        {
            for (int i = startIndex; i < array.length; i++)
            {
                if (array[i] == null)
                {
                    return i;
                }
            }
        }
        else if (array.getClass().getComponentType().isInstance(objectToFind))
        {
            for (int i = startIndex; i < array.length; i++)
            {
                if (objectToFind.equals(array[i]))
                {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks whether the character is ASCII 7 bit alphabetic.</p>
     *
     * <pre>
     *   CharUtils.isAsciiAlpha('a')  = true
     *   CharUtils.isAsciiAlpha('A')  = true
     *   CharUtils.isAsciiAlpha('3')  = false
     *   CharUtils.isAsciiAlpha('-')  = false
     *   CharUtils.isAsciiAlpha('\n') = false
     *   CharUtils.isAsciiAlpha('&copy;') = false
     * </pre>
     *
     * @param ch  the character to check
     * @return true if between 65 and 90 or 97 and 122 inclusive
     */
    public static boolean isAsciiAlpha(char ch)
    {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    /**
     * <p>Checks whether the character is ASCII 7 bit numeric.</p>
     *
     * <pre>
     *   CharUtils.isAsciiAlphanumeric('a')  = true
     *   CharUtils.isAsciiAlphanumeric('A')  = true
     *   CharUtils.isAsciiAlphanumeric('3')  = true
     *   CharUtils.isAsciiAlphanumeric('-')  = false
     *   CharUtils.isAsciiAlphanumeric('\n') = false
     *   CharUtils.isAsciiAlphanumeric('&copy;') = false
     * </pre>
     *
     * @param ch  the character to check
     * @return true if between 48 and 57 or 65 and 90 or 97 and 122 inclusive
     */
    public static boolean isAsciiAlphanumeric(char ch)
    {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9');
    }

    public static String nullToEmpty(String string)
    {
        return (string == null) ? "" : string;
    }

    public static String[] clean( final String[] v )
    {
        List<String> list = new ArrayList<String>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

    /**
     * @param stringList
     *            source string list
     * @param sourceText
     *            original text contains in the list
     * @param textWithLink
     *            text with 'href' link address
     * @return the string list contains specific text with specified link
     *         address
     * */
    public static List<String> getLinkedTextStringList( final List<String> stringList, final String sourceText, final String textWithLink )
    {
        List<String> resultList = new ArrayList<String>();
        if (stringList != null && !stringList.isEmpty())
        {
            for (String eachMsg : stringList)
            {
                String translateStr = Html.fromHtml(eachMsg).toString();
                String finalMessageText = translateStr.replaceAll(sourceText, textWithLink);
                resultList.add(finalMessageText);
            }
        }
        return resultList;
    }
}
