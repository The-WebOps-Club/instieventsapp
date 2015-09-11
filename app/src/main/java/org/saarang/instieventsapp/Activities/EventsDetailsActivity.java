package org.saarang.instieventsapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.Result;
import org.saarang.instieventsapp.R;
import org.saarang.saarangsdk.Helpers.TimeHelper;
import org.saarang.saarangsdk.Utils.SaarangIntents;

import java.util.ArrayList;
import java.util.Collections;

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
    ArrayList<Integer> positionlist;
    ArrayList<Integer> scorelist;
    int[] score;
    int[] positions;
    Result[] result;

    @Override
    protected void onStart() {
        super.onStart();
        Tracker tracker=((TrackerApplication) getApplication()).getTracker();
        tracker.setScreenName("EventDetailsActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

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

        scorelist=new ArrayList<>();
        positionlist=new ArrayList<>();

        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        //TextView tvDate2 = (TextView) findViewById(R.id.tvDate2);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        TextView tvPlace = (TextView) findViewById(R.id.tvPlace);
        TextView tvConvener = (TextView) findViewById(R.id.tvConvenor);
        LinearLayout coordlayout=(LinearLayout)findViewById(R.id.coord_layout);
        TextView coordsep=(TextView)findViewById(R.id.coord_sep);
        // to hide the separator after coordinators if there are no coords
        LinearLayout resultlayout=(LinearLayout) findViewById(R.id.result_layout);
        TextView resultsep=(TextView) findViewById(R.id.result_sep);

        Log.d(LOG_TAG, event.getName() + " :: " + event.getTime() + " :: " + event.getVenue() + " :: " + event.getDescription());

       // tvDate2.setText(event.getName());
        if(event.getTime()==null){
            tvTime.setText("Event time has not been fixed");
            tvDate.setText("Event date has not been fixed");
        }
        else{
            tvTime.setText(TimeHelper.getTime(event.getTime()));
            tvDate.setText(TimeHelper.getDate(event.getTime()));
        }

        if(event.getVenue()==null){
            tvPlace.setText("Event venue has not been fixed");
        }
        else{
            tvPlace.setText(event.getVenue());
        }

        tvDescription.setText(event.getDescription());

        //TODO tvConvener.setText(event.something);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbarEventDetails);
        tool.setTitle(event.getName());
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
                    SaarangIntents.call(mContext ,eventconvenors[finalI1].getConPhone());
                }
            });
        }

        if(eventconvenors.length==0){
          coordlayout.setVisibility(View.GONE);
          coordsep.setVisibility(View.GONE);
        }

       Log.d(LOG_TAG,event.getResult());

        LinearLayout llResult = (LinearLayout) findViewById(R.id.llResut);

        //TODO Clear mLinearLayout

        Gson gson1 = new Gson();
        String resultstring = event.getResult();
        result=gson1.fromJson(resultstring,Result[].class);
        Log.d(LOG_TAG, gson.toJson(result, Result[].class));

        for(i=0; i<result.length; i++){
            scorelist.add(Integer.parseInt(result[i].getScore()));
        }

        if(result.length==0){
            resultlayout.setVisibility(View.GONE);
            resultsep.setVisibility(View.GONE);
        }
        else{

            Collections.sort(scorelist, Collections.reverseOrder());

            score = new int[scorelist.size()];
            for( i = 0; i < scorelist.size(); i++) {
                score[i] = scorelist.get(i);
            }

            for (i=0; i<result.length; i++){
                int currpos=0;
                for(int j=0; j<result.length; j++){
                    if(Integer.parseInt(result[j].getScore())==score[i]){
                        currpos=j;

                    }
                }
                positionlist.add(currpos);
            }

            positions = new int[positionlist.size()];
            for( i = 0; i < positionlist.size(); i++) {
                positions[i] = positionlist.get(i);

            }
            int pos;
            for (i = 0; i < result.length; i++) {


                View tempView = LayoutInflater.from(mContext).inflate(R.layout.event_result, llResult, false);
                TextView position = (TextView) tempView.findViewById(R.id.res_position);
                TextView hostelname=(TextView) tempView.findViewById(R.id.res_hostel);
                TextView score=(TextView) tempView.findViewById(R.id.res_score);
                pos=positions[i];
                position.setText(""+(i+1));
                hostelname.setText(result[pos].getHostel());
                score.setText(result[pos].getScore());
                llResult.addView(tempView);
            }

        }
        //Arrays.sort(score);
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
