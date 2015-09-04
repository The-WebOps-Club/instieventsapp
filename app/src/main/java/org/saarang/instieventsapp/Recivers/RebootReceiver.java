package org.saarang.instieventsapp.Recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.saarang.instieventsapp.IntentServices.SetUpAlarms;

/**
 * Created by Ahammad on 04/09/15.
 */
public class RebootReceiver extends BroadcastReceiver {

    private static String LOG_TAG = "RebootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(LOG_TAG, "System restarted");
            Intent intentService = new Intent(context, SetUpAlarms.class);
            context.startService(intentService);

        }
    }
}
