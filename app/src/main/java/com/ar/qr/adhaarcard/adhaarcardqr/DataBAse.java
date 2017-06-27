package com.ar.qr.adhaarcard.adhaarcardqr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abhay on 22/6/17.
 */

public class DataBAse extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "AdhaarAbhay";
    private static final String  TABLE_CONTACTS = "rastogis";
    private static final String  AADHAR_UID_ATTR = "uid";
    private static final String  AADHAR_NAME_ATTR = "name";
    private static final String  AADHAR_GENDER_ATTR = "gender";
    private static final String  AADHAR_YOB_ATTR = "yob";
    private static final String  AADHAR_CO_ATTR = "co";
    private static final String  AADHAR_VTC_ATTR = "vtc";
    private static final String  AADHAR_PO_ATTR = "po";
    private static final String  AADHAR_DIST_ATTR = "dist";
    private static final String  AADHAR_STATE_ATTR = "state";
    private static final String  AADHAR_PC_ATTR = "pc";
    private static final String  KEY_ID = "id";
    public DataBAse(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + AADHAR_UID_ATTR + " TEXT,"
                + AADHAR_NAME_ATTR + " TEXT," + AADHAR_GENDER_ATTR +  " TEXT," + AADHAR_YOB_ATTR + " TEXT,"
                + AADHAR_CO_ATTR +   " TEXT," + AADHAR_VTC_ATTR +     " TEXT," + AADHAR_PO_ATTR  + " TEXT,"
                + AADHAR_DIST_ATTR + " TEXT," + AADHAR_STATE_ATTR +    " TEXT," + AADHAR_PC_ATTR  + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }
    void addContact(MsqliteSting msqliteSting) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AADHAR_UID_ATTR, msqliteSting.getAADHAR_UID_ATTR());
        values.put(AADHAR_NAME_ATTR, msqliteSting.getAADHAR_NAME_ATTR());
        values.put(AADHAR_GENDER_ATTR, msqliteSting.getAADHAR_GENDER_ATTR());
        values.put(AADHAR_YOB_ATTR, msqliteSting.getAADHAR_YOB_ATTR());
        values.put(AADHAR_CO_ATTR, msqliteSting.getAADHAR_CO_ATTR());
        values.put(AADHAR_VTC_ATTR, msqliteSting.getAADHAR_YOB_ATTR());
        values.put(AADHAR_PO_ATTR, msqliteSting.getAADHAR_PO_ATTR());
        values.put(AADHAR_DIST_ATTR, msqliteSting.getAADHAR_DIST_ATTR());
        values.put(AADHAR_STATE_ATTR, msqliteSting.getAADHAR_STATE_ATTR());
        values.put(AADHAR_PC_ATTR, msqliteSting.getAADHAR_PC_ATTR());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        return data;
    }
    DataAttributes getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DataAttributes contact = new DataAttributes((cursor.getString(0)));
        // return contact
        return contact;
    }
    public void deleteContact(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { contact });
        db.close();
    }

}
