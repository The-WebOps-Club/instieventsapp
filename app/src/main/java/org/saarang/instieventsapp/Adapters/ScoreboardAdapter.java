package org.saarang.instieventsapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
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
   // static ArrayList<Integer> scorepos;


    static String highlight;
    Context mContext;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    static int pos=1;

    public ScoreboardAdapter(Context context, ArrayList<ScoreCard> list,String userhostel){
        mList=list;
        mContext=context;
        highlight=userhostel;
       // scorepos=new ArrayList<>();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        private  final TextView hostelname;
        private final TextView points;
        private final TextView position;


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
    public void bind(ScoreCard score,int currpos){
        if (score.getHostel().equals(highlight)) {

            hostelname.setText(score.getHostel());
            points.setText(score.getscore());
            position.setText("" + (currpos));
            hostelname.setBackgroundColor(Color.parseColor("#f8f8fa"));
            points.setBackgroundColor(Color.parseColor("#f8f8fa"));
            position.setBackgroundColor(Color.parseColor("#f8f8fa"));
            /*viewholder.hostelname.setTextColor(Color.parseColor("#0057e7"));
            viewholder.points.setTextColor(Color.parseColor("#0057e7"));
            viewholder.position.setTextColor(Color.parseColor("#0057e7"));*/
            hostelname.setTypeface(null, Typeface.BOLD);
            points.setTypeface(null, Typeface.BOLD);
            position.setTypeface(null, Typeface.BOLD);
        }
        else {

            hostelname.setText(score.getHostel());
            points.setText(score.getscore());
            position.setText("" + (currpos));


        }
       /* if(currpos==pos){
            scorepos.add(currpos);
        }*/
    }

    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_score, viewGroup, false);
        Viewholder pvh = new Viewholder(v);
        return pvh;

    }



    @Override
    public void onBindViewHolder(Viewholder viewholder, int i) {

        final ScoreCard score=mList.get(i);
        //int scoreposarr[]=new int[scorepos.size()];
        //scoreposarr=scorepos.toArray(scoreposarr);
        //if(scorepos.get(i)==null) {

            if (i > 0) {
                final ScoreCard previous = mList.get(i - 1);
                if (!score.getscore().equals(previous.getscore()))
                    pos = pos + 1;
            }
            viewholder.bind(score,1 );



    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
