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

    public ScoreboardAdapter(Context context, ArrayList<ScoreCard> list,String userhostel){
        mList=list;
        mContext=context;
        highlight=userhostel;
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

    @Override
    public void onBindViewHolder(Viewholder viewholder, int i) {

        if (mList.get(i).getHostel() == highlight) {
            viewholder.hostelname.setText(mList.get(i).getHostel());
            viewholder.points.setText(mList.get(i).getscore());
            viewholder.position.setText("" + (i + 1));
            viewholder.hostelname.setBackgroundColor(Color.parseColor("#f8f8fa"));
            viewholder.points.setBackgroundColor(Color.parseColor("#f8f8fa"));
            viewholder.position.setBackgroundColor(Color.parseColor("#f8f8fa"));
            /*viewholder.hostelname.setTextColor(Color.parseColor("#0057e7"));
            viewholder.points.setTextColor(Color.parseColor("#0057e7"));
            viewholder.position.setTextColor(Color.parseColor("#0057e7"));*/
            viewholder.hostelname.setTypeface(null, Typeface.BOLD);
            viewholder.points.setTypeface(null, Typeface.BOLD);
            viewholder.position.setTypeface(null, Typeface.BOLD);
        }
         else {

            viewholder.hostelname.setText(mList.get(i).getHostel());
            viewholder.points.setText(mList.get(i).getscore());
            viewholder.position.setText("" + (i + 1));
        }

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
