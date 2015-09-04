package org.saarang.instieventsapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.instieventsapp.Adapters.ScheduleAdapter;
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

    View rootView;
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
