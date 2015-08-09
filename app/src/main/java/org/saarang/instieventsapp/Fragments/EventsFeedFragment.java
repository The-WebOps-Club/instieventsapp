package org.saarang.instieventsapp.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.instieventsapp.Adapters.EventsFeedAdapter;
import org.saarang.instieventsapp.R;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class EventsFeedFragment extends Fragment {

    View rootView;
    LinearLayoutManager layoutManager;
    RecyclerView rvEvents;
    String[] headings = {"Thespian", "Quiz", "Coding", "Dance", "Drama"};


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_events_feed, container, false);

        //Get recycler view and linear layout manager
        rvEvents = (RecyclerView)rootView.findViewById(R.id.rvEvents);
        layoutManager = new LinearLayoutManager(getActivity());

        //set the recycler view to use the linear layout manager
        rvEvents.setLayoutManager(layoutManager);

        //initialize events feed adapter
        EventsFeedAdapter eventsAdapter = new EventsFeedAdapter(getActivity(), headings);

        //Use the events feed adapter
        rvEvents.setAdapter(eventsAdapter);



        return rootView;
    }
}

