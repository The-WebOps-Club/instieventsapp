package org.saarang.instieventsapp.Activities;

import android.app.Activity;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Adapters.ClubSubscriptionAdapter;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.IntentService.GetEvents;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.GetRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiran on 23/8/15.
 */
public class ClubSubscriptionActivity extends Activity {


    RecyclerView rvClubs;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button bNext;
    String LOG_TAG="ClubSubscriptionActivity";
    private List<Club> list;
    ProgressDialog pDialog;
    Clubdetails details;
    int status=400;
    Context context = ClubSubscriptionActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_subscription);

        rvClubs=(RecyclerView)findViewById(R.id.reSubscription);
        layoutManager=new LinearLayoutManager(this);
        rvClubs.setLayoutManager(layoutManager);

        initialise();

        adapter=new ClubSubscriptionAdapter(this,list);
        rvClubs.setAdapter(adapter);
        bNext=(Button) findViewById(R.id.gotomain);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent("org.saarang.instieventsapp.Activities.MAINACTIVITY");
                startActivity(i);
            }
        });
    }

    public void initialise(){

        list=new ArrayList<>();
        if (Connectivity.isConnected()) {
            Log.d(LOG_TAG, "Retrieving club details ... ");
            details = new Clubdetails();
            details.execute();

            // Fetching Events in background through GetEvents IntentService
            Log.d("GetEvents", "getting events ");
            Intent getEventsIntent = new Intent(this, GetEvents.class);
            startService(getEventsIntent);



        }
        else {
            Log.d(LOG_TAG, "**1 no net");
        }

    }

    private class Clubdetails extends AsyncTask<Void,Void,Void>{

        JSONObject jEvent;
        Gson gson = new Gson();
        Club club;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ClubSubscriptionActivity.this);
            pDialog.setMessage("Getting  club details...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String urlString= URLConstants.URL_SUBSCRIBE;
            JSONObject json = GetRequest.execute(urlString, UserProfile.getUserToken(context));
            Log.d(LOG_TAG, json.toString());
            if (json == null) {
                return null;

            }
            try{

                status=json.getInt("status");

                if(status==200){
                  Log.d(LOG_TAG,"Retrieval success");
                  JSONArray jEvents=json.getJSONObject("data").getJSONArray("response");
                  for(int i=0; i< jEvents.length(); i++) {
                      jEvent = jEvents.getJSONObject(i);
                      club = gson.fromJson(jEvent.toString(), Club.class);
                      DatabaseHelper data = new DatabaseHelper(context);
                      data.addClub(club.getCV());
                  }

              }
              else {
                  Log.d(LOG_TAG,"5 unsuccessfull!! ");
              }

            }catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            list = Club.getAllClubs(context);
            adapter=new ClubSubscriptionAdapter(context,list);
            rvClubs.setAdapter(adapter);
        }
    }
}
