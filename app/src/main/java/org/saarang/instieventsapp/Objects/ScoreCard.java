package org.saarang.instieventsapp.Objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.saarang.instieventsapp.Helper.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Ahammad on 25/08/15.
 */
public class ScoreCard {

    String category, hostel, score;

    public ScoreCard(String category, String hostel, String score) {
        this.category = category;
        this.hostel = hostel;
        this.score = score;
    }

    public static String LOG_TAG = "ScoreCardObject";

    public static String TABLE_NAME = "ScoreCard";

    private static String KEY_ROWID = "_id";
    private static String COLUMN_SCORE_ID = "scoreId";
    public static String COLUMN_CATEGORY  = "category";
    public static String COLUMN_SCORE = "score";
    private static String COLUMN_HOSTEL = "hostel";


    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            KEY_ROWID + " INTEGER " + " PRIMARY KEY , " +
            COLUMN_SCORE_ID + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE , " +
            COLUMN_CATEGORY + " TEXT NOT NULL , " +
            COLUMN_HOSTEL + " TEXT  , " +
            COLUMN_SCORE + " NUMBER " +
            " );";

    public static String[] columns = {KEY_ROWID, COLUMN_CATEGORY, COLUMN_HOSTEL, COLUMN_SCORE};

    public static ContentValues getCV(String category, String hostel, int score, String _id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SCORE_ID, _id);
        cv.put(COLUMN_HOSTEL, hostel);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_SCORE, score );
        return cv;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getscore() {
        return score;
    }

    public void setscore(String score) {
        this.score = score;
    }

    public static long saveScoreCard(Context context, ContentValues cv){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.addScoreCard(cv);
    }

    public static ArrayList<ScoreCard> getArrayList(Cursor c){
        ArrayList<ScoreCard> arrayList = new ArrayList<>();
        while ( c.moveToNext() ){
            ScoreCard scoreCard = new ScoreCard(c.getString(1), c.getString(2), c.getString(3));
            arrayList.add(scoreCard);
        }
        return arrayList;
    }

    public static ArrayList<ScoreCard> getScoreBoards (Context context, String category){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getScoreBoards(category);
    }

}
