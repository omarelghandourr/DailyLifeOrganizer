package com.example.dailylifeorganizerv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NOTES = "notes";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";

    public NotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + TABLE_NOTES + "(" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COL_TITLE + " TEXT," +
                        COL_CONTENT + " TEXT" +
                        ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public void insertNote(String title,
                           String content) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_TITLE, title);
        values.put(COL_CONTENT, content);

        db.insert(TABLE_NOTES, null, values);

        db.close();
    }

    public Cursor getAllNotes() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_NOTES,
                null
        );
    }

    public void deleteNote(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                TABLE_NOTES,
                COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }
    public Cursor getNoteById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_NOTES +
                        " WHERE id=?",
                new String[]{String.valueOf(id)}
        );
    }

    public void updateNote(
            int id,
            String title,
            String content) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_TITLE, title);
        values.put(COL_CONTENT, content);

        db.update(
                TABLE_NOTES,
                values,
                COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

}