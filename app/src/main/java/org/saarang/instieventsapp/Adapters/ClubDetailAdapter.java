package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.R;

/**
 * Created by kevin selva prasanna on 11-Aug-15.
 */
public class ClubDetailAdapter extends RecyclerView.Adapter<ClubDetailAdapter.ViewHolder> {

    Context mContext;
    Club mClub;
    public ClubDetailAdapter(Context context,Club club) {
        mContext = context;
        mClub=club;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView tv,tv2,description;
        public ViewHolder(View view) {
            super(view);
            tv = (TextView)view.findViewById(R.id.tv);
            tv2 = (TextView)view.findViewById(R.id.tv2);
            description=(TextView)view.findViewById(R.id.tv2);


        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public ClubDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_events_feed, parent, false);
        View view2 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.club_description, parent, false);


        switch(viewType) {
            case 0:
            case 1:
                return new ViewHolder(view2);
            default:
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(position==1){
            holder.tv.setText("Convenors:");
            String convenors="";
            for(int j=0;j<mClub.getConvenorsList().size(); j++){
            convenors=convenors+"\t"+mClub.getConvenorsList().get(j).getConName()+"\n";
            }
            holder.tv2.setText(convenors);

        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
