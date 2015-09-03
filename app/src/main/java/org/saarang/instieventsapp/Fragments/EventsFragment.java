package org.saarang.instieventsapp.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.instieventsapp.Adapters.EventsAdapter;
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


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_events_feed, container, false);

        //Get recycler view and linear layout manager
        rvEvents = (RecyclerView)rootView.findViewById(R.id.rvEvents);
        layoutManager = new LinearLayoutManager(getActivity());

        //set the recycler view to use the linear layout manager
        rvEvents.setLayoutManager(layoutManager);

        // Load events from Database
        events = Event.getAllRelevantEvents(getActivity());

        //initialize events feed adapter
        EventsAdapter eventsAdapter = new EventsAdapter(getActivity(), events);

        //Use the events feed adapter
        rvEvents.setAdapter(eventsAdapter);



        return rootView;
    }
}

