package org.saarang.instieventsapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.saarang.instieventsapp.Adapters.ClubDetailAdapter;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Utils.URLConstants;

import java.util.ArrayList;

public class ClubDetailActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Event> mEvent;
    RecyclerView recyclerView;
    Context mContext=ClubDetailActivity.this;
    private static String LOG_TAG = "ClubDetailActivity";
    @Override
    protected void onStart() {
        super.onStart();
        Tracker tracker=((TrackerApplication) getApplication()).getTracker();
        tracker.setScreenName("ClubDetailActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_club_detail);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview2);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton floatB;
        floatB=(FloatingActionButton)findViewById(R.id.floatB);

        Bundle getclubid=getIntent().getExtras();
        String clubId;
        clubId=getclubid.getString(Club.KEY_ROWID);
        Club club= Club.getAClub(mContext, clubId);
        if(club.getIsSubscribed()){

            floatB.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.floating_button_sub));
        }
        else{
            floatB.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.floating_button_notsub));
        }


        mEvent=Event.getClubEvents(mContext,club.getId());
        adapter = new ClubDetailAdapter(this,club,mEvent);
        recyclerView.setAdapter(adapter);





        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarClubDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);
        /*toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.ic_arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(club.getName());
        //collapsingToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this)
                .load(URLConstants.URL_CLUB_LOGO + club.getLogo())
                .placeholder(R.drawable.events_bg)
                .into(imageView);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_club_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    //    if (item.getItemId()==R.id.action_aboutus){startActivity(new Intent(this, AboutUsActivity.class));}
        return super.onOptionsItemSelected(item);
    }

}
