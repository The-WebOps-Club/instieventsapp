package org.saarang.instieventsapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.instieventsapp.Objects.Club;
import org.saarang.instieventsapp.Objects.Event;
import org.saarang.instieventsapp.Objects.ScoreCard;

import java.util.ArrayList;

/**
 * Created by Ahammad on 15/06/15.
 */
public class DatabaseHelper {

    private static String LOG_TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "InstiEventsApp";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;


    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub _id INTEGER PRIMARY KEY
            db.execSQL(Club.CREATE_TABLE_CLUB);
            db.execSQL(ScoreCard.CREATE_TABLE);
            db.execSQL(Event.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL(" DROP TABLE IF EXISTS " + Club.TABLE_NAME  );
            db.execSQL(" DROP TABLE IF EXISTS " + ScoreCard.TABLE_NAME  );
            db.execSQL(" DROP TABLE IF EXISTS " + Event.TABLE_NAME  );
            onCreate(db);
        }

    }

    public DatabaseHelper(Context c){
        ourContext = c;
    }

    public DatabaseHelper open(){
        ourHelper = new DbHelper (ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public long addClub(ContentValues cv) {
        open();
        long id = ourDatabase.insert(Club.TABLE_NAME, null, cv);
        close();
        return id;
    }

    public void updateClub(int i,String clubid){
        open();
        ContentValues cv=new ContentValues();
        cv.put("isSubscribed", i);
        ourDatabase.update(Club.TABLE_NAME, cv, "clubId" + " = ?", new String[]{clubid});
        close();

    }

    public long addScoreCard(ContentValues cv) {
        open();
        long id = ourDatabase.insert(ScoreCard.TABLE_NAME, null, cv);
        close();
        return id;
    }

    public long addEvent(ContentValues cv){
        open();
        if (cv.getAsInteger(Event.COLUMN_ISLITSOCEVENT) == 0){
            try {
                String clubId = new JSONObject(cv.getAsString(Event.COLUMN_CLUB)).getString("_id");
                Cursor c = ourDatabase.query(Club.TABLE_NAME, new String[]{Club.COLUMN_ISSUBSCRIBED},
                        Club.COLUMN_CLUB_ID + " LIKE ? ", new String[]{clubId}, null, null, null, null);
                while (c.moveToNext()){
                    cv.put(Event.COLUMN_ISRELEVANT, c.getInt(0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            cv.put(Event.COLUMN_ISRELEVANT, 1);
        }

        long id = ourDatabase.insert(Event.TABLE_NAME, null, cv);
        close();
        return id;
    }

    public ArrayList<Club> getAllClubs () {
        open();
        String[] columns = Club.columns;
        Cursor c = ourDatabase.query(Club.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Club> arrayList = Club.getArrayList(c);
        close();
        return arrayList;
    }

    public Club getAClub (String id) {
        open();
        String[] columns = Club.columns;
        Cursor c = ourDatabase.query(Club.TABLE_NAME, columns, Club.COLUMN_CLUB_ID + " LIKE ? ",
                new String[]{id}, null, null, null, null);
        Club club = Club.getClub(c);
        close();
        return club;
    }

    public ArrayList<Event> getAllEvents () {
        open();
        long timeNow = System.currentTimeMillis()/1000000;
        String[] columns = Event.columns;
        Cursor c = ourDatabase.query(Event.TABLE_NAME, columns, null, null, null, null,
                " ABS( " + Event.COLUMN_TIMESTAMP + " - "+ timeNow + " ) ASC ");
        ArrayList<Event> arrayList = Event.getArrayList(c);
        close();
        return arrayList;
    }

    public ArrayList<Event> getAllRelevantEvents () {
        open();
        long timeNow = System.currentTimeMillis()/1000000;
        String[] columns = Event.columns;
        Cursor c = ourDatabase.query(Event.TABLE_NAME, columns, Event.COLUMN_ISRELEVANT + " LIKE ?",
                new String[]{"1"}, null, null, " ABS( " + Event.COLUMN_TIMESTAMP + " - "+
                        timeNow + " ) ASC ");
        ArrayList<Event> arrayList = Event.getArrayList(c);
        close();
        return arrayList;
    }

    public ArrayList<Event> getUpcomingEvents () {
        open();
        long timeNow = System.currentTimeMillis()/1000000;
        String[] columns = Event.columns;
        Cursor c = ourDatabase.query(Event.TABLE_NAME, columns, Event.COLUMN_TIMESTAMP + "> ?",
                new String[]{"" + timeNow}, null, null, null);
        ArrayList<Event> arrayList = Event.getArrayList(c);
        close();
        return arrayList;
    }

    public ArrayList<Event> getClubEvents (String clubId) {
        open();
        long timeNow = System.currentTimeMillis()/1000000;
        String[] columns = Event.columns;
        Cursor c = ourDatabase.query(Event.TABLE_NAME, columns, Event.COLUMN_CLUB + " LIKE ?",
                new String[]{'%'+ clubId +'%'}, null, null, " ABS( " + Event.COLUMN_TIMESTAMP + " - "+
                        timeNow + " ) ASC ");
        ArrayList<Event> arrayList = Event.getArrayList(c);
        close();
        return arrayList;
    }

    public Event getAnEvent(String eventId){
        Event event = new Event();
        open();
        String[] columns = Event.columns;
        Cursor c = ourDatabase.query(Event.TABLE_NAME, columns, Event.COLUMN_EVENTID + " LIKE ?",
                new String[]{eventId}, null, null, null);
        while (c.moveToNext()){
            event = Event.parseEvent(c);
        }
        close();
        return event;
    }

    public ArrayList<ScoreCard> getScoreBoards (String category) {
        open();
        String[] columns = ScoreCard.columns;
        Cursor c = ourDatabase.query(ScoreCard.TABLE_NAME, columns,
                ScoreCard.COLUMN_CATEGORY + " LIKE ?", new String[]{category}, null, null,
                ScoreCard.COLUMN_SCORE + " DESC");
        ArrayList<ScoreCard> arrayList = ScoreCard.getArrayList(c);
        close();
        return arrayList;
    }

//    public boolean ifSubscribedClub(String clubId){
//        open();
//        String[] columns = Event.columns;
//        Cursor c = ourDatabase.query(Event.TABLE_NAME, columns, Event.COLUMN_EVENTID + " LIKE ?",
//                new String[]{eventId}, null, null, null);
//        while (c.moveToFirst()){
//            event = Event.parseEvent(c);
//        }
//        close();
//        return event;
//    }



}