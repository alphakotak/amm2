package com.example.android.footaball_turf.data;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.database.Cursor;


import com.example.android.footaball_turf.model.User;

import java.util.*;
import java.io.*;



public class RegisterDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "register.db";

    private static final int DATABASE_VERSION = 1;

    public final static String TABLE_NAME = "register";
    public final static String _ID = BaseColumns._ID;
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_EMAIL = "email";
    public final static String COLUMN_PASS = "password";

    // Create a String that contains the SQL statement to create the pets table
    String SQL_CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL, "
            + COLUMN_PASS + " TEXT NOT NULL);";

    String SQL_DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public RegisterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete
        db.execSQL(SQL_DROP_USER_TABLE);
        //wapas
        onCreate(db);
    }

    //creates user record will be called in register activity
    /*public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASS, user.getPassword());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    */
//fetches all user list to show during matching

    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                _ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASS
        };
        // sorting orders
        String sortOrder =
                COLUMN_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASS)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

}