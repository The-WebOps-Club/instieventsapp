package org.saarang.instieventsapp.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;
import org.saarang.saarangsdk.Helpers.TimeHelper;

import java.util.ArrayList;

/**
 * Created by kevin selva prasanna on 11-Aug-15.
 */
public class ClubDetailAdapter extends RecyclerView.Adapter<ClubDetailAdapter.ViewHolder> {

    Context mContext;
    Club mClub;
    int size=8;
    ArrayList<Event> mEvents;

   // ArrayList<Club> mList;
    public ClubDetailAdapter(Context context,Club club,ArrayList<Event> events) {
        mContext = context;
        mClub=club;
        mEvents=events;
        size=mEvents.size()+2;
      //  mList=Club.getAllClubs(mContext);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView tv,tv2,description;
        ViewGroup mLinearLayout;
        TextView tvHeading,tvTime,tvDate,tvLocation,tvconvenor;
        public ViewHolder(View view) {
            super(view);
            tv = (TextView)view.findViewById(R.id.tv);
            tv2 = (TextView)view.findViewById(R.id.tv2);
            description=(TextView)view.findViewById(R.id.tv2);
            mLinearLayout = (ViewGroup) view.findViewById(R.id.convenorlist1);
            tvHeading=(TextView)view.findViewById(R.id.tvHeading);
            tvTime=(TextView)view.findViewById(R.id.tvTime);
            tvDate=(TextView)view.findViewById(R.id.tvDate);
            tvLocation=(TextView)view.findViewById(R.id.tvLocation);
            tvconvenor=(TextView)view.findViewById(R.id.convenor);


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
        View view3 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.club_convenors, parent, false);


        switch(viewType) {
            case 0:return new ViewHolder(view2);
            case 1:
                return new ViewHolder(view3);
            default:
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Club.Convenor[] clubconvenors=mClub.getConvenors();

        if(position==1){

            //Log.d("ClubDetailAdapter",convenors.getConName());
          // for(int j=0;j<clubconvenors.length; j++){

            holder.mLinearLayout.removeAllViews();
             int i;


            //holder.convenor.setText(clubconvenors[0].getConName());
            for (i = 0; i < clubconvenors.length;  i++){


                //View tempView = li.inflate(R.layout.club_convenors, null);
                View tempView=LayoutInflater.from(mContext).inflate(R.layout.club_convenors,holder.mLinearLayout,false);
                TextView convenor=(TextView)tempView.findViewById(R.id.tvConvenor);
                ImageView call=(ImageView)tempView.findViewById(R.id.ivCall);
                ImageView mail=(ImageView)tempView.findViewById(R.id.ivMail);
                convenor.setText(clubconvenors[i].getConName());
                final int finalI = i;

                mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent emailintent=new Intent(Intent.ACTION_SEND);
                        String[] abc={clubconvenors[finalI].getConEmail()};
                        emailintent.putExtra(Intent.EXTRA_EMAIL, abc);
                        emailintent.setType("plain/text");
                        mContext.startActivity(emailintent);
                    }
                });
                holder.mLinearLayout.addView(tempView);

                final int finalI1 = i;
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent phonecall=new Intent(Intent.ACTION_CALL);
                        String call="tel:"+clubconvenors[finalI1].getConPhone();
                        phonecall.setData(Uri.parse(call));
                        try{
                            mContext.startActivity(phonecall);
                        }catch (ActivityNotFoundException e){
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        }
        else if(position==0){
            holder.tv.setText("Description");
            holder.tv2.setText(mClub.getDescription());
            if(clubconvenors.length<1)
                holder.tvconvenor.setVisibility(View.INVISIBLE);

        }
        else{
            holder.tvHeading.setText(mEvents.get(position-2).getName());
            String date,time;
            date= TimeHelper.getDate(mEvents.get(position-2).getTime());
            time= TimeHelper.getTime(mEvents.get(position-2).getTime());
            holder.tvDate.setText(date);
            holder.tvTime.setText(time);
            holder.tvLocation.setText(mEvents.get(position-2).getVenue());
        }

    }

    @Override
    public int getItemCount() {
        return size;
    }
}
