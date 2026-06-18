package com.example.dailylifeorganizerv2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {

    private EditText editTask;
    private Button btnAddTask;
    private ListView listTasks;

    private ArrayList<TodoItem> tasks;
    private TodoAdapter adapter;

    private TodoDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        editTask = findViewById(R.id.editTask);
        btnAddTask = findViewById(R.id.btnAddTask);
        listTasks = findViewById(R.id.listTasks);

        dbHelper = new TodoDatabaseHelper(this);

        tasks = dbHelper.getAllTodoItems();

        adapter = new TodoAdapter(
                this,
                tasks,
                dbHelper
        );

        listTasks.setAdapter(adapter);

        listTasks.setOnItemLongClickListener((parent, view, position, id) -> {

            TodoItem selectedTask = tasks.get(position);

            dbHelper.deleteTask(
                    selectedTask.getId()
            );

            tasks.remove(position);

            adapter.notifyDataSetChanged();

            Toast.makeText(
                    TodoActivity.this,
                    "Task deleted",
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        });

        btnAddTask.setOnClickListener(v -> {

            String task = editTask.getText().toString().trim();

            if (task.isEmpty()) {

                Toast.makeText(
                        TodoActivity.this,
                        "Please enter a task",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            dbHelper.addTask(task);

            tasks.clear();
            tasks.addAll(
                    dbHelper.getAllTodoItems()
            );

            adapter.notifyDataSetChanged();

            editTask.setText("");

            Toast.makeText(
                    TodoActivity.this,
                    "Task added",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}