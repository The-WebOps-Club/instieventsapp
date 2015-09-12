package org.saarang.instieventsapp.Objects;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Moochi on 23-08-2015.
 */


public class UserProfile {

    private static String LOG_TAG = "UserProfile";

    public static String spName = "spName";
    public static String spRollNumber = "spRollNumber";
    public static String spId = "spId";
    public static String spHostel = "spHostel";
    public static String spToken = "spToken";

    // Variables for storing user data obtained from Insti Server
    String id, username, fullname, hostel;
    public UserProfile(){}

    public UserProfile(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getJSONObject("data").getJSONArray("response").optJSONObject(0).getString("id");
            this.username = jsonObject.getJSONObject("data").getJSONArray("response").optJSONObject(0).getString("username");
            this.fullname = jsonObject.getJSONObject("data").getJSONArray("response").optJSONObject(0).getString("fullname");
            this.hostel = jsonObject.getJSONObject("data").getJSONArray("response").optJSONObject(0).getString("hostel");

        } catch (JSONException e) {

        }
    }

    public String getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getHostel() {
        return hostel;
    }

    public String getUsername() {
        return username;
    }


    // Saving user - Saarang server
    public void saveUser(Context context, JSONObject json) {
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            String token = json.getString("token");
            editor.putString(spToken, token);

            JSONObject user = json.getJSONObject("user");
            String id = user.getString("_id");
            editor.putString(spId, id);

            String rollNumber = user.getString("rollNumber");
            editor.putString(spRollNumber, rollNumber);


            // Tunga = Sharav Correction
            if (hostel == "Tunga"){
                hostel = "Sharavati";
            }
            editor.putString(spHostel, hostel);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        } finally {
            editor.commit();

        }

    }

    public static String getUserToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return pref.getString(spToken, "");
    }

    public static String getUserHostel(Context context) {
        SharedPreferences pref = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return pref.getString(spHostel, "");
    }

    public static String getUserId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return pref.getString(spId, "");
    }

    public static String getUserRollNumber(Context context) {
        SharedPreferences pref = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return pref.getString(spRollNumber, "");
    }

    public static String spUserState = "spUserState";
    public static String spLastActivity = "spLastActivity";

    public static void setUserState(Context context, int state) {
        SharedPreferences preferences = context.getSharedPreferences(spUserState, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(spLastActivity, state);
        editor.commit();
    }

    public static int getUserState(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(spUserState, Context.MODE_PRIVATE);
        return preferences.getInt(spLastActivity, 1);
    }
}


