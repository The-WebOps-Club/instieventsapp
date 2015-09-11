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
import org.saarang.instieventsapp.Activities.MainActivity;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;

public class IE_GCMListenerService extends GcmListenerService{

    private static final String TAG = "GcmListenerService";
    String message=" ";
    String data="";
    String type=" ",category,scoreBoardId,title=" ";
    Event event;
    JSONObject Data, jScoreBoard,Club;
    JSONArray jScoreBoards, jScoreCards,Clubs;
    Club club;
    Gson gson = new Gson();


    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param datakjfk Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data1) {
        message = data1.getString("message");
        data = data1.getString("data");
        type = data1.getString("type");
        title = data1.getString("title");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG ,data1.toString());



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
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=org.saarang.app"));
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

                for(int x=0; x< results.length();x++){

                        JSONObject hosres = results.getJSONObject(x);
                        JSONObject hostel = hosres.getJSONObject("hostel");
                        if(UserProfile.getUserHostel(this).equals(hostel.getString("name"))){
                            int score = hosres.getInt("score");
                            sendNotification(event.getName(),"Your Hostel" + UserProfile.getUserHostel(this) + "scored" + String.valueOf(score) + "in" + event.getName(), new Intent());
                        }
                }
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
                try {
                    jScoreBoards = new JSONArray(data);
                    Log.d(TAG, "SUCCESS");
                    for (int j =0; j< jScoreBoards.length(); j++){
                        jScoreBoard = jScoreBoards.getJSONObject(j);
                        category = jScoreBoard.getString("category");
                        jScoreCards = jScoreBoard.getJSONArray("scorecard");
                        scoreBoardId = jScoreBoard.getString("_id");

                        for (int k = 0; k< jScoreCards.length(); k++){
                            jScoreBoard = jScoreCards.getJSONObject(k);
                            ContentValues cv = ScoreCard.getCV(category,
                                    jScoreBoard.getJSONObject("hostel").getString("name"),
                                    jScoreBoard.getInt("score"), scoreBoardId + jScoreBoard.getString("_id"));
                            Log.d(TAG,"cat:" + category + "score:" + jScoreBoard.getInt("score") + "id" + scoreBoardId + jScoreBoard.getString("_id")+ "hostel"+ jScoreBoard.getJSONObject("hostel").getString("name") );
                            ScoreCard.saveScoreCard(this, cv);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }break;

        }
        /*sendNotification(message);*/
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
                .setContentIntent(pendingIntent);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int)(Math.random()*1000) , notificationBuilder.build());


    }

}
