package org.saarang.instieventsapp.Objects;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Moochi on 23-08-2015.
 */


public class UserProfile {

    private static String LOG_TAG = "INSTIProfile";

    public static String spUser = "spUser";
    public static String spRollNumber="spRollNumber";
    public static String spId = "spId";
    public static String spToken = "spToken";

    public static void saveUser(Context context, JSONObject json){
        SharedPreferences preferences = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            String token = json.getString("token");
            editor.putString(spToken, token);

            JSONObject user = json.getJSONObject("user");
            String id = user.getString("_id");
            editor.putString(spId, id);

            String rollNumber = user.getString("rollNumber");
            editor.putString(spRollNumber, rollNumber);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        } finally {
            editor.commit();

        }

    }

    public static String getUserToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spToken, "");
    }
    public static String getUserId(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spId, "");
    }

    public static String getUserRollNumber(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spRollNumber, "");
    }

    public static  String spUserState = "spUserState";
    public static String spLastActivity = "spLastActivity";

    public static void setUserState( Context context, int state){
        SharedPreferences preferences = context.getSharedPreferences(spUserState, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(spLastActivity, state);
        editor.commit();
    }

    public static int getUserState(Context context){
        SharedPreferences preferences = context.getSharedPreferences(spUserState, Context.MODE_PRIVATE);
        return preferences.getInt(spLastActivity, 1);
    }
}


