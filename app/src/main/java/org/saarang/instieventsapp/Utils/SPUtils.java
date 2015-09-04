package org.saarang.instieventsapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.saarang.saarangsdk.Helpers.TimeHelper;

/**
 * Created by kevin selva prasanna on 04-Sep-15.
 */
public class spUtils {
    private static String sp = "sharedPreferences";
    private static String spLastUpdateDate = "latestPost";

    public static void setLastUpdateDate(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Log.d("TIME:", TimeHelper.getTimeStamp(System.currentTimeMillis()));
        editor.putString(spLastUpdateDate, TimeHelper.getTimeStamp(System.currentTimeMillis()));
        editor.commit();
    }

    public static String getLastUpdateDate(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(spLastUpdateDate, "");
    }
}
