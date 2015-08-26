package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;
import org.saarang.saarangsdk.Helpers.TimeHelper;

import java.util.ArrayList;

/**
 * Created by kevin selva prasanna on 25-Aug-15.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    ArrayList<Event> events;
    Context mContext;
    public ScheduleAdapter(Context context, ArrayList<Event> events ) {
        mContext = context;
        this.events = events;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate, tvName, tvClub;
        public ViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvClub = (TextView) view.findViewById(R.id.tvClub);
        }
    }
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(events.get(position).getName());
        holder.tvClub.setText(events.get(position).eventContext());
        holder.tvDate.setText(TimeHelper.getRelative(events.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
