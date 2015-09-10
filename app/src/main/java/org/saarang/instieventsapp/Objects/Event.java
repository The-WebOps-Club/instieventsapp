package org.saarang.instieventsapp.Objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Helper.DatabaseHelper;
import org.saarang.saarangsdk.Helpers.TimeHelper;

import java.util.ArrayList;

/**
 * Created by Seetharaman on 22-08-2015.
 */


public class Event {

    String _id, name, time, venue, description, category, club, result, coords, createdOn, updatedOn;
    boolean active, isLitSocEvent, isSubscribed;
    public static final long TIMEZONE_CORRECTION = 330 * 60 * 1000;

    public class Convenor{

        String name;
        String phoneNumber;
        String email;

        public String getConName(){
            return name;
        }

        public String getConPhone(){
            return phoneNumber;
        }

        public String getConEmail(){
            return email;
        }

    }

    Convenor[] convenors;

    public static String TABLE_NAME = "Events";

    public static String KEY_ROWID = "_id";
    public static String COLUMN_EVENTID = "eventId";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_TIME = "time";
    public static String COLUMN_TIMESTAMP = "timeStamp";
    public static String COLUMN_VENUE = "venue";
    public static String COLUMN_DESCRIPTION = "description";
    public static String COLUMN_CATEGORY = "category";
    public static String COLUMN_CLUB = "club";
    public static String COLUMN_RESULT = "result";
    public static String COLUMN_COORDS = "coords";
    public static String COLUMN_CREATEDON = "createdOn";
    public static String COLUMN_UPDATEDON = "updatedOn";
    public static String COLUMN_ISLITSOCEVENT = "isLitSocEvent";
    public static String COLUMN_ISRELEVANT = "isRelevant";

    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            KEY_ROWID + " INTEGER " + " PRIMARY KEY , " +
            COLUMN_EVENTID + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE , " +
            COLUMN_NAME + " TEXT " + " NOT NULL , " +
            COLUMN_TIME + " TEXT  , " +
            COLUMN_TIMESTAMP + " NUMBER  , " +
            COLUMN_VENUE + " TEXT  , " +
            COLUMN_DESCRIPTION + " TEXT , " +
            COLUMN_CATEGORY + " TEXT  , " +
            COLUMN_CLUB + " TEXT  , " +
            COLUMN_RESULT + " TEXT  , " +
            COLUMN_COORDS + " TEXT  , " +
            COLUMN_CREATEDON + " TEXT  , " +
            COLUMN_UPDATEDON + " TEXT , " +
            COLUMN_ISLITSOCEVENT + " NUMBER , " +
            COLUMN_ISRELEVANT + " NUMBER " +
            " );";

    public static String[] columns = {KEY_ROWID, COLUMN_EVENTID, COLUMN_NAME, COLUMN_TIME, COLUMN_VENUE, COLUMN_DESCRIPTION,
            COLUMN_CATEGORY, COLUMN_CLUB, COLUMN_RESULT, COLUMN_COORDS, COLUMN_CREATEDON, COLUMN_UPDATEDON, COLUMN_ISLITSOCEVENT, COLUMN_TIMESTAMP};

    public Event(){

    }
    public Event(String id, String name, String time, String venue, String description, String category,
                 String club, String result, Convenor[] conveners, String createdOn, String updatedOn,
                 boolean isLitSocEvent) {

        this._id = id;
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.description = description;
        this.result = result;
        this.isLitSocEvent = isLitSocEvent;
        this.club = club;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.coords = coords;
        this.category = category;
        this.convenors = conveners;

    }



    public Event(JSONObject jEvent) {

        try {
            Gson gson = new Gson();
            this._id = jEvent.getString("_id");
            this.name = jEvent.getString("name");
            this.description = jEvent.getString("description");
            this.updatedOn = jEvent.getString("updatedOn");
            this.createdOn = jEvent.getString("createdOn");
            this.result = jEvent.getJSONArray("result").toString();
            this.convenors = gson.fromJson(jEvent.getJSONArray("coords").toString(), Convenor[].class);
            this.isLitSocEvent = jEvent.getBoolean("isLitSocEvent");
            this.category = jEvent.getString("category");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.time = jEvent.getString("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.venue = jEvent.getString("venue");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!this.isLitSocEvent){
            try {
                this.club = jEvent.getJSONObject("club").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    public Convenor[] getConvenors() {
        return convenors;
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
    public String getId(){
        return _id;
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

    public String eventContext(){
        if (!isLitSocEvent) try {
            return new JSONObject(club).getString("name");
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            return " ";
        }
        else if (category.equals("lit")){
            return "Lit-Soc";
        }
        else if (category.equals("tech")){
            return "Tech-Soc";
        }
        else if (category.equals("sports")){
            return "Schroeter";
        }
        return " ";
    }

    public long saveEvent(Context context){
        Gson gson = new Gson();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TIME,time);
        cv.put(COLUMN_TIMESTAMP, TimeHelper.getTimeStamp(time) + TIMEZONE_CORRECTION);
        cv.put(COLUMN_EVENTID, _id);
        cv.put(COLUMN_DESCRIPTION,description);
        cv.put(COLUMN_VENUE,venue);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_CLUB,club);
        cv.put(COLUMN_RESULT,result);
        cv.put(COLUMN_COORDS,gson.toJson(convenors, Convenor[].class));
        cv.put(COLUMN_CREATEDON,createdOn);
        cv.put(COLUMN_UPDATEDON, updatedOn);
        cv.put(COLUMN_ISLITSOCEVENT, isLitSocEvent?"1":"0" );

        DatabaseHelper dh = new DatabaseHelper(context);
        return dh.addEvent(cv);
    }

    public static ArrayList<Event> getAllEvents(Context context){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getAllEvents();
    }

    public static ArrayList<Event> getAllRelevantEvents(Context context){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getAllRelevantEvents();
    }

    public static ArrayList<Event> getUpcomingRelevantEvents(Context context){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getUpcomingRelevantEvents();
    }

    public static ArrayList<Event> getClubEvents(Context context, String clubId){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getClubEvents(clubId);
    }

    public static ArrayList<Event> getUpcomingEvents(Context context){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getUpcomingEvents();
    }

    public static ArrayList<Event> getEventsForNotification(Context context){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getEventsForNotification();
    }

    public static ArrayList<Event> getArrayList(Cursor c){
        ArrayList<Event> arrayList = new ArrayList<>();
        Gson gson = new Gson();
        while ( c.moveToNext() ){
            arrayList.add(parseEvent(c));
        }
        return arrayList;
    }

    public static Event parseEvent(Cursor c){
        Gson gson = new Gson();
        Event event = new Event(c.getString(1), c.getString(2),c.getString(3),c.getString(4),
                c.getString(5),c.getString(6), c.getString(7),c.getString(8), gson.fromJson(c.getString(9), Convenor[].class) ,
                c.getString(10),c.getString(11), (c.getInt(12)==1)?true:false);
        return event;
    }

    public static Event getAnEvent(Context context, String eventId){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getAnEvent(eventId);
    }

}