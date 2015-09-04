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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Adapters.ClubSubscriptionAdapter;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.IntentServices.GetSetupData;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.HttpRequest;

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
    JSONObject jsonsub;
    ProgressDialog pDialog;
    int status=400;
    Context context = ClubSubscriptionActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Connectivity.isNetworkAvailable(context)){
            Intent intent = new Intent(this, GetSetupData.class);
            startService(intent);
        }

        setContentView(R.layout.club_subscription);

        rvClubs=(RecyclerView)findViewById(R.id.reSubscription);
        layoutManager=new LinearLayoutManager(this);
        Toolbar tool=(Toolbar) findViewById(R.id.toolbarClubSub);
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

            subclub=new ArrayList<>();
            for(int j=0; j<list.size(); j++){
                if(list.get(j).getIsSubscribed()){
                    subclub.add(list.get(j).getId());
                }
            }

            jsonsub=new JSONObject();
            try {
                jsonsub.put("clubs",new JSONArray(subclub));
                Log.d(LOG_TAG, jsonsub.toString());
                sendSubscribed send=new sendSubscribed();
                send.execute(jsonsub);

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
        return super.onOptionsItemSelected(item);
    }



   private class sendSubscribed extends AsyncTask<JSONObject,Void,Void> {
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(ClubSubscriptionActivity.this);
           pDialog.setMessage("Loading data...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(false);
           pDialog.show();
       }

       @Override
       protected Void doInBackground(JSONObject... params) {
           JSONObject json=params[0];
           String url=URLConstants.URL_SUBCLUBS;
           String token;
           token=UserProfile.getUserToken(context);
           Log.d(LOG_TAG,token);
           JSONObject responseJSON = HttpRequest.execute("POST",url , token, json);
           Log.d(LOG_TAG, responseJSON.toString());

           if(responseJSON==null){
               return null;
           }
           int status;
           try {
               status=responseJSON.getInt("status");
               if(status==200){
                   Log.d(LOG_TAG,"Subscribed data has been sent");
                   for(int j=0; j<list.size(); j++){
                       if(list.get(j).getIsSubscribed()){
                           DatabaseHelper data = new DatabaseHelper(context);
                           Log.d(LOG_TAG,list.get(j).getName());
                           data.updateClub(1,list.get(j).getId());
                       }
                       else
                       {
                           DatabaseHelper data=new DatabaseHelper(context);
                           data.updateClub(0,list.get(j).getId());
                       }
                   }

               }
               else{
                   Log.d(LOG_TAG,"Unsuccessful");
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }
           return null;
       }

       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
           pDialog.dismiss();
           UserProfile.setUserState(context, 3);
           Intent i;
           i = new Intent("org.saarang.instieventsapp.Activities.MAINACTIVITY");
           startActivity(i);

       }
   }


}
