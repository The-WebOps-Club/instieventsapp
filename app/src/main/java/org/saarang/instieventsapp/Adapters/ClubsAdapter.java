package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.saarang.instieventsapp.Activities.ClubDetailActivity;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.R;

import java.util.ArrayList;

/**
 * Created by kevin selva prasanna on 08-Aug-15.
 */
public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder>{

    Context mContext;
    ArrayList<Club> mList;
    public ClubsAdapter(Context context,ArrayList<Club> list) {
        mContext = context;
        mList=list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        Button bViewMore;
        Button bSubscribe;
        public ViewHolder(View view) {
            super(view);
            bViewMore = (Button)view.findViewById(R.id.bViewMore);
            bSubscribe = (Button)view.findViewById(R.id.bSubscibe);


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
        markAsSubscribed(holder.bSubscribe);


        holder.bViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),ClubDetailActivity.class);
                view.getContext().startActivity(myIntent);
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
}
