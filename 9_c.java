package com.example.meetingschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MeetingSchedule.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "meetings";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_AGENDA = "agenda";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_TIME + " TEXT, " +
            COLUMN_AGENDA + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertMeeting(String date, String time, String agenda) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_AGENDA, agenda);
        return db.insert(TABLE_NAME, null, values);
    }

    public String getMeetingAgenda(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_AGENDA},
                COLUMN_DATE + "=?", new String[]{date}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String agenda = cursor.getString(0);
            cursor.close();
            return agenda;
        }
        return null;
    }
}
