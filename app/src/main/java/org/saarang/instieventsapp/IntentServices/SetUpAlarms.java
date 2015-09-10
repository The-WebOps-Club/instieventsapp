package org.saarang.instieventsapp.IntentServices;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.saarang.instieventsapp.Objects.Event;
import org.saarang.saarangsdk.Helpers.TimeHelper;

import java.util.ArrayList;

public class SetUpAlarms extends IntentService {

    private static String LOG_TAG = "SetUpAlarms";
    ArrayList<Event> events;
    Event event;
    Gson gson = new Gson();
    private static long notificationTime = 330 * 60 * 1000 - 10 * 60 * 1000;

    public SetUpAlarms() {
        super("SetUpAlarms");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(LOG_TAG, "Started SetUpAlarms");
//            events = Event.getAllEvents(this);
            events = Event.getEventsForNotification(this);
            Log.d(LOG_TAG, TimeHelper.getTimeStamp(System.currentTimeMillis()));
            Log.d(LOG_TAG, "Number of events " + events.size());
            for (int i = 0; i < events.size(); i++){
                event = events.get(i);
                Log.d(LOG_TAG, new Gson().toJson(event));
                Log.d(LOG_TAG, "Alarm set for event " + event.getName() + " at " + event.getTime());
                long time = TimeHelper.getTimeStamp(event.getTime());
                Intent startAlarm = new Intent(this, ShowNotification.class);
                startAlarm.putExtra(ShowNotification.EXTRA_EVENT, gson.toJson(event));
                PendingIntent pendingIntent = PendingIntent.getService(this, 0, startAlarm, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, time + notificationTime , pendingIntent);
                Log.d(LOG_TAG, "Time of the event " + time + " " + TimeHelper.getTimeStamp(time));
                Long systemTime = System.currentTimeMillis();
                Log.d(LOG_TAG, "Time of the systemTime " + systemTime + " " + TimeHelper.getTimeStamp(systemTime));
                Log.d(LOG_TAG, "Time differnce is " + (time - System.currentTimeMillis()) );
            }

        }
    }

}
