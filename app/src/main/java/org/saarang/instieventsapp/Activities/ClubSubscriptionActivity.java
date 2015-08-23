package org.saarang.instieventsapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.saarang.instieventsapp.Adapters.ClubSubscriptionAdapter;
import org.saarang.instieventsapp.Objects.ClubSubscriptionObject;
import org.saarang.instieventsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiran on 23/8/15.
 */
public class ClubSubscriptionActivity extends Activity {


    RecyclerView clubsubscriptionrecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ClubSubscriptionObject> list;
    private void initialise(){
        list=new ArrayList<>();
        list.add(new ClubSubscriptionObject("Thespian",false));
        list.add(new ClubSubscriptionObject("Quiz",true));
        list.add(new ClubSubscriptionObject("Coding",true));
        list.add(new ClubSubscriptionObject("Drama",false));
        list.add(new ClubSubscriptionObject("Dance", false));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_subscription);
        clubsubscriptionrecycler=(RecyclerView)findViewById(R.id.club_subscription_recyclerview);
        layoutManager=new LinearLayoutManager(this);
        clubsubscriptionrecycler.setLayoutManager(layoutManager);
        initialise();
        adapter=new ClubSubscriptionAdapter(this,list);
        clubsubscriptionrecycler.setAdapter(adapter);
    }
}
