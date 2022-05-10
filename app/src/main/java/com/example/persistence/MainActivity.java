package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        addMountain("Kinnekulle", 306);
        printMountains();
        Log.d("===", "Any mountains would have been listed above.");
    }

    private void deleteAllMountains () {
        database.delete(DatabaseTables.Mountain.TABLE_NAME, null, null);
    }

    private void printMountains () {
        Cursor cursor = database.query(DatabaseTables.Mountain.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Mountain mountain = new Mountain(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseTables.Mountain.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Mountain.COLUMN_NAME_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Mountain.COLUMN_NAME_HEIGHT))
            );
            Log.d("===", mountain.getName());
        }
        cursor.close();
    }

    private void addMountain (String name, int height) {
        ContentValues values = new ContentValues();
        values.put(DatabaseTables.Mountain.COLUMN_NAME_NAME, name);
        values.put(DatabaseTables.Mountain.COLUMN_NAME_HEIGHT, height);
        database.insert(DatabaseTables.Mountain.TABLE_NAME, null, values);
    }
}
