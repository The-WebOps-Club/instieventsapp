package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saarang.instieventsapp.R;

/**
 * Created by Seetharaman on 09-08-2015.
 */
public class EventsFeedAdapter extends RecyclerView.Adapter<EventsFeedAdapter.ViewHolder> {

    Context mContext;
    String[] mItems;
    String[] dates = {"15th August 2015", "17th September 2015", "8th November 2015", "21st December 2015",
                                            "1st January 2015"};
    String[] time = {"8 pm", "6 pm", "9:30 am", "10am", "11 pm"};
    String[] locations = {"MRC", "SAC", "OAT", "CRC", "SAC"};
    String[] descriptions = {"random", "gen", "St. agur blue cheese rubber cheese caerphilly cheddar cheesecake cream cheese manchego lancashire. ",
            "Cheeseburger swiss bavarian bergkase cream cheese fromage frais cheesy feet port-salut airedale.",
            "Jarlsberg lancashire edam. Dolcelatte hard cheese brie st. agur blue\n" +
            " cheese caerphilly bavarian bergkase cheese and biscuits mascarpone.  Roquefort squirty cheese\n" +
            " the big cheese."};

    public EventsFeedAdapter(Context context, String[] items)
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
    public EventsFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_events_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsFeedAdapter.ViewHolder holder, int position) {
        holder.tvHeading.setText(mItems[position]);
        holder.tvDate.setText(dates[position]);
        holder.tvTime.setText(time[position]);
        holder.tvLocation.setText(locations[position]);
        holder.tvDescription.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }
}
