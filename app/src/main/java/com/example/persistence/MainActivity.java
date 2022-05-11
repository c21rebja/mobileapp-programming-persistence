package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button readButton;
    private Button writeButton;
    private Button deleteButton;

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextHeight;

    private TextView readText;

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readText = findViewById(R.id.read_text_view);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        //add references to the edit texts
        editTextId = findViewById(R.id.id_edit);
        editTextName = findViewById(R.id.name_edit);
        editTextHeight = findViewById(R.id.height_edit);

        //add references to the buttons
        //add onclickhandlers
        readButton = findViewById(R.id.read_button);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printTrees();
                Log.d("===", "Any trees would have been listed above.");
            }
        });

        writeButton = findViewById(R.id.write_button);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //convert data in edittexts to ints and strings, then add that as a tree in the database
                int idNum = Integer.parseInt(editTextId.getText().toString());
                String nameText = editTextName.getText().toString();
                int heightNum = Integer.parseInt(editTextHeight.getText().toString());

                addTree(idNum, nameText, heightNum);
            }
        });

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllTrees();
            }
        });

        //addTree("Björk", 306); //this would add something new every time we run the app
    }

    private void deleteAllTrees () {
        database.delete(DatabaseTables.Tree.TABLE_NAME, null, null);
    }

    private void printTrees () {
        Cursor cursor = database.query(DatabaseTables.Tree.TABLE_NAME, null, null, null, null, null, null);
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            Tree tree = new Tree(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseTables.Tree.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Tree.COLUMN_NAME_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Tree.COLUMN_NAME_HEIGHT))
            );
            Log.d("===", tree.toString());
            builder.append(tree).append("\n");
        }
        readText.setText(builder.toString()); //läser bara upp den senaste
        cursor.close();
    }

    private void addTree (int id, String name, int height) {
        ContentValues values = new ContentValues();
        values.put(DatabaseTables.Tree.COLUMN_NAME_ID, id);
        values.put(DatabaseTables.Tree.COLUMN_NAME_NAME, name);
        values.put(DatabaseTables.Tree.COLUMN_NAME_HEIGHT, height);
        database.insert(DatabaseTables.Tree.TABLE_NAME, null, values);
    }
}
