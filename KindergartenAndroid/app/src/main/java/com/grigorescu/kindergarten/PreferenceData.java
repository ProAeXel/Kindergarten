package com.grigorescu.kindergarten;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceData
{
    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_RANK = "rank";
    static final String PREF_USER_FIRST = "first";
    static final String PREF_USER_LAST = "last";
    static final String PREF_USER_EMAIL = "email";
    static final String PREF_USER_SCHOLARSHIP = "scholarship";
    static final String PREF_USER_ADDRESS = "address";
    static final String PREF_USER_PHONE = "phone";
    static final String PREF_USER_IMAGE = "image";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setUserRank(Context ctx, String rank)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_RANK, rank);
        editor.commit();
    }

    public static String getUserRank(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_RANK, "");
    }

    public static void setUserFirstName(Context ctx, String firstName)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_FIRST, firstName);
        editor.commit();
    }

    public static void setUserLastName(Context ctx, String lastName)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_LAST, lastName);
        editor.commit();
    }

    public static String getUserFirstName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_FIRST, "");
    }

    public static String getUserLastName(Context ctx)
    {
        return  getSharedPreferences(ctx).getString(PREF_USER_LAST, "");
    }

    public static void setUserEmail(Context ctx, String email)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, email);
        editor.commit();
    }

    public static String getUserEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static void setUserScholarship(Context ctx, String scholarship)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_SCHOLARSHIP, scholarship);
        editor.commit();
    }

    public static String getUserScholarship(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_SCHOLARSHIP, "");
    }

    public static void setUserAddress(Context ctx, String address)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ADDRESS, address);
        editor.commit();
    }

    public static void setUserPhone(Context ctx, String phone)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PHONE, phone);
        editor.commit();
    }

    public static String getUserAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_ADDRESS, "");
    }

    public static String getUserPhone(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_PHONE, "");
    }

    public static void setUserProfileImage(Context ctx, String image)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_IMAGE, image);
        editor.commit();
    }

    public static String getUserProfileImage(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_IMAGE, "");
    }
}