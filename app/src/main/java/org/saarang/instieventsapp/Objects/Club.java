package org.saarang.instieventsapp.Objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

import org.saarang.instieventsapp.Helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiran on 23/8/15.
 */
public class Club {

    private static String LOG_TAG = "ClubObject";

    String name;
    String createdOn;
    String updatedOn;
    String description;
    String category;
    Boolean isSubscribed;
    String _id;

    class Convenor{

        String name;
        String phonenum;
        String email;
    }
    List<Convenor> convenorsList;
    Convenor[] convenors;

    public static String TABLE_NAME = "clubs";

    private static String KEY_ROWID = "_id";
    private static String COLUMN_CLUB_ID = "clubId";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_CREATED_ON = "createdOn";
    private static String COLUMN_UPDATED_ON = "updatedOn";
    private static String COLUMN_CATEGORY = "category";
    private static String COLUMN_ISSUBSCRIBED = "isSubscribed";
    private static String COLUMN_DESCRIPTION = "description";
    private static String COLUMN_CONVENORS = "convenors";


    public static String CREATE_TABLE_CLUB = "CREATE TABLE " + TABLE_NAME + " ( " +
            KEY_ROWID + " INTEGER " + " PRIMARY KEY , " +
            COLUMN_CLUB_ID + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE , " +
            COLUMN_NAME + " TEXT NOT NULL , " +
            COLUMN_CREATED_ON + " TEXT  , " +
            COLUMN_UPDATED_ON + " TEXT  , " +
            COLUMN_CATEGORY + " TEXT NOT NULL , " +
            COLUMN_ISSUBSCRIBED + " NUMBER  , " +
            COLUMN_DESCRIPTION + " TEXT , " +
            COLUMN_CONVENORS + " TEXT  " +
            " );";



    public static String[] columns = {KEY_ROWID, COLUMN_CLUB_ID, COLUMN_NAME, COLUMN_CREATED_ON,
            COLUMN_UPDATED_ON, COLUMN_CATEGORY, COLUMN_DESCRIPTION, COLUMN_CONVENORS, COLUMN_ISSUBSCRIBED};


    public Club(String _id, String name, String createdOn, String updatedOn, String category,
                String description, Convenor[] convenors,int isSubscribed){
        this.name=name;
        this._id = _id;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.convenors = convenors;
        this.category=category;
        this.description=description;
        if(isSubscribed==0)
            this.isSubscribed=false;
        else
            this.isSubscribed=true;
    }

    public String getName(){
        return name;
    }

    public Boolean getIsSubscribed(){
        return isSubscribed;
    }

    public String getId(){
        return _id;
    }

    public String getCreatedOn(){
        return createdOn;
    }

    public String getUpdatedOn(){
        return updatedOn;
    }

    public String getDescription(){
        return description;
    }

    public String getCategory(){
        return category;
    }

    public List<Convenor> getConvenorsList(){
        return convenorsList;
    }
    public void setIsSubscribed(Boolean subscribed){
        this.isSubscribed=subscribed;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setId(String id){
        this._id=id;
    }

    public void setCreatedOn(String createdon){
        this.createdOn=createdon;
    }

    public void setUpdatedOn(String updatedon){
        this.updatedOn=updatedon;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void setCategory(String category){
        this.category=category;
    }

    public void setConvenorsList(List<Convenor> list){
        this.convenorsList=list;
    }

    public ContentValues getCV(){
        Gson gson = new Gson();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_CLUB_ID, _id);
        cv.put(COLUMN_CREATED_ON, createdOn);
        cv.put(COLUMN_UPDATED_ON, updatedOn);
        cv.put(COLUMN_DESCRIPTION, description);
        if (isSubscribed){
            cv.put(COLUMN_ISSUBSCRIBED, 1);}
         else
        {cv.put(COLUMN_ISSUBSCRIBED, 0);}
        cv.put(COLUMN_CONVENORS, gson.toJson(convenors, Convenor[].class));
        cv.put(COLUMN_NAME, name);
        return cv;
    }

    public static ArrayList<Club> getAllClubs(Context context){
        DatabaseHelper data = new DatabaseHelper(context);
        return data.getAllClubs();
    }

//    public static String[] columns = {COLUMN_CLUB_ID, COLUMN_NAME, COLUMN_CREATED_ON,
//            COLUMN_UPDATED_ON, COLUMN_CATEGORY, COLUMN_DESCRIPTION, COLUMN_CONVENORS};

//    public Club(String _id, String name, String createdOn, String updatedOn, Convenor[] convenors){

    public static ArrayList<Club> getArrayList(Cursor c){
        ArrayList<Club> arrayList = new ArrayList<>();
        Gson gson = new Gson();
        while ( c.moveToNext() ){
            Log.d(LOG_TAG, "Club id loaded is " + c.getString(1));
            Club club = new Club(c.getString(1), c.getString(2),c.getString(3),c.getString(4),
                    c.getString(5),c.getString(6), gson.fromJson(c.getString(7), Convenor[].class),c.getInt(8) );
            arrayList.add(club);
        }
        return arrayList;
    }

}

