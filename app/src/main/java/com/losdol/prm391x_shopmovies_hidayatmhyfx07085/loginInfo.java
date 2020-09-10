package com.losdol.prm391x_shopmovies_hidayatmhyfx07085;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class loginInfo {
    static final String LOGIN_KEY = "Username_logged_in";
    static final String LOGIN_STATUS_KEY = "Status_logged_in";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setRegisteredUser(Context context, String username, String email, String profilePic){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("USERNAME", username);
        editor.putString("EMAIL", email);
        editor.putString("URI_PROFPIC", profilePic);
        editor.apply();
    }

    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString("USERNAME","");
    }
    public static String getRegisteredEmail(Context context){
        return getSharedPreference(context).getString("EMAIL","");
    }
    public static String getRegisteredProfile(Context context){
        return getSharedPreference(context).getString("URI_PROFPIC","");
    }

    public static void setLoggedInUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(LOGIN_KEY, username);
        editor.apply();
    }

    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(LOGIN_KEY,"");
    }

    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(LOGIN_STATUS_KEY,status);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(LOGIN_STATUS_KEY,false);
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(LOGIN_KEY);
        editor.remove(LOGIN_STATUS_KEY);
        editor.apply();
    }
}
