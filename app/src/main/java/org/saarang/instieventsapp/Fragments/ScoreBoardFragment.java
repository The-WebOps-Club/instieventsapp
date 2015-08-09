package org.saarang.instieventsapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.instieventsapp.Adapters.ScoreboardAdapter;
import org.saarang.instieventsapp.Objects.ScoreboardObject;
import org.saarang.instieventsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class ScoreBoardFragment extends Fragment {

    View rootView;

    RecyclerView scoreboardrecycle;
    private RecyclerView.Adapter adapter;
    //private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager;
    private List<ScoreboardObject> score;
    private void initialise(){
        score=new ArrayList<>();
        score.add(new ScoreboardObject("Pampa","250","1"));
        score.add(new ScoreboardObject("Mahanadhi","200","2"));
        score.add(new ScoreboardObject("Thamarabharani","150","3"));
        score.add(new ScoreboardObject("Sindhu","100","4"));
        score.add(new ScoreboardObject("Ganga","250","1"));
        score.add(new ScoreboardObject("Jamuna","200","2"));
        score.add(new ScoreboardObject("Mandakini","150","3"));
        score.add(new ScoreboardObject("Godavery","100","4"));
        score.add(new ScoreboardObject("Narmada","250","1"));
        score.add(new ScoreboardObject("Thunga","200","2"));
        score.add(new ScoreboardObject("Bhadra","150","3"));
        score.add(new ScoreboardObject("Alakananda","100","4"));
        score.add(new ScoreboardObject("Saraswathy","250","1"));
        score.add(new ScoreboardObject("Brahmaputra","200","2"));
        score.add(new ScoreboardObject("Tapti","150","3"));
        score.add(new ScoreboardObject("Sarawati","100","4"));
        score.add(new ScoreboardObject("Sarayu","250","1"));
        score.add(new ScoreboardObject("Sabarmati","200","2"));
        score.add(new ScoreboardObject("Krishna","150","3"));
        score.add(new ScoreboardObject("Cauvery","100","4"));
    }
    //int i;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_score_board, container, false);


        scoreboardrecycle=(RecyclerView) rootView.findViewById(R.id.scoreboardrv);
        layoutManager=new LinearLayoutManager(getActivity());
        scoreboardrecycle.setLayoutManager(layoutManager);
        initialise();
        adapter=new ScoreboardAdapter(score,"Mandakini");
        scoreboardrecycle.setAdapter(adapter);
        return rootView;
    }

}
