package com.example.blooddonstion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ReportDb extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BloodApplications";
    private static final String TABLE_Report = "DataReport";
    public static final String ID = "ID";
    private static final String SerialID = "SerialID";
    private static final String Temperature = "Temperature";
    private static final String DateTime = "DateTime";
    private static final String Progress = "Process";
    private static final String rfid = "rfidnumber";


    public ReportDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_Report + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SerialID + " TEXT,"
                + Temperature + " TEXT,"
                + Progress + " TEXT,"
                + DateTime + " TEXT,"
                + rfid + " TEXT)";


        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Report);
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addContact(ReportDataModel dataModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(ID, dataModelClass.getID());
        values.put(SerialID, dataModelClass.getSerialNumber());
        values.put(Temperature, dataModelClass.getTemperature());
        values.put(Progress, dataModelClass.getProcess());
        values.put(DateTime, dataModelClass.getDataTime());
        values.put(rfid, dataModelClass.getRfidnumber());


        // Inserting Row
        db.insert(TABLE_Report, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<ReportDataModel> getAllContacts() {
        List<ReportDataModel> contactList = new ArrayList<ReportDataModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Report;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReportDataModel dataModelClass = new ReportDataModel();
                dataModelClass.setId(cursor.getString(0));
                dataModelClass.setSerialNumber(cursor.getString(1));
                dataModelClass.setTemperature(cursor.getString(2));
                dataModelClass.setProcess(cursor.getString(3));
                dataModelClass.setDataTime(cursor.getString(4));
                dataModelClass.setRfidnumber(cursor.getString(5));
                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }


    public List<ReportDataModel> getAllDetails(String Modelname) {
        List<ReportDataModel> contactList = new ArrayList<ReportDataModel>();
        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_Report + "WHERE" + PVmodleName + "=" + Modelname;
        String selectQuery = "SELECT * FROM " + TABLE_Report + " WHERE " + SerialID + " = '" + Modelname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReportDataModel dataModelClass = new ReportDataModel();
                dataModelClass.setId(cursor.getString(0));
                dataModelClass.setSerialNumber(cursor.getString(1));
                dataModelClass.setTemperature(cursor.getString(2));
                dataModelClass.setProcess(cursor.getString(3));
                dataModelClass.setDataTime(cursor.getString(4));
                dataModelClass.setRfidnumber(cursor.getString(5));

                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    public List<ReportDataModel> getAllDetails1(String Modelname) {
        List<ReportDataModel> contactList = new ArrayList<ReportDataModel>();
        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_Report + "WHERE" + PVmodleName + "=" + Modelname;
        String selectQuery = "SELECT * FROM " + TABLE_Report + " WHERE " + rfid + " = '" + Modelname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReportDataModel dataModelClass = new ReportDataModel();
                dataModelClass.setId(cursor.getString(0));
                dataModelClass.setSerialNumber(cursor.getString(1));
                dataModelClass.setTemperature(cursor.getString(2));
                dataModelClass.setProcess(cursor.getString(3));
                dataModelClass.setDataTime(cursor.getString(4));
                dataModelClass.setRfidnumber(cursor.getString(5));

                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }


    // Deleting single contact
//    public void deleteContact(ReportModelClass contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_Report, ID + " = ?",
//                new String[]{String.valueOf(contact.getID())});
//        db.close();
//    }
    public void deleteRow(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Report + " WHERE " + ID + "='" + value + "'");
        db.close();
    }

    //    public void delete(int position) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        String table = TABLE_Report;
//        String whereClause = ID;
//        String [] whereArgs = new String[] {String.valueOf(position)};
//        db.delete (table, whereClause, whereArgs);
//
//    }
    //Method for Update
    public int updateContact(ReportDataModel dataModelClass, String valueID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SerialID, dataModelClass.getSerialNumber());
        values.put(Temperature, dataModelClass.getTemperature());
        values.put(Progress, dataModelClass.getProcess());
        values.put(DateTime, dataModelClass.getDataTime());
        // updating row
        return db.update(TABLE_Report, values, ID + " = ?",
                new String[]{String.valueOf(valueID)});
    }

}
