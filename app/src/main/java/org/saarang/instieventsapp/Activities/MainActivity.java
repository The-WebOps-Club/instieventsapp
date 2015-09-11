package org.saarang.instieventsapp.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Fragments.CalenderFragment;
import org.saarang.instieventsapp.Fragments.ClubsFragment;
import org.saarang.instieventsapp.Fragments.EventsFragment;
import org.saarang.instieventsapp.Fragments.ScoreBoardFragment;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;
import org.saarang.instieventsapp.Objects.UserProfile;
import org.saarang.instieventsapp.R;
import org.saarang.instieventsapp.Services.IE_RegistrationIntentService;
import org.saarang.instieventsapp.Utils.SPUtils;
import org.saarang.instieventsapp.Utils.UIUtils;
import org.saarang.instieventsapp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //    private DrawerLayout mDrawerLayout;
//    private SwipeRefreshLayout swipeContainer;
    JSONArray Events, jScoreBoards, jScoreCards, Clubs;
    JSONObject jScoreBoard, jClub;
    Event event;
    Club club;
    String category;
    String scoreBoardId;
    RefreshRequest refreshrequest = new RefreshRequest();
    ProgressDialog pDialog;

    private static final String LOG_TAG = "MainActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    int userState;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userState = UserProfile.getUserState(this);
        redirectUser(userState);

        setContentView(R.layout.ac_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


       /* swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //fetchTimelineAsync(0);
            }
        });*/


//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        if (navigationView != null) {
//            setupDrawerContent(navigationView);
//        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3F51B5")));
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, IE_RegistrationIntentService.class);
            startService(intent);
        }
    }

    private void redirectUser(int userState) {
        switch (userState) {
            case 1:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                Intent intent_2 = new Intent(this, ClubSubscriptionActivity.class);
                startActivity(intent_2);
                finish();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuRefresh) {
            refresh(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
    //            case android.R.id.home:
    //                mDrawerLayout.openDrawer(GravityCompat.START);
    //                return true;
            }
            return super.onOptionsItemSelected(item);
        }*/
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("tag", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new EventsFragment(), "Events ");
        adapter.addFragment(new CalenderFragment(), "Calender");
        adapter.addFragment(new ScoreBoardFragment(), "ScoreBoard");
        adapter.addFragment(new ClubsFragment(), "Clubs");
        viewPager.setAdapter(adapter);
    }

//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        menuItem.setChecked(true);
//                        mDrawerLayout.closeDrawers();
//                        return true;
//                    }
//                });
//    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }


    }


    private class RefreshRequest extends AsyncTask<String, Void, Void> {
        ArrayList<PostParam> params = new ArrayList<>();
        int status;
        Gson gson = new Gson();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Refreshing ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            params.add(new PostParam("time", SPUtils.getLastUpdateDate(MainActivity.this)));
            JSONObject json = PostRequest.execute(URLConstants.URL_REFRESH, params,
                    UserProfile.getUserToken(MainActivity.this));
            try {
                status = json.getInt("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status == 200) {
                SPUtils.setLastUpdateDate(MainActivity.this);
                try {
                    Log.d(LOG_TAG, "Status:" + String.valueOf(status));
                    Log.d(LOG_TAG, json.toString());
                    Events = json.getJSONArray("events");
                    for (int i = 0; i < Events.length(); i++) {
                        JSONObject json1 = Events.getJSONObject(i);
                        event = new Event(json1);
                        event.saveEvent(MainActivity.this);
                    }
                    jScoreBoards = json.getJSONArray("scoreboard");
                    for (int j = 0; j < jScoreBoards.length(); j++) {
                        jScoreBoard = jScoreBoards.getJSONObject(j);
                        category = jScoreBoard.getString("category");
                        jScoreCards = jScoreBoard.getJSONArray("scorecard");
                        scoreBoardId = jScoreBoard.getString("_id");

                        for (int k = 0; k < jScoreCards.length(); k++) {
                            jScoreBoard = jScoreCards.getJSONObject(k);
                            ContentValues cv = ScoreCard.getCV(category,
                                    jScoreBoard.getJSONObject("hostel").getString("name"),
                                    jScoreBoard.getInt("score"), scoreBoardId + jScoreBoard.getString("_id"));
                            Log.d(LOG_TAG, "cat:" + category + "score:" + jScoreBoard.getInt("score") + "id" + scoreBoardId + jScoreBoard.getString("_id") + "hostel" + jScoreBoard.getJSONObject("hostel").getString("name"));
                            ScoreCard.saveScoreCard(MainActivity.this, cv);
                        }
                    }
                    Clubs = json.getJSONArray("clubs");
                    for (int i = 0; i < Clubs.length(); i++) {
                        jClub = Clubs.getJSONObject(i);
                        club = gson.fromJson(jClub.toString(), Club.class);
                        Log.d(LOG_TAG, jClub.getString("name"));
                        DatabaseHelper data = new DatabaseHelper(MainActivity.this);
                        data.addClub(club.getCV());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
        }
    }

    public void refresh(MenuItem item) {
        // View for displaying SnackBar
        View llSnackBar = findViewById(R.id.drawer_layout);
        if (Connectivity.isNetworkAvailable(MainActivity.this)) {
            new RefreshRequest().execute();

        } else {
            UIUtils.showSnackBar(llSnackBar, getResources().getString(R.string.error_connection));
        }

    }
}