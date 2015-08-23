package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.R;

import java.util.List;

/**
 * Created by kiran on 23/8/15.
 */
public class ClubSubscriptionAdapter extends RecyclerView.Adapter<ClubSubscriptionAdapter.Viewholder> {

    List<Club> mList;
    Context mContext;

    public ClubSubscriptionAdapter(Context context,List<Club> list){
        mContext=context;
        mList=list;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        TextView clubname;
        CheckBox subscribed;


        public Viewholder(View itemView) {
            super(itemView);
            clubname=(TextView)itemView.findViewById(R.id.club_subscription_hostelname);
            subscribed=(CheckBox)itemView.findViewById(R.id.club_subscription_subscribed);

        }
    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.club_subscription_design, viewGroup, false);
        Viewholder pvh = new Viewholder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(Viewholder viewholder, int i) {
        viewholder.clubname.setText(mList.get(i).getName());
        viewholder.subscribed.setChecked(mList.get(i).getIsSubscribed());
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
}
