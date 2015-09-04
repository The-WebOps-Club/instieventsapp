package org.saarang.instieventsapp.IntentServices;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.Utils.SPUtils;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.GetRequest;

public class GetSetupData extends IntentService {

    private static String LOG_TAG = "GetSetupData";
    String category;
    JSONObject json, jScoreBoard, jScoreCard, jEvent;
    JSONArray jScoreCards;
    String scoreBoardId;
    Event event;


    public GetSetupData() {
        super("GetSetupData");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            Gson gson= new Gson();

                try {
                // Fetching all events
                JSONObject json = GetRequest.execute(URLConstants.URL_EVENT_FETCH,
                        UserProfile.getUserToken(this));
                Log.d(LOG_TAG, json.toString());
                if (json.getInt("status") == 200){
                    SPUtils.setLastUpdateDate(this);
                    JSONArray jEvents = json.getJSONObject("data").getJSONArray("response");
                    for (int i=0; i<jEvents.length(); i++){
                        jEvent = jEvents.getJSONObject(i);
                        event = new Event(jEvent);
//                        Log.d(LOG_TAG, "Category of event is " + event.getCate)
                        event.saveEvent(this);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                // Fetching all the scoreboards
                json = GetRequest.execute(URLConstants.URL_SCORECARD_FETCH,
                        UserProfile.getUserToken(this));
                Log.d(LOG_TAG, json.toString());
                JSONArray jScoreBoards = json.getJSONObject("data").getJSONArray("response");
                for (int i =0; i< jScoreBoards.length(); i++){
                    jScoreBoard = jScoreBoards.getJSONObject(i);
                    category = jScoreBoard.getString("category");
                    jScoreCards = jScoreBoard.getJSONArray("scorecard");
                    scoreBoardId = jScoreBoard.getString("_id");

                    for (int j = 0; j< jScoreCards.length(); j++){
                        jScoreBoard = jScoreCards.getJSONObject(j);
                        ContentValues cv = ScoreCard.getCV(category,
                                jScoreBoard.getJSONObject("hostel").getString("name"),
                                jScoreBoard.getInt("score"), scoreBoardId + jScoreBoard.getString("_id"));
                        ScoreCard.saveScoreCard(this, cv);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
