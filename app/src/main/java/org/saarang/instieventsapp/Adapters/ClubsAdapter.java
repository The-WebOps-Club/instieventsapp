package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.saarang.instieventsapp.Activities.ClubDetailActivity;
import org.saarang.instieventsapp.R;

/**
 * Created by kevin selva prasanna on 08-Aug-15.
 */
public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder>{

    Context mContext;
    public ClubsAdapter(Context context) {
        mContext = context;
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

        holder.bViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),ClubDetailActivity.class);
                view.getContext().startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
