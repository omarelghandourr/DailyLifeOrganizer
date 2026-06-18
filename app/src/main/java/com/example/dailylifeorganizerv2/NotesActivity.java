package com.example.dailylifeorganizerv2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    ListView listNotes;
    Button btnAddNote;

    NotesDatabaseHelper db;

    ArrayList<String> noteTitles;
    ArrayList<Integer> noteIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        listNotes = findViewById(R.id.listNotes);
        btnAddNote = findViewById(R.id.btnAddNote);

        db = new NotesDatabaseHelper(this);

        btnAddNote.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            NotesActivity.this,
                            NoteEditorActivity.class
                    );

            startActivity(intent);
        });

        loadNotes();

        listNotes.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent =
                    new Intent(
                            NotesActivity.this,
                            NoteEditorActivity.class
                    );

            intent.putExtra(
                    "NOTE_ID",
                    noteIds.get(position)
            );

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {

        noteTitles = new ArrayList<>();
        noteIds = new ArrayList<>();

        Cursor cursor = db.getAllNotes();

        while(cursor.moveToNext()) {

            int noteId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("id")
                    );

            String title =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("title")
                    );

            noteIds.add(noteId);
            noteTitles.add(title);
        }

        cursor.close();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        noteTitles
                );

        listNotes.setAdapter(adapter);
    }
}