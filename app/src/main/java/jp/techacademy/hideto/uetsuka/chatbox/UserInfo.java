package jp.techacademy.hideto.uetsuka.chatbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by Airhead-Kangaroo on 2017/05/14.
 */

public class UserInfo {

    private static String userId = "";

    public static final String SP_USERID_KEY = "uid";

    static String getUserId(Context context){
        if(userId != "" || userId.length() == 0 || userId == null){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            UserInfo.userId = sp.getString(SP_USERID_KEY,null);
        }
        return userId;
    }

    static void setUserId(Context context,@Nullable String userId){
        UserInfo.userId = userId;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_USERID_KEY, userId);
        editor.apply();
    }




}
