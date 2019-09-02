package com.isep.todolist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.isep.todolist.R;
import com.isep.todolist.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends ArrayAdapter<Todo> {
    private Context mContext;
    private List<Todo> todoList = new ArrayList<>();

    public TodoAdapter(@NonNull Context context, @LayoutRes ArrayList<Todo> list) {
        super(context, 0, list);
        mContext = context;
        todoList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_todo, parent, false);

        Todo currentTodo = todoList.get(position);

        TextView todoListTitle = listItem.findViewById(R.id.todo_title);
        todoListTitle.setText(currentTodo.getTitle());
        todoListTitle.setTag(currentTodo.getId());

        return listItem;
    }
}
