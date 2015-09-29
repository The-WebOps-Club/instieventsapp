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

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONObject;
import org.saarang.instieventsapp.Activities.MainActivity;
import org.saarang.instieventsapp.Activities.TrackerApplication;
import org.saarang.instieventsapp.Adapters.EventsAdapter;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;

import java.util.ArrayList;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class EventsFragment extends Fragment {

    View rootView;
    LinearLayoutManager layoutManager;
    RecyclerView rvEvents;
    ArrayList<Event> events;
    String LOG_TAG = "EventsFragment";
    SwipeRefreshLayout swipeRefreshLayout;
    JSONArray Events,jScoreBoards,jScoreCards,Clubs;
    JSONObject jScoreBoard,jClub;
    Event event;
    Club club;
    String category;
    String scoreBoardId;
    EventsAdapter eventsAdapter;


    @Override
    public void onStart() {
        super.onStart();
        Tracker tracker=((TrackerApplication) getActivity().getApplication()).getTracker();
        tracker.setScreenName("EventFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(MainActivity.BROADCAST_UPDATE));

        rootView = inflater.inflate(R.layout.fr_events_feed, container, false);

        //Get recycler view and linear layout manager
        rvEvents = (RecyclerView)rootView.findViewById(R.id.rvEvents);
        layoutManager = new LinearLayoutManager(getActivity());

        //set the recycler view to use the linear layout manager
        rvEvents.setLayoutManager(layoutManager);

        // Load events from Database
       // events = Event.getAllRelevantEvents(getActivity());
        events = Event.getAllEvents(getActivity());

        //initialize events feed adapter
        eventsAdapter = new EventsAdapter(getActivity(), events);

        //Use the events feed adapter
        rvEvents.setAdapter(eventsAdapter);



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
                eventsAdapter = new EventsAdapter(getActivity(), events);

                eventsAdapter.notifyDataSetChanged();
                rvEvents.setAdapter(eventsAdapter);


            }
        }
    };

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

}

