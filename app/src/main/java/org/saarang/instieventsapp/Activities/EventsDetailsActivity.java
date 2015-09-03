package org.saarang.instieventsapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.saarang.instieventsapp.R;

/**
 * Created by kiran on 26/8/15.
 */
public class EventsDetailsActivity extends AppCompatActivity {

    Context mContext=EventsDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_events_details);

        Toolbar tool=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);

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

      //  Event event=Event.getAEvent(mContext,eventId);


    }
}
