package org.saarang.instieventsapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.instieventsapp.Adapters.ScoreboardAdapter;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.ScoreboardObject;
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
    //SwipeRefreshLayout swipe;
    private RecyclerView.Adapter adapter;
    //private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ScoreCard> list;
    private List<ScoreboardObject> score;



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_score_board, container, false);

        list = ScoreCard.getScoreBoards(getActivity(), "lit");


        scoreboardrecycle=(RecyclerView) rootView.findViewById(R.id.scoreboardrv);
        layoutManager=new LinearLayoutManager(getActivity());
        scoreboardrecycle.setLayoutManager(layoutManager);

  //      swipe=(SwipeRefreshLayout) rootView.findViewById(R.id.score_swipe);
//        swipe.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        adapter=new ScoreboardAdapter(getActivity(),list, "Jamuna");
        scoreboardrecycle.setAdapter(adapter);
        return rootView;
    }

}
