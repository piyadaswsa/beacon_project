package com.example.projectbeacon.Database.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projectbeacon.Model.OffScreenTime;

import java.util.ArrayList;
import java.util.Date;

public class OffScreenTimeSqlite extends SQLiteOpenHelper {
//    private String username;
//    private Date time;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "offstdb";
    private static final String TABLE_OffSTs = "offstdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DATE_MILLIS = "date_millis";
    private static final String KEY_IS_OFF = "is_off";

    public OffScreenTimeSqlite(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_OffSTs + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USERNAME + " TEXT,"
                + KEY_DATE_MILLIS + " INTEGER,"
                + KEY_IS_OFF + " INTEGER"+ ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OffSTs);
        // Create tables again
        onCreate(db);
    }

    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new time Details
    public void insertOffSTDetails(OffScreenTime time){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_USERNAME, time.getUsername());
        cValues.put(KEY_DATE_MILLIS, time.getTimeMillis());
        cValues.put(KEY_IS_OFF, time.getIsOff());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_OffSTs,null, cValues);
        db.close();
    }

    // Get offscreentime Details
    public ArrayList<OffScreenTime> GetDetails(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT username, date_millis, is_off FROM "+ TABLE_OffSTs;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<OffScreenTime> offList = new ArrayList<>();
        while (cursor.moveToNext()){
            OffScreenTime o = new OffScreenTime();
            o.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            o.setTime(new Date(cursor.getLong(cursor.getColumnIndex(KEY_DATE_MILLIS))));
            if(cursor.getInt(cursor.getColumnIndex(KEY_IS_OFF)) == 1){
                o.setIsOff(true);
            }else{
                o.setIsOff(false);
            }
            offList.add(o);
        }
        return  offList;
    }

    // Get time Details
    public long GetOffScreenTime(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT date_millis FROM "+ TABLE_OffSTs +" where username = ?";
        Cursor cursor = db.rawQuery(query,new String[] {user});
        String str = "";
        while (cursor.moveToNext()){
            str = cursor.getString(cursor.getColumnIndex(KEY_DATE_MILLIS));
        }
        return  Long.parseLong(str);
    }

    // Get is off Details
    public int GetIsOffScreen(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT is_off FROM "+ TABLE_OffSTs +" where username = ?";
        Cursor cursor = db.rawQuery(query,new String[] {user});
        int i = 0;
        while (cursor.moveToNext()){
            i = cursor.getInt(cursor.getColumnIndex(KEY_IS_OFF));
        }
        return  i;
    }

    // Delete time Details
    public void DeleteOffScreenTime(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OffSTs, KEY_USERNAME+" = ?",new String[]{user});
        db.close();
    }

    public OffScreenTime GetOffScreenTimeDetail(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT username, date_millis, is_off FROM "+ TABLE_OffSTs +" where username = ?";
        Cursor cursor = db.rawQuery(query,new String[] {user});
        OffScreenTime offST = new OffScreenTime();
        while (cursor.moveToNext()){
            offST.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            offST.setTime(new Date(cursor.getLong(cursor.getColumnIndex(KEY_DATE_MILLIS))));
            if(cursor.getInt(cursor.getColumnIndex(KEY_IS_OFF)) == 1){
                offST.setIsOff(true);
            }else{
                offST.setIsOff(false);

            }
        }
        return  offST;
    }

    public boolean isContain(String user){
        OffScreenTime o = GetOffScreenTimeDetail(user);
        if(o != null){
            return true;
        }else{
            return false;
        }
    }

    // Update time Details
    public int UpdateOffStDetails(OffScreenTime time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_DATE_MILLIS, time.getTimeMillis());
        cVals.put(KEY_IS_OFF, time.getIsOff());

        int count = db.update(TABLE_OffSTs, cVals, KEY_USERNAME+" = ?",new String[]{time.getUsername()});
        return  count;
    }

}
