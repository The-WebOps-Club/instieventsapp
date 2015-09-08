package org.saarang.instieventsapp.IntentServices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ShowNotification extends IntentService {

    private static String LOG_TAG = "ShowNotification";
    public static String EXTRA_EVENT = "event";

    public ShowNotification() {
        super("ShowNotification");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(LOG_TAG, "Show a notification for event" + intent.getExtras().getString(EXTRA_EVENT));
        }
    }
}
