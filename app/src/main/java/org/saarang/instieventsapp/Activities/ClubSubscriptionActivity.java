package org.saarang.instieventsapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Adapters.ClubSubscriptionAdapter;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.GetRequest;

import java.util.ArrayList;

/**
 * Created by kiran on 23/8/15.
 */
public class ClubSubscriptionActivity extends AppCompatActivity {


    RecyclerView rvClubs;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button bNext;
    String LOG_TAG="ClubSubscriptionActivity";
    private ArrayList<Club> list;
    private ArrayList<String> subclub;
    JSONArray jsonsub;
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
        Toolbar tool=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        rvClubs.setLayoutManager(layoutManager);

        //initialise();
        list=new ArrayList<>();
        list = Club.getAllClubs(context);
        adapter=new ClubSubscriptionAdapter(context,list);
        rvClubs.setAdapter(adapter);

       // adapter=new ClubSubscriptionAdapter(this,list);
        //rvClubs.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subscriptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id=item.getItemId();
        if(id==R.id.gotomain){
            Intent i;
            i = new Intent("org.saarang.instieventsapp.Activities.MAINACTIVITY");
            subclub=new ArrayList<>();
            for(int j=0; j<list.size(); j++){
                if(list.get(j).getIsSubscribed()){
                    subclub.add(list.get(j).getId());
                }
            }

            jsonsub=new JSONArray(subclub);
            Log.d(LOG_TAG,jsonsub.toString());
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void initialise(){

        list=new ArrayList<>();
        if (Connectivity.isConnected()) {
            Log.d(LOG_TAG, "Retrieving club details ... ");
            details = new Clubdetails();
            details.execute();

        }
        else {
            Log.d(LOG_TAG, "1 no net");
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
