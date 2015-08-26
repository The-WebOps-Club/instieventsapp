package org.saarang.instieventsapp.Services;


/**
 * Created by kevin selva prasanna on 24-Aug-15.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.saarang.instieventsapp.Activities.MainActivity;
import org.saarang.instieventsapp.R;

public class IE_GCMListenerService extends GcmListenerService{

    private static final String TAG = "GcmListenerService";
    String message;
    String data1;
    String type;


    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        message = data.getString("message");
        data1 = data.getString("data");
        type = data.getString("type");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG ,data.toString());

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
        sendNotification(message);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        PendingIntent pendingIntent;
        Intent i;
        Uri defaultSoundUri;
        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager;
        switch (message) {
            case "0":
                i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=org.saarang.app"));
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                        PendingIntent.FLAG_ONE_SHOT);

                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("New Update Avalibale!")
                        .setContentText("Click to update now")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                break;
            case "1":
                i = new Intent(IE_GCMListenerService.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                        PendingIntent.FLAG_ONE_SHOT);

                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("New Event Added")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                break;
            case "2":
                i = new Intent(IE_GCMListenerService.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                        PendingIntent.FLAG_ONE_SHOT);

                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("Event details added")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

                break;
            case "3":
                i = new Intent(IE_GCMListenerService.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                        PendingIntent.FLAG_ONE_SHOT);

                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("Results Out!")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                break;
            case "4":
                i = new Intent(IE_GCMListenerService.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                        PendingIntent.FLAG_ONE_SHOT);

                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("Club Subscribed")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                break;
            case "5":
                i = new Intent(IE_GCMListenerService.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                        PendingIntent.FLAG_ONE_SHOT);

                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("Scoreboard Updated!")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                break;
            case "6":
                i = new Intent(IE_GCMListenerService.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                        PendingIntent.FLAG_ONE_SHOT);

                defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("New club added!")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                break;


            default:
            i = new Intent(IE_GCMListenerService.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                    PendingIntent.FLAG_ONE_SHOT);

            defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle("GCM Message")
                    .setContentText("message:" + message + " data:" + data1 + " type" + type)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }
}
