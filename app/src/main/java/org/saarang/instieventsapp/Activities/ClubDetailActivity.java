package org.saarang.instieventsapp.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Adapters.ClubDetailAdapter;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.GetRequest;

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
        tracker.setScreenName("ClubDetailsActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_club_detail);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview2);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton floatB;
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

        floatB.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if(club.getIsSubscribed()){

                   Subscribe subscribe=new Subscribe();
                    subscribe.execute(club.getId(),0);
                    //v.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.floating_button_notsub));
                    v.setBackgroundColor(mContext.getResources().getColor(R.color.floating_button_notsub));
                }
                else{

                    Subscribe subscribe=new Subscribe();
                    subscribe.execute(club.getId(), 1);
                    //v.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.floating_button_sub));
                    v.setBackgroundColor(mContext.getResources().getColor(R.color.floating_button_sub));
                }
            }
        });


        mEvent=Event.getClubEvents(mContext,club.getId());
        adapter = new ClubDetailAdapter(this,club,mEvent);
        recyclerView.setAdapter(adapter);





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
        Glide.with(this).load(R.drawable.webclub).centerCrop().into(imageView);

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
            String id=(String) params[0];
            int bool=(Integer) params[1];
            String urlString;
            if(bool==0){
                urlString= URLConstants.URL_SUBSCRIBE_CLUB+id;}
            else{
                urlString=URLConstants.URL_UNSUBSCRIBE_CLUB+id;
            }

            JSONObject JSONrequest = new JSONObject();


            try {

                JSONrequest.put("Clubid",id);
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

                    if(bool==1)
                    {
                        DatabaseHelper data=new DatabaseHelper(mContext);
                        data.updateClub(0,id);
                    }
                    else
                    {
                        DatabaseHelper data=new DatabaseHelper(mContext);
                        data.updateClub(1,id);
                    }
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
        }
    }



}
