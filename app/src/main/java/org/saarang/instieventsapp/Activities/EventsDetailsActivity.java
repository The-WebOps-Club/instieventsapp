package org.saarang.instieventsapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.saarang.instieventsapp.R;

/**
 * Created by kiran on 26/8/15.
 */
public class EventsDetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_events_details);

        Toolbar tool=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
    }
}
