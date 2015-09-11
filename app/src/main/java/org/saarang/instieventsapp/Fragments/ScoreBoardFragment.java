package org.saarang.instieventsapp.Fragments;


import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Activities.TrackerApplication;
import org.saarang.instieventsapp.Adapters.ScoreboardAdapter;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.ScoreboardObject;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.SPUtils;
import org.saarang.instieventsapp.Utils.UIUtils;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class ScoreBoardFragment extends Fragment {

    View rootView;
    private static String LOG_TAG = "ScoreBoardFragment";
    RecyclerView scoreboardrecycle;
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
        rootView = inflater.inflate(R.layout.fr_score_board, container, false);

        final RecyclerView.Adapter adapter;

        list = ScoreCard.getScoreBoards(getActivity(), "lit");


        scoreboardrecycle=(RecyclerView) rootView.findViewById(R.id.scoreboardrv);
        layoutManager=new LinearLayoutManager(getActivity());
        scoreboardrecycle.setLayoutManager(layoutManager);

        hostel= UserProfile.getUserHostel(getActivity());
        adapter=new ScoreboardAdapter(getActivity(),list,hostel);
        scoreboardrecycle.setAdapter(adapter);
        return rootView;
    }

}
