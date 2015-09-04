package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.saarang.instieventsapp.Activities.EventsDetailsActivity;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;
import org.saarang.saarangsdk.Helpers.TimeHelper;
import java.util.ArrayList;

/**
 * Created by Seetharaman on 09-08-2015.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Event> mItems;
    TimeHelper th;
    String LOG_TAG = "EventsAdapter";


    public EventsAdapter(Context context, ArrayList<Event> items)
    {
        mContext = context;
        mItems = items;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

         TextView tvHeading, tvDate, tvTime, tvLocation, tvDescription;
        LinearLayout eventsfeed;
        public ViewHolder(View itemView) {
            super(itemView);

            //Getting variables from id's in item layout
            tvHeading = (TextView)itemView.findViewById(R.id.tvHeading);
            tvDate = (TextView)itemView.findViewById(R.id.tvDate);
            tvTime = (TextView)itemView.findViewById(R.id.tvTime);
            eventsfeed=(LinearLayout)itemView.findViewById(R.id.events_feed);
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

        th = new TimeHelper();

        holder.tvHeading.setText(mItems.get(position).getName());
        holder.tvDate.setText(th.getDate(mItems.get(position).getTime()) == ""?"Event date has not been fixed":th.getDate(mItems.get(position).getTime()));
        holder.tvTime.setText(th.getTime(mItems.get(position).getTime()) == ""?"Event time has not been decided":th.getTime(mItems.get(position).getTime()));
        holder.tvLocation.setText(mItems.get(position).getVenue() == null?"Event venue has not been announced":mItems.get(position).getVenue());
        holder.tvDescription.setText(mItems.get(position).getDescription());
        holder.eventsfeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventsDetailsActivity.class);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
