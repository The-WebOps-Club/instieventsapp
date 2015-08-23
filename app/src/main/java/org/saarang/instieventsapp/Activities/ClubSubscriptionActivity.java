package org.saarang.instieventsapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.saarang.instieventsapp.Adapters.ClubSubscriptionAdapter;
import org.saarang.instieventsapp.Objects.Club;
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
    private Button gotonextpage;
    private List<Club> list;
    private void initialise(){
        list=new ArrayList<>();
        list.add(new Club("Thespian",false));
        list.add(new Club("Quiz",true));
        list.add(new Club("Coding",true));
        list.add(new Club("Drama",false));
        list.add(new Club("Dance", false));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_subscription);
        clubsubscriptionrecycler=(RecyclerView)findViewById(R.id.reSubscription);
        layoutManager=new LinearLayoutManager(this);
        clubsubscriptionrecycler.setLayoutManager(layoutManager);
        initialise();
        adapter=new ClubSubscriptionAdapter(this,list);
        clubsubscriptionrecycler.setAdapter(adapter);
        gotonextpage=(Button) findViewById(R.id.gotomain);
        gotonextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i=new Intent("org.saarang.instieventsapp.Activities.MAINACTIVITY");
                startActivity(i);
            }
        });
    }
}
