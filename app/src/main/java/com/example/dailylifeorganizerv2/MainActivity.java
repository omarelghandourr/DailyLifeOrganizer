package com.example.dailylifeorganizerv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNotes = findViewById(R.id.btnNotes);
        Button btnTodo = findViewById(R.id.btnTodo);
        Button btnDates = findViewById(R.id.btnDates);
        Button btnShopping = findViewById(R.id.btnShopping);
        Button btnAbout = findViewById(R.id.btnAbout);

        btnNotes.setOnClickListener(v ->
                startActivity(new Intent(this, NotesActivity.class)));

        btnTodo.setOnClickListener(v ->
                startActivity(new Intent(this, TodoActivity.class)));

        btnDates.setOnClickListener(v ->
                startActivity(new Intent(this, DatesActivity.class)));

        btnShopping.setOnClickListener(v ->
                startActivity(new Intent(this, ShoppingActivity.class)));

        btnAbout.setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));
    }
}