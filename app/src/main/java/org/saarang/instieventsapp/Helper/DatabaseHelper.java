package org.saarang.instieventsapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.saarang.instieventsapp.Objects.Club;

import java.util.ArrayList;

/**
 * Created by Ahammad on 15/06/15.
 */
public class DatabaseHelper {

    private static final String DATABASE_NAME = "InstiEventsApp";
    private static final int DATABASE_VERSION = 2;

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
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL(" DROP TABLE IF EXISTS " + Club.TABLE_NAME  );
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

    public ArrayList<Club> getAllClubs () {
        open();
        String[] columns = Club.columns;
        Cursor c = ourDatabase.query(Club.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Club> arrayList = Club.getArrayList(c);
        close();
        return arrayList;
    }




}

