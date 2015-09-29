package org.saarang.instieventsapp.IntentServices;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import org.saarang.instieventsapp.Activities.EventsDetailsActivity;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;
import org.saarang.saarangsdk.Helpers.TimeHelper;

public class ShowNotification extends IntentService {

    private static String LOG_TAG = "SetUpAlarms";
    public static String EXTRA_EVENT = "event";
    Event event;
    Gson gson = new Gson();
    String date, time, utcDate, message, venue;

    public ShowNotification() {
        super("ShowNotification");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String eventExtra = intent.getExtras().getString(EXTRA_EVENT);
            Log.d(LOG_TAG, "EventExtra " + eventExtra);
            event = gson.fromJson(eventExtra, Event.class);
            Intent openEvent = new Intent(this, EventsDetailsActivity.class);
            openEvent.putExtra(Event.COLUMN_EVENTID, event.getId());
            utcDate = event.getTime();
            date= TimeHelper.getDate(utcDate);
            time= TimeHelper.getTime(utcDate);
            message = "Event about to start at ";
            if (!event.getTime().isEmpty()) message += time;
            if (!event.getVenue().equals(Event.NO_VENUE)) message += " at " + event.getVenue();
            if (time.equals("4:46 PM")) message = "Event Reminder";
            sendNotification(event.getName(), message, openEvent);
            Log.d(LOG_TAG, "Show a notification for event" + intent.getExtras().getString(EXTRA_EVENT));
        }
    }

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

        notificationManager.notify(0 , notificationBuilder.build());


    }
}
