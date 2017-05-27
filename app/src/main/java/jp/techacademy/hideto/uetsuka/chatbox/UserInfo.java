package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by Airhead-Kangaroo on 2017/05/14.
 */

public class UserInfo {

    private static String userId = "";
    private static String userName = "";

    public static final String SP_USERID_KEY = "uid";
    public static final String SP_USERNAME_KEY = "userName";

    static String getUserId(Context context){
        if(userId == "" || userId.length() == 0 || userId == null){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            UserInfo.userId = sp.getString(SP_USERID_KEY,null);
        }
        return UserInfo.userId;
    }

    static void setUserId(Context context,@Nullable String userId){
        UserInfo.userId = userId;
        setSharedPreferences(context,userId, SP_USERID_KEY);
    }

    static void isLogin(Activity activity){
        String userId = getUserId(activity);
        if(userId == null || userId.equals("") || userId.length() == 0){
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        }
    }

    static String getUserName(Context context){
        if(userName == "" || userName.length() == 0 || userName == null){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            UserInfo.userName = sp.getString(SP_USERNAME_KEY,null);
        }
        return UserInfo.userName;
    }

    static void setUserName(Context context, String userName){
        UserInfo.userName = userName;
        setSharedPreferences(context,userName,SP_USERNAME_KEY);
    }

    private static void setSharedPreferences(Context context, String value, String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }







}
