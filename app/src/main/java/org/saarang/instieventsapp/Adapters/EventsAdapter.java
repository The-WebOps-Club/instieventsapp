package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;

import java.util.ArrayList;

/**
 * Created by Seetharaman on 09-08-2015.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Event> mItems;


    public EventsAdapter(Context context, ArrayList<Event> items)
    {
        mContext = context;
        mItems = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvHeading, tvDate, tvTime, tvLocation, tvDescription;


        public ViewHolder(View itemView) {
            super(itemView);

            //Getting variables from id's in item layout
            tvHeading = (TextView)itemView.findViewById(R.id.tvHeading);
            tvDate = (TextView)itemView.findViewById(R.id.tvDate);
            tvTime = (TextView)itemView.findViewById(R.id.tvTime);
            tvLocation = (TextView)itemView.findViewById(R.id.tvLocation);
            tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);

        }
    }
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_events_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, int position) {
        holder.tvHeading.setText(mItems.get(position).getName());
        holder.tvDate.setText(mItems.get(position).getTime());
        holder.tvTime.setText(mItems.get(position).getTime());
        holder.tvLocation.setText(mItems.get(position).getVenue());
        holder.tvDescription.setText(mItems.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
