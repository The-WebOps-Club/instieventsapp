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

    public SetUpAlarms() {
        super("SetUpAlarms");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(LOG_TAG, "Started SetUpAlarms");
            events = Event.getUpcomingRelevantEvents(this);
            Log.d(LOG_TAG, "Number of events " + events.size());
            for (int i = 0; i < events.size(); i++){
                event = events.get(i);
                Log.d(LOG_TAG, "Alarm set for event " + event.getName() + " at " + event.getTime());
                long time = TimeHelper.getTimeStamp(event.getTime());
                Intent startAlarm = new Intent(this, ShowNotification.class);
                startAlarm.putExtra(ShowNotification.EXTRA_EVENT, gson.toJson(event));
                PendingIntent pendingIntent = PendingIntent.getService(this, 0, startAlarm, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }

        }
    }

}
