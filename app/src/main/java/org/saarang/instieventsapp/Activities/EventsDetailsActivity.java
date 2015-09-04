package org.saarang.instieventsapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;
import org.saarang.saarangsdk.Helpers.TimeHelper;

/**
 * Created by kiran on 26/8/15.
 */
public class EventsDetailsActivity extends AppCompatActivity {

    Context mContext = EventsDetailsActivity.this;
    String id;
    Event event;
    private static String LOG_TAG = "EventDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_events_details);

        // Collecting event id from the parent Activity

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString(Event.COLUMN_EVENTID);

        // Getting event object
        event = Event.getAnEvent(mContext, id);



        TextView tvDate = (TextView)findViewById(R.id.tvDate);

        TextView tvDate2 = (TextView)findViewById(R.id.tvDate2);
        TextView tvTime = (TextView)findViewById(R.id.tvTime);
        TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
        TextView tvPlace = (TextView)findViewById(R.id.tvPlace);
        TextView tvConvener = (TextView)findViewById(R.id.tvConvenor);


        Log.d(LOG_TAG, event.getName() + " :: " +event.getTime() +  " :: " + event.getVenue()+ " :: "+ event.getDescription());

        tvDate2.setText(event.getName());
        tvTime.setText(TimeHelper.getTime(event.getTime()));
        tvPlace.setText(event.getVenue());
        tvDescription.setText(event.getDescription());
        tvDate.setText(TimeHelper.getDate(event.getTime()));
        //TODO tvConvener.setText(event.something);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbarEventDetails);
        setSupportActionBar(tool);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        tool.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_left));
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      /*  Bundle geteventid=getIntent().getExtras();
        String eventId;
        eventId=geteventid.getString(Event.COLUMN_EVENTID);*/

        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                //          close activity on back
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
