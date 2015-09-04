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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.R;

/**
 * Created by kevin selva prasanna on 11-Aug-15.
 */
public class ClubDetailAdapter extends RecyclerView.Adapter<ClubDetailAdapter.ViewHolder> {

    Context mContext;
    Club mClub;
    int size=8;
    // ArrayList<Club> mList;
    public ClubDetailAdapter(Context context,Club club) {
        mContext = context;
        mClub=club;
        //  mList=Club.getAllClubs(mContext);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView tv,tv2,description,convenor;
        ImageView call,mail;
        LinearLayout convenorlist,parentlayout;
        ViewGroup mLinearLayout;
        public ViewHolder(View view) {
            super(view);
            tv = (TextView)view.findViewById(R.id.tv);
            tv2 = (TextView)view.findViewById(R.id.tv2);
            description=(TextView)view.findViewById(R.id.tv2);
            //convenorlist=(LinearLayout)view.findViewById(R.id.convenorlist);
            // convenor=(TextView)view.findViewById(R.id.tvConvenor);
            //call=(ImageView)view.findViewById(R.id.ivCall);
            //mail=(ImageView)view.findViewById(R.id.ivMail);
            mLinearLayout = (ViewGroup) view.findViewById(R.id.convenorlist1);
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
        if(position==1){
            final Club.Convenor[] clubconvenors=mClub.getConvenors();
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
        if(position==0){
            holder.tv.setText("Description");
            holder.tv2.setText(mClub.getDescription());

        }

    }

    @Override
    public int getItemCount() {
        return size;
    }
}