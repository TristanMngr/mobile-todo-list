package com.isep.todolist.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.isep.todolist.R;
import com.isep.todolist.Utils.Helper;
import com.isep.todolist.interfaces.TodoService;
import com.isep.todolist.models.Todo;
import com.isep.todolist.remote.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoActivity extends AppCompatActivity {
    private static final String TAG = "AuthenticateActivity";

    private ListView todoListView;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        todoListView = findViewById(R.id.list_todo);

        loadTodos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.add_todo:
                alertCreationTodo();
                return true;
            case R.id.sign_out:
                Helper.removeToken(this);

                Intent myIntent = new Intent(getBaseContext(), AuthenticateActivity.class);
                startActivity(myIntent);
                return true;
                default:
                    Log.d(TAG, "no options");
            return super.onOptionsItemSelected(menuItem);
        }
    }

    private void alertCreationTodo() {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("Add a new task")
        .setMessage("What do you want to do next?")
        .setView(taskEditText)
        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String todoTitle = String.valueOf(taskEditText.getText());
                createTodo(todoTitle);
            }
        })
        .setNegativeButton("Cancel", null)
        .create();
        dialog.show();
    }

    public void loadTodos() {
        final TodoActivity todoActivity = this;
        TodoService todoService = ServiceGenerator.createService(TodoService.class, Helper.getToken(this));

        todoService.loadTodos().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> todoList = new ArrayList<>();
                    for (Todo todo : response.body()) {
                        todoList.add(todo.getTitle());
                    }

                    if (arrayAdapter == null) {
                        arrayAdapter = new ArrayAdapter(todoActivity, R.layout.item_todo, R.id.todo_title, todoList);
                        todoListView.setAdapter(arrayAdapter);
                    } else {
                        arrayAdapter.clear();
                        arrayAdapter.addAll(todoList);
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d(TAG, "Unable to fetch todos");
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }

    public void createTodo(String title) {
        TodoService todoService = ServiceGenerator.createService(TodoService.class, Helper.getToken(this));

        todoService.create(title).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    Todo todo = response.body();
                    loadTodos();
                } else {
                    Log.d(TAG, "Unable to create the todo");
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }
}
