package org.saarang.instieventsapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.instieventsapp.Adapters.ClubsAdapter;
import org.saarang.instieventsapp.R;

//import org.saarang.instieventsapp.R;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class ClubsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public ClubsFragment() {
    }

    View rootView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_clubs, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new ClubsAdapter(getActivity());
        recyclerView.setAdapter(adapter);



        return rootView;
    }

    @Override
    public void onRefresh() {

    }
}
