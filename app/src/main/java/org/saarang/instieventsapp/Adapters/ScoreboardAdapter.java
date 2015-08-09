package org.saarang.instieventsapp.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saarang.instieventsapp.Objects.ScoreboardObject;
import org.saarang.instieventsapp.R;

import java.util.List;

/**
 * Created by kiran on 8/8/15.
 */
public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.Viewholder> {

    List<ScoreboardObject> mList;
    String highlight;
    int check=0;
    public ScoreboardAdapter(List<ScoreboardObject> list,String userhostel){
        mList=list;
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
        if(check==0){
        if (mList.get(i).getHostelname() == highlight) {
            viewholder.hostelname.setText(mList.get(i).getHostelname());
            viewholder.points.setText(mList.get(i).getPoints());
            viewholder.position.setText(mList.get(i).getPosition());
            viewholder.hostelname.setBackgroundColor(Color.parseColor("#c0c0c0"));
            viewholder.points.setBackgroundColor(Color.parseColor("#c0c0c0"));
            viewholder.position.setBackgroundColor(Color.parseColor("#c0c0c0"));
            viewholder.hostelname.setTextColor(Color.parseColor("#0057e7"));
            viewholder.points.setTextColor(Color.parseColor("#0057e7"));
            viewholder.position.setTextColor(Color.parseColor("#0057e7"));
            check=1;
        } else {

            viewholder.hostelname.setText(mList.get(i).getHostelname());
            viewholder.points.setText(mList.get(i).getPoints());
            viewholder.position.setText(mList.get(i).getPosition());
        }}
        else
        {
            viewholder.hostelname.setText(mList.get(i).getHostelname());
            viewholder.points.setText(mList.get(i).getPoints());
            viewholder.position.setText(mList.get(i).getPosition());
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
