package org.saarang.instieventsapp.Fragments;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Adapters.EventsAdapter;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.spUtils;
import org.saarang.instieventsapp.Utils.UIUtils;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class EventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

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





    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_events_feed, container, false);

        //Get recycler view and linear layout manager
        rvEvents = (RecyclerView)rootView.findViewById(R.id.rvEvents);
        layoutManager = new LinearLayoutManager(getActivity());

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);


        //set the recycler view to use the linear layout manager
        rvEvents.setLayoutManager(layoutManager);

        // Load events from Database
       // events = Event.getAllRelevantEvents(getActivity());
        events = Event.getAllEvents(getActivity());

        //initialize events feed adapter
        EventsAdapter eventsAdapter = new EventsAdapter(getActivity(), events);

        //Use the events feed adapter
        rvEvents.setAdapter(eventsAdapter);



        return rootView;
    }

    @Override
    public void onRefresh() {
        RefreshRequest refreshrequest = new RefreshRequest();
        if (Connectivity.isNetworkAvailable(getActivity())) {
            refreshrequest.execute();
        } else {
            UIUtils.showSnackBar(rootView, getResources().getString(R.string.error_connection));
        }

        swipeRefreshLayout.setRefreshing(false);
    }

   private class RefreshRequest extends AsyncTask<String, Void, Void> {
        ArrayList<PostParam> params = new ArrayList<>();
        int status;
       Gson gson = new Gson();


        @Override
        protected Void doInBackground(String... strings) {
            params.add(new PostParam("time", spUtils.getLastUpdateDate(getActivity())));
            JSONObject json = PostRequest.execute(URLConstants.URL_REFRESH, params,
                    UserProfile.getUserToken(getActivity()));
            try {
                status = json.getInt("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(status==200){
                spUtils.setLastUpdateDate(getActivity());
            try {
                Log.d(LOG_TAG, "Status:" + String.valueOf(status));
                Log.d(LOG_TAG, json.toString());
                Events = json.getJSONArray("events");
                for (int i=0; i<Events.length(); i++){
                    JSONObject json1  = Events.getJSONObject(i);
                    event = new Event(json1);
                    event.saveEvent(getActivity());
                }
                jScoreBoards = json.getJSONArray("scoreboard");
                for (int j =0; j< jScoreBoards.length(); j++){
                    jScoreBoard = jScoreBoards.getJSONObject(j);
                    category = jScoreBoard.getString("category");
                    jScoreCards = jScoreBoard.getJSONArray("scorecard");
                    scoreBoardId = jScoreBoard.getString("_id");

                    for (int k = 0; k< jScoreCards.length(); k++){
                        jScoreBoard = jScoreCards.getJSONObject(k);
                        ContentValues cv = ScoreCard.getCV(category,
                                jScoreBoard.getJSONObject("hostel").getString("name"),
                                jScoreBoard.getInt("score"), scoreBoardId + jScoreBoard.getString("_id"));
                        Log.d(LOG_TAG,"cat:" + category + "score:" + jScoreBoard.getInt("score") + "id" + scoreBoardId + jScoreBoard.getString("_id")+ "hostel"+ jScoreBoard.getJSONObject("hostel").getString("name") );
                        ScoreCard.saveScoreCard(getActivity(), cv);
                    }
                }
                Clubs = json.getJSONArray("clubs");
                for (int i = 0; i < Clubs.length(); i++) {
                    jClub = Clubs.getJSONObject(i);
                    club = gson.fromJson(jClub.toString(), Club.class);
                    Log.d(LOG_TAG, jClub.getString("name"));
                    DatabaseHelper data = new DatabaseHelper(getActivity());
                    data.addClub(club.getCV());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }}
            return null;
        }
    }
}

