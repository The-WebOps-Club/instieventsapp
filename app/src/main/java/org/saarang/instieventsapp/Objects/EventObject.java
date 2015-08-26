package org.saarang.instieventsapp.Objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Helper.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Seetharaman on 22-08-2015.
 */
public class EventObject {
    String id, name, time, venue, description, category, club, result, coords, createdOn, updatedOn;
    boolean active, isLitSocEvent;


    public static String TABLE_NAME = "Events";
    public static String KEY_ROWID = "_id";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_TIME = "time";
    public static String COLUMN_VENUE = "venue";
    public static String COLUMN_DESCRIPTION = "description";
    public static String COLUMN_CATEGORY = "category";
    public static String COLUMN_CLUB = "club";
    public static String COLUMN_RESULT = "result";
    public static String COLUMN_COORDS = "coords";
    public static String COLUMN_CREATEDON = "createdOn";
    public static String COLUMN_UPDATEDON = "updatedOn";
    public static String COLUMN_ACTIVE = "active";
    public static String COLUMN_ISLITSOCEVENT = "isLitSocEvent";

    public static String EVENTOBJECT_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            KEY_ROWID + " INTEGER " + " PRIMARY KEY , " +
            COLUMN_NAME + " TEXT " + " NOT NULL , " +
            COLUMN_TIME + " TEXT  , " +
            COLUMN_VENUE + " TEXT  , " +
            COLUMN_DESCRIPTION + " TEXT , " +
            COLUMN_CATEGORY + " TEXT  , " +
            COLUMN_CLUB + " TEXT  , " +
            COLUMN_RESULT + " TEXT  , " +
            COLUMN_COORDS + " TEXT  , " +
            COLUMN_CREATEDON + " NUMBER  , " +
            COLUMN_UPDATEDON + " TEXT , " +
            COLUMN_ACTIVE + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE ,  " +
            COLUMN_ISLITSOCEVENT + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE  " +
            " );";

    public static String[] columns = {KEY_ROWID, COLUMN_NAME, COLUMN_TIME, COLUMN_VENUE, COLUMN_DESCRIPTION,
            COLUMN_CATEGORY, COLUMN_CLUB, COLUMN_RESULT, COLUMN_COORDS, COLUMN_CREATEDON, COLUMN_UPDATEDON, COLUMN_ACTIVE, COLUMN_ISLITSOCEVENT};

    public EventObject(String id, String name, String time, String description, String venue, String category,
                       String club, String result, String coords, String createdOn, String updatedOn, boolean active,
                       boolean isLitSocEvent) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.description = description;
        this.result = result;
        this.active = active;
        this.isLitSocEvent = isLitSocEvent;
        this.club = club;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.coords = coords;
        this.category = category;

    }


    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }

    public String getResult() {
        return result;
    }


    public boolean isActive() {
        return active;
    }

    public boolean isLitSocEvent() {
        return isLitSocEvent;
    }

    public String getClub() {
        return club;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long SaveEvent(Context context){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TIME,time);
        cv.put(COLUMN_DESCRIPTION,description);
        cv.put(COLUMN_VENUE,venue);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_CLUB,club);
        cv.put(COLUMN_RESULT,result);
        cv.put(COLUMN_COORDS,coords);
        cv.put(COLUMN_CREATEDON,createdOn);
        cv.put(COLUMN_UPDATEDON,updatedOn);
        cv.put(COLUMN_ACTIVE, active?"1":"0" );
        cv.put(COLUMN_ISLITSOCEVENT, isLitSocEvent?"1":"0" );

        DatabaseHelper dh = new DatabaseHelper(context);

        return dh.addEvent(cv);
    }

    public static void SaveEvents(Context context, JSONArray jsonArray) {

        Gson gson = new Gson();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject post = jsonArray.getJSONObject(i);
                EventObject eo = new EventObject(post.getString("_id"), post.getString("name"),
                        post.getString("time"), post.getString("description"), post.getString("venue"), post.getString("category"), post.getString("club"),
                        post.getJSONArray("result").toString(), post.getJSONArray("coords").toString(),
                        post.getString("createdOn"), post.getString("updatedOn"),
                        post.getBoolean("active"), post.getBoolean("isLitSocEvent")
                );

                // TODO eo.SaveEvent(context);




            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
    }

    public static ArrayList<EventObject> getArrayList(Cursor c){
        ArrayList<EventObject> arrayList = new ArrayList<>();
        Gson gson = new Gson();
        while ( c.moveToNext() ){
            EventObject event = new EventObject(c.getString(1), c.getString(2),c.getString(3),c.getString(4),
                    c.getString(5),c.getString(6), c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11),
                    (c.getString(12)=="1")?true:false, (c.getString(13)=="1")?true:false);
            arrayList.add(event);
        }
        return arrayList;
    }
}
