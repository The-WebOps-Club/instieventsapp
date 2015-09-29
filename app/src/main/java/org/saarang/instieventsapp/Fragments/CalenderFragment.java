package org.saarang.instieventsapp.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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

import org.json.JSONArray;
import org.json.JSONObject;
import org.saarang.instieventsapp.Activities.MainActivity;
import org.saarang.instieventsapp.Activities.TrackerApplication;
import org.saarang.instieventsapp.Adapters.ScheduleAdapter;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;

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

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(MainActivity.BROADCAST_UPDATE));

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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String code = intent.getStringExtra("code");
            Log.d(LOG_TAG, "Message  recieved " + code );
            if (code.equals("events")){
                events = Event.getAllEvents(getActivity());
                adapter = new ScheduleAdapter(getActivity(), events);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        }
    };

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

}
