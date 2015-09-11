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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Activities.ClubDetailActivity;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.UIUtils;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.GetRequest;

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
        TextView tvName,tvDesc;
        ImageView ivProf;


        public ViewHolder(View view) {
            super(view);
            bViewMore = (Button)view.findViewById(R.id.bViewMore);
            bSubscibe = (Button)view.findViewById(R.id.bSubscribe);
            tvName=(TextView) view.findViewById(R.id.titleoverlay);
            ivProf=(ImageView) view.findViewById(R.id.ivProfilePic);
            tvDesc=(TextView)view.findViewById(R.id.tvDesc);

        }
    }
    public ClubsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clubs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(mList.get(position).getIsSubscribed()){

            holder.bSubscibe.setText("Subscribed");
        }else {
            holder.bSubscibe.setText("Subscribe");
        }


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
                if (!mList.get(position).getIsSubscribed()) {

                    if(Connectivity.isNetworkAvailable(mContext)){
                        Subscribe subscribe = new Subscribe();
                        subscribe.execute(mList.get(position).getId(), holder.bSubscibe,position);
                    }
                    else
                        UIUtils.showSnackBar(v,"Connection not available");

                }


            }
        });

        holder.tvName.setText(mList.get(position).getName());
        holder.tvDesc.setText(mList.get(position).getDescription());
        Glide.with(mContext).load(R.drawable.webclub).centerCrop().into(holder.ivProf);
    }

    public void markAsSubscribed(Button subscribe){
        subscribe.setText("Subscribed");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class Subscribe extends AsyncTask<Object,Void,Void>{

        int status=200;
        TextView tvSubscribe;
        int pos;

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

            String clubId=(String) params[0];
            String urlString= URLConstants.URL_SUBSCRIBE_CLUB+clubId;

            JSONObject JSONrequest = new JSONObject();
            tvSubscribe=(TextView) params[1];
            pos=(Integer) params[2];

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
            if(status==200)
            {tvSubscribe.setText("Subscribed");
            mList.get(pos).setIsSubscribed(true);
            Club.updateSubscription(mContext,mList.get(pos).getId(),1);}

        }
    }


}