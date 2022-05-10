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

        //addTree("Björk", 306); //this would add something new every time we run the app
        printTrees();
        Log.d("===", "Any trees would have been listed above.");
    }

    private void deleteAllTrees () {
        database.delete(DatabaseTables.Tree.TABLE_NAME, null, null);
    }

    private void printTrees () {
        Cursor cursor = database.query(DatabaseTables.Tree.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Tree tree = new Tree(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseTables.Tree.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Tree.COLUMN_NAME_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Tree.COLUMN_NAME_HEIGHT))
            );
            Log.d("===", tree.toString());
        }
        cursor.close();
    }

    private void addTree (String name, int height) {
        ContentValues values = new ContentValues();
        values.put(DatabaseTables.Tree.COLUMN_NAME_NAME, name);
        values.put(DatabaseTables.Tree.COLUMN_NAME_HEIGHT, height);
        database.insert(DatabaseTables.Tree.TABLE_NAME, null, values);
    }
}
