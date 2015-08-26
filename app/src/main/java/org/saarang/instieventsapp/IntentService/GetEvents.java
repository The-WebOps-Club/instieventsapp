package org.saarang.instieventsapp.IntentService;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Objects.EventObject;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.GetRequest;
import org.saarang.saarangsdk.Network.HttpRequest;

/**
 * Created by Moochi on 24-08-2015.
 */
public class GetEvents extends IntentService {

    private static String LOG_TAG = "GetEvents";

    JSONObject json;
    int status = 200;
    JSONArray jsonArray;

    public GetEvents() {
        super("GetEvents");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(LOG_TAG, " Started ");
        // Getting the list of events from server
        json = GetRequest.execute(URLConstants.URL_GETEVENTS, UserProfile.getUserToken(this));

        Log.d(LOG_TAG, json.toString());
        try {
            // Converting obtained response into JSON Array
            jsonArray = json.getJSONObject("data").getJSONArray("response");

            //Saving Events into DB

            EventObject.SaveEvents(this, jsonArray);

        } catch (JSONException e) {
            jsonArray = null;
        }


    }
}
