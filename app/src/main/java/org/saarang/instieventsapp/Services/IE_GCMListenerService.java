package org.saarang.instieventsapp.Services;


/**
 * Created by kevin selva prasanna on 24-Aug-15.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Activities.EventsDetailsActivity;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Helper.SortResults;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.AppConstants;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.GetRequest;

public class IE_GCMListenerService extends GcmListenerService{

    private static final String TAG = "GcmListenerService";
    String message=" ";
    String data="";
    String type=" ",category,scoreBoardId,title=" ";
    Event event;
    String myHostel;
    int position = 0;
    JSONObject Data, jScoreBoard,Club, json;
    JSONArray jScoreBoards, jScoreCards,Clubs;
    Club club;
    Gson gson = new Gson();



    @Override
    public void onMessageReceived(String from, Bundle data1) {
        message = data1.getString("message");
        data = data1.getString("data");
        type = data1.getString("type");
        title = data1.getString("title");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG, "Its type is  " + type);
        Log.d(TAG , data1.toString());



        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */

        switch(type){
            case "0":
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=org.saarang.instieventsapp&hl=en"));
                sendNotification("New Update Available!", "Click to update now", i);
                break;
            case "1":
            case "2":
                Intent l= new Intent(this, EventsDetailsActivity.class);
                try {
                    Data = new JSONObject(data);
                    l.putExtra(Event.COLUMN_EVENTID, Data.getString("_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendNotification(title, message, l);
            case "101":
            case "201":
                try {
                    Data = new JSONObject(data);
                    if(Data.getBoolean("isLitSocEvent"))
                        Data.put("club", new JSONObject());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("JSON - CONVERSION:", Data.toString());
                event = new Event(Data);
                event.saveEvent(this);
                break;
            case "301":
                myHostel = UserProfile.getUserHostel(this);
                JSONArray events = new JSONArray();
                JSONArray results = new JSONArray();
                try {
                    events = new JSONArray(data);
                    Data = events.getJSONObject(0);
                    Data.put("club", new JSONObject());
                    Log.d("JSON - CONVERSION:", Data.toString());
                    event = new Event(Data);
                    event.saveEvent(this);
                    results = Data.getJSONArray("result");
                    Log.d(TAG, "Unsorted " + results.toString());
                    results = SortResults.getSortedList(results);
                    Log.d(TAG, "Sorted " + results);
                    for(int x=0; x< results.length();x++){

                            JSONObject hosres = results.getJSONObject(x);
                            JSONObject hostel = hosres.getJSONObject("hostel");
                            if(myHostel.equals(hostel.getString("name"))){
                                position = x + 1;
                                int score = hosres.getInt("score");
                            }
                    }

                    Intent intent = new Intent(this, EventsDetailsActivity.class);
                    intent.putExtra(Event.COLUMN_EVENTID, event.getId());
                    sendNotification(event.getName(),
                            AppConstants.getResultMessage(position, myHostel, event.getName()), intent);

                    fetchScoreBoard();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case "103":
                try {
                    Club = new JSONObject(data);
                        club = gson.fromJson(Club.toString(), Club.class);
                        Log.d(TAG, Club.getString("name"));
                        DatabaseHelper data = new DatabaseHelper(this);
                        data.addClub(club.getCV());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "302":
                fetchScoreBoard();
                break;
        }
        /*sendNotification(message);*/
    }

    private void fetchScoreBoard() {

        try {
            // Fetching new scoreboards
            json = GetRequest.execute(URLConstants.URL_SCORECARD_FETCH,
                    UserProfile.getUserToken(this));
            Log.d(TAG, json.toString());
            JSONArray jScoreBoards = json.getJSONObject("data").getJSONArray("response");
            for (int k =0; k< jScoreBoards.length(); k++){
                jScoreBoard = jScoreBoards.getJSONObject(k);
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
        } catch (JSONException e){
            e.printStackTrace();
        }

    }
    // [END receive_message]

   /* *//**
     * Create and show a simple notification containing the received GCM message.
     *
     * message GCM message received.
     */
    private void sendNotification(String Title, String content, Intent i){
        PendingIntent pendingIntent;
        Uri defaultSoundUri;
        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager;
        pendingIntent = PendingIntent.getActivity(this, 0  , i,
                PendingIntent.FLAG_ONE_SHOT);

        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setContentIntent(pendingIntent);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int)(Math.random()*1000) , notificationBuilder.build());


    }

}
