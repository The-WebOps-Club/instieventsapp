package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;
import org.saarang.saarangsdk.Helpers.TimeHelper;

import java.util.ArrayList;


/**
 * Created by kevin selva prasanna on 25-Aug-15.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Event> events;

    public ScheduleAdapter(Context context, ArrayList<Event> events) {
        mContext = context;
        this.events = events;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView textimage;
        TextView tvDate, tvName, tvClub;
        public ViewHolder(View view) {
            super(view);
            textimage = (ImageView)view.findViewById(R.id.iv_text);
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
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color1 = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(events.get(position).eventContext().charAt(0)), color1);
        holder.textimage.setImageDrawable(drawable);
        holder.tvName.setText(events.get(position).getName());
        holder.tvClub.setText(events.get(position).eventContext());
        if(TimeHelper.getDate(events.get(position).getTime()) != null) {
            holder.tvDate.setText(TimeHelper.getDate(events.get(position).getTime()));
            
        }
        else
        {
            holder.tvDate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
