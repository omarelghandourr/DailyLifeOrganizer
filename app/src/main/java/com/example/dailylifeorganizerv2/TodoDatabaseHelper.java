package com.example.dailylifeorganizerv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TODO = "todo_table";

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + TABLE_TODO + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "task TEXT," +
                        "completed INTEGER DEFAULT 0)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public void addTask(String task) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("task", task);
        values.put("completed", 0);

        db.insert(TABLE_TODO, null, values);

        db.close();
    }

    public ArrayList<TodoItem> getAllTodoItems() {

        ArrayList<TodoItem> items = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, task, completed FROM " + TABLE_TODO,
                null
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                boolean completed = cursor.getInt(2) == 1;

                items.add(
                        new TodoItem(id, task, completed)
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return items;
    }

    public void updateTaskStatus(int id, boolean completed) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("completed", completed ? 1 : 0);

        db.update(
                TABLE_TODO,
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    public void deleteTask(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                TABLE_TODO,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }
}