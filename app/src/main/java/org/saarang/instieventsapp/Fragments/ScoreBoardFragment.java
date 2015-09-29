package org.saarang.instieventsapp.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONObject;
import org.saarang.instieventsapp.Activities.MainActivity;
import org.saarang.instieventsapp.Activities.TrackerApplication;
import org.saarang.instieventsapp.Adapters.ScoreboardAdapter;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.ScoreboardObject;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class ScoreBoardFragment extends Fragment {

    View rootView;
    private static String LOG_TAG = "ScoreBoardFragment";
    RecyclerView scoreboardrecycle;
    RecyclerView.Adapter adapter;
    //SwipeRefreshLayout swipe;

    //private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ScoreCard> list;
    private List<ScoreboardObject> score;
    String hostel;
    JSONArray Events,jScoreBoards,jScoreCards,Clubs;
    JSONObject jScoreBoard,jClub;
    Event event;
    Club club;
    String category;
    String scoreBoardId;

    @Override
    public void onStart() {
        super.onStart();
        Tracker tracker=((TrackerApplication) getActivity().getApplication()).getTracker();
        tracker.setScreenName("ScoreboardFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(MainActivity.BROADCAST_UPDATE));

        rootView = inflater.inflate(R.layout.fr_score_board, container, false);


        list = ScoreCard.getScoreBoards(getActivity(), "lit");


        scoreboardrecycle=(RecyclerView) rootView.findViewById(R.id.scoreboardrv);
        layoutManager=new LinearLayoutManager(getActivity());
        scoreboardrecycle.setLayoutManager(layoutManager);

        hostel= UserProfile.getUserHostel(getActivity());
        adapter=new ScoreboardAdapter(getActivity(),list,hostel);
        scoreboardrecycle.setAdapter(adapter);
        return rootView;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String code = intent.getStringExtra("code");
            Log.d(LOG_TAG, "Message  recieved " + code);
            if (code.equals("scoreboards")){
                list = ScoreCard.getScoreBoards(getActivity(), "lit");
                adapter=new ScoreboardAdapter(getActivity(),list,hostel);
                scoreboardrecycle.setAdapter(adapter);
            }
        }
    };

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

}
