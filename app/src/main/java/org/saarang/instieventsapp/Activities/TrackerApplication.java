package org.saarang.instieventsapp.Activities;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import org.saarang.instieventsapp.R;

/**
 * Created by kiran on 10/9/15.
 */
public class TrackerApplication extends Application {

    public Tracker mTracker;

    public void startTracking(){

        if(mTracker==null){

            GoogleAnalytics ga=GoogleAnalytics.getInstance(this);
            mTracker=ga.newTracker(R.xml.track_app);
            ga.enableAutoActivityReports(this);
            ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);   ;
        }
    }

    public Tracker getTracker(){
        startTracking();
        return  mTracker;
    }
}
