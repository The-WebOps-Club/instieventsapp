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
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Activities.TrackerApplication;
import org.saarang.instieventsapp.Adapters.ScheduleAdapter;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.SPUtils;
import org.saarang.instieventsapp.Utils.UIUtils;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class CalenderFragment extends Fragment {


    public CalenderFragment(){

    }

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Event> events;
    String LOG_TAG = "CalenderFragment";
    SwipeRefreshLayout swipeRefreshLayout;
    JSONArray Events,jScoreBoards,jScoreCards,Clubs;
    JSONObject jScoreBoard,jClub;
    Event event;
    Club club;
    String category;
    String scoreBoardId;
    Button btRefresh;

    View rootView;

    @Override
    public void onStart() {
        super.onStart();
        Tracker tracker=((TrackerApplication) getActivity().getApplication()).getTracker();
        tracker.setScreenName("CalenderFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_calender, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.scoreboardrv);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        events = Event.getUpcomingEvents(getActivity());
        adapter = new ScheduleAdapter(getActivity(), events);
        recyclerView.setAdapter(adapter);


        return rootView;
    }

}
