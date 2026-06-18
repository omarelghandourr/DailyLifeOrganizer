package com.example.dailylifeorganizerv2;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<TodoItem> {

    private Context context;
    private ArrayList<TodoItem> items;
    private TodoDatabaseHelper dbHelper;

    public TodoAdapter(
            Context context,
            ArrayList<TodoItem> items,
            TodoDatabaseHelper dbHelper) {

        super(context, 0, items);

        this.context = context;
        this.items = items;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public View getView(
            int position,
            @Nullable View convertView,
            @NonNull ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.todo_row, parent, false);
        }

        CheckBox checkTask =
                convertView.findViewById(R.id.checkTask);

        TodoItem item = items.get(position);

        checkTask.setOnCheckedChangeListener(null);

        checkTask.setText(item.getTask());

        checkTask.setChecked(item.isCompleted());

        if (item.isCompleted()) {

            checkTask.setPaintFlags(
                    checkTask.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG
            );

        } else {

            checkTask.setPaintFlags(
                    checkTask.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG)
            );
        }
        checkTask.setOnLongClickListener(v -> {

            TodoItem itemToDelete = item;

            dbHelper.deleteTask(itemToDelete.getId());

            items.remove(itemToDelete);

            notifyDataSetChanged();

            return true;
        });


        checkTask.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    item.setCompleted(isChecked);

                    dbHelper.updateTaskStatus(
                            item.getId(),
                            isChecked
                    );

                    notifyDataSetChanged();
                });

        return convertView;
    }
}