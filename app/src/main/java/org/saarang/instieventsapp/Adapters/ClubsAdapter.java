package org.saarang.instieventsapp.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Activities.ClubDetailActivity;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.HttpRequest;

import java.util.ArrayList;

/**
 * Created by kevin selva prasanna on 08-Aug-15.
 */
public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder>{

    Context mContext;
    ArrayList<Club> mList;
    ProgressDialog pDialog;
    String LOG_TAG="ClubsAdapter";

    public ClubsAdapter(Context context,ArrayList<Club> list) {
        mContext = context;
        mList=list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        Button bViewMore;
        Button bSubscibe;
        public ViewHolder(View view) {
            super(view);
            bViewMore = (Button)view.findViewById(R.id.bViewMore);
            bSubscibe = (Button)view.findViewById(R.id.bSubscibe);


        }
    }
    public ClubsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clubs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(mList.get(position).getIsSubscribed())
        markAsSubscribed(holder.bSubscibe);


        holder.bViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle clubId=new Bundle();
                clubId.putString(Club.KEY_ROWID,mList.get(position).getId());
                Intent myIntent = new Intent(view.getContext(),ClubDetailActivity.class);
                myIntent.putExtras(clubId);
                view.getContext().startActivity(myIntent);
            }
        });

        holder.bSubscibe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Subscribe subscribe=new Subscribe();
                subscribe.execute(mList.get(position).getId());
            }
        });
    }

    public void markAsSubscribed(Button subscribe){
        subscribe.setText("Subscribed");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

private class Subscribe extends AsyncTask<String,Void,Void>{

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
    protected Void doInBackground(String... params) {
        String urlString= URLConstants.URL_SUBSCRIBE_CLUB+params[0];

        JSONObject JSONrequest = new JSONObject();
        try {

            JSONrequest.put("Clubid", params[0]);
            Log.d(LOG_TAG, "2 JSONrequest\n" + JSONrequest.toString());
            Log.d(LOG_TAG, "3 urlstring :: " + urlString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject responseJSON = HttpRequest.execute("POST", urlString, null, JSONrequest);
        if (responseJSON == null) {
            return null;

        }

        try {
            status = responseJSON.getInt("status");
            if (status == 200) {
                Log.d(LOG_TAG, "successfull\n");}
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
