package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.R;

import java.util.ArrayList;

/**
 * Created by kiran on 8/8/15.
 */
public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.Viewholder> {

    ArrayList<ScoreCard> mList;

    String highlight;
    Context mContext;
    int position = 0;
    int score = 0;
    int pos=1;


    public ScoreboardAdapter(Context context, ArrayList<ScoreCard> list,String userhostel){
        mList=list;
        mContext=context;
        highlight=userhostel;
        for (int i = 0; i < mList.size(); i++){
            if (Integer.parseInt(mList.get(i).getscore()) == score){
                mList.get(i).setPosition(position);
            } else {
                position = i + 1;
                mList.get(i).setPosition(position);
                score = Integer.parseInt(mList.get(i).getscore());
            }
        }
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

         TextView hostelname;
         TextView points;
         TextView position;

        public Viewholder(View itemView) {
            super(itemView);
            hostelname=(TextView)itemView.findViewById(R.id.scoreboard_hostelname_body);
            points=(TextView)itemView.findViewById(R.id.scoreboard_points_body);
            position=(TextView)itemView.findViewById(R.id.scoreboard_position_body);

        }
        // Add a constructor later
   /* public void Updateadapter(List<ScoreboardObject> obj){
        mList=obj;
    }*/



    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_score, viewGroup, false);
        Viewholder pvh = new Viewholder(v);
        return pvh;

    }

    public void bind(ScoreCard score){

    }

    @Override
    public void onBindViewHolder(Viewholder viewholder, int i) {

        viewholder.position.setText(" " + mList.get(i).getPosition());
        if (mList.get(i).getHostel().equals(highlight)) {
            viewholder.hostelname.setText(mList.get(i).getHostel());
            viewholder.points.setText(mList.get(i).getscore());
            viewholder.hostelname.setBackgroundColor(Color.parseColor("#f8f8fa"));
            viewholder.points.setBackgroundColor(Color.parseColor("#f8f8fa"));
            viewholder.position.setBackgroundColor(Color.parseColor("#f8f8fa"));
            viewholder.hostelname.setTypeface(null, Typeface.BOLD);
            viewholder.points.setTypeface(null, Typeface.BOLD);
            viewholder.position.setTypeface(null, Typeface.BOLD);
        }
        else {

            viewholder.hostelname.setText(mList.get(i).getHostel());
            viewholder.points.setText(mList.get(i).getscore());
            viewholder.hostelname.setBackgroundColor(Color.parseColor("#ffffff"));
            viewholder.points.setBackgroundColor(Color.parseColor("#ffffff"));
            viewholder.position.setBackgroundColor(Color.parseColor("#ffffff"));
            viewholder.hostelname.setTypeface(null, Typeface.NORMAL);
            viewholder.points.setTypeface(null, Typeface.NORMAL);
            viewholder.position.setTypeface(null, Typeface.NORMAL);

//
        }

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}