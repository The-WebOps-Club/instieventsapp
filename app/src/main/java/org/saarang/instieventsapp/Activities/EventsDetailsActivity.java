package org.saarang.instieventsapp.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.saarang.instieventsapp.Objects.Club;
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
    Club mClub;
    private static String LOG_TAG = "EventDetails";
    Event.Convenor[] eventconvenors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_events_details);

        // Collecting event id from the parent Activity

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString(Event.COLUMN_EVENTID);

        // Getting event object
        Log.d(LOG_TAG,"event = Event.getAnEvent(mContext," + id + ");" );
        event = Event.getAnEvent(mContext, id);
        Log.d(LOG_TAG,"event.getId() :: " + event.getId() );

        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        TextView tvDate2 = (TextView) findViewById(R.id.tvDate2);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        TextView tvPlace = (TextView) findViewById(R.id.tvPlace);
        TextView tvConvener = (TextView) findViewById(R.id.tvConvenor);


        Log.d(LOG_TAG, event.getName() + " :: " + event.getTime() + " :: " + event.getVenue() + " :: " + event.getDescription());

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


        //Adding Convenors

        LinearLayout llConvenors = (LinearLayout) findViewById(R.id.llConvenors);

        //TODO Clear mLinearLayout

        Gson gson = new Gson();
        eventconvenors = event.getConvenors();
        Log.d(LOG_TAG, gson.toJson(eventconvenors, Event.Convenor[].class));

        int i;
        for (i = 0; i < eventconvenors.length; i++) {


            View tempView = LayoutInflater.from(mContext).inflate(R.layout.event_convenors, llConvenors, false);
            TextView convenor = (TextView) tempView.findViewById(R.id.EtvConvenor);
            ImageView call = (ImageView) tempView.findViewById(R.id.EivCall);
            ImageView mail = (ImageView) tempView.findViewById(R.id.EivMail);
            convenor.setText(eventconvenors[i].getConName());
            final int finalI = i;

            mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailintent = new Intent(Intent.ACTION_SEND);
                    String[] abc = {eventconvenors[finalI].getConEmail()};
                    emailintent.putExtra(Intent.EXTRA_EMAIL, abc);
                    emailintent.setType("plain/text");
                    mContext.startActivity(emailintent);
                }
            });
            llConvenors.addView(tempView);

            final int finalI1 = i;
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phonecall = new Intent(Intent.ACTION_CALL);
                    String call = "tel:" + eventconvenors[finalI1].getConPhone();
                    phonecall.setData(Uri.parse(call));
                    try {
                        mContext.startActivity(phonecall);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

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
