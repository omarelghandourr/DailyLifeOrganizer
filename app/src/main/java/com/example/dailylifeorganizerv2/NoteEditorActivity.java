package com.example.dailylifeorganizerv2;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NoteEditorActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;

    Button btnSave;
    Button btnDelete;

    NotesDatabaseHelper db;

    int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        db = new NotesDatabaseHelper(this);

        noteId =
                getIntent().getIntExtra(
                        "NOTE_ID",
                        -1
                );

        if(noteId != -1) {

            Cursor cursor =
                    db.getNoteById(noteId);

            if(cursor.moveToFirst()) {

                editTitle.setText(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("title")
                        )
                );

                editContent.setText(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("content")
                        )
                );
            }

            cursor.close();
        }

        btnSave.setOnClickListener(v -> {

            String title =
                    editTitle.getText().toString();

            String content =
                    editContent.getText().toString();

            if(noteId == -1) {

                db.insertNote(
                        title,
                        content
                );

                Toast.makeText(
                        this,
                        "Note Saved",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                db.updateNote(
                        noteId,
                        title,
                        content
                );

                Toast.makeText(
                        this,
                        "Note Updated",
                        Toast.LENGTH_SHORT
                ).show();
            }

            finish();
        });

        btnDelete.setOnClickListener(v -> {

            if(noteId != -1) {

                db.deleteNote(noteId);

                Toast.makeText(
                        this,
                        "Note Deleted",
                        Toast.LENGTH_SHORT
                ).show();
            }

            finish();
        });
    }
}