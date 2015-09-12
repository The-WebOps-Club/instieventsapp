package org.saarang.instieventsapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.saarang.instieventsapp.Adapters.ClubDetailAdapter;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.UIUtils;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.GetRequest;
import org.saarang.instieventsapp.Utils.URLConstants;

import java.util.ArrayList;

public class ClubDetailActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Event> mEvent;
    RecyclerView recyclerView;
    Context mContext=ClubDetailActivity.this;
    private static String LOG_TAG = "ClubDetailActivity";
    ProgressDialog pDialog;
    @Override
    protected void onStart() {
        super.onStart();
        Tracker tracker=((TrackerApplication) getApplication()).getTracker();
        tracker.setScreenName("ClubDetailActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_club_detail);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview2);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final FloatingActionButton floatB;
        floatB=(FloatingActionButton)findViewById(R.id.floatB);

        Bundle getclubid=getIntent().getExtras();
        String clubId;
        clubId=getclubid.getString(Club.KEY_ROWID);
        final Club club= Club.getAClub(mContext, clubId);
        if(club.getIsSubscribed()){

            floatB.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.floating_button_sub));
        }
        else{
            floatB.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.floating_button_notsub));
        }


        mEvent=Event.getClubEvents(mContext,club.getId());
        adapter = new ClubDetailAdapter(this,club,mEvent);
        recyclerView.setAdapter(adapter);

        floatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Connectivity.isNetworkAvailable(mContext)){

                    if(club.getIsSubscribed()){
                        Subscribe subscribe=new Subscribe();
                        subscribe.execute(club.getId(),1,club.getName(),floatB);
                    }
                    else
                    {
                        Subscribe subscribe=new Subscribe();
                        subscribe.execute(club.getId(),0,club.getName(),floatB);
                    }
                }
                else
                {
                    UIUtils.showSnackBar(v,"Connection not available");
                }

            }
        });



        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarClubDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);
        /*toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.ic_arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(club.getName());
        //collapsingToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this)
                .load(URLConstants.URL_CLUB_LOGO + club.getLogo())
                .placeholder(R.drawable.events_bg)
                .into(imageView);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_club_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    //    if (item.getItemId()==R.id.action_aboutus){startActivity(new Intent(this, AboutUsActivity.class));}
        return super.onOptionsItemSelected(item);
    }

    private class Subscribe extends AsyncTask<Object,Void,Void> {

        int status=200;
        int isSubscribed;
        String clubname,clubId;
        FloatingActionButton floatB;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Please Wait....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Object... params) {

            clubId=(String) params[0];

            JSONObject JSONrequest = new JSONObject();

            isSubscribed=(Integer) params[1];
            clubname=(String) params[2];
            floatB=(FloatingActionButton) params[3];

            String urlString;

            if(isSubscribed==1){
                 urlString= URLConstants.URL_UNSUBSCRIBE_CLUB+clubId;
            }
            else{
                 urlString= URLConstants.URL_SUBSCRIBE_CLUB+clubId;
            }

            try {

                JSONrequest.put("Clubid", clubId);
                Log.d(LOG_TAG, "2 JSONrequest\n" + JSONrequest.toString());
                Log.d(LOG_TAG, "3 urlstring :: " + urlString);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject responseJSON = GetRequest.execute(urlString, UserProfile.getUserToken(mContext));
            if (responseJSON == null) {
                return null;

            }

            try {
                status = responseJSON.getInt("status");
                Log.d(LOG_TAG,""+(status));

                if (status == 200) {
                    Log.d(LOG_TAG, "successfull\n");

                }
                else
                    Log.d(LOG_TAG,"Unsuccessful\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            if(status==200) {

                if (isSubscribed == 1) {
                    Toast.makeText(mContext, "Unsubscribed to " + clubname, Toast.LENGTH_SHORT).show();
                    Club.updateSubscription(mContext, clubId, 0);
                    floatB.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.floating_button_notsub));
                    Intent intent=new Intent();
                    intent.setAction("com.reload.RELOAD_ADAPTER");
                    sendBroadcast(intent);
                } else {
                    Toast.makeText(mContext, "Subscribed to" + clubname, Toast.LENGTH_SHORT).show();
                    Club.updateSubscription(mContext, clubId, 1);
                    floatB.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.floating_button_sub));
                    Intent intent=new Intent();
                    intent.setAction("com.reload.RELOAD_ADAPTER");
                    sendBroadcast(intent);
                }

            }
        }

    }

}
