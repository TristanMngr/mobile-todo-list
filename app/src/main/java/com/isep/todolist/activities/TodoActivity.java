package com.isep.todolist.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.isep.todolist.R;
import com.isep.todolist.Utils.Helper;
import com.isep.todolist.adapters.TodoAdapter;
import com.isep.todolist.interfaces.TodoService;
import com.isep.todolist.models.Todo;
import com.isep.todolist.remote.ServiceGenerator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoActivity extends AppCompatActivity {
    private static final String TAG = "AuthenticateActivity";

    private ListView todoListView;
    private ArrayAdapter<Todo> arrayAdapter;


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
        final EditText todoEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("Add a new todo")
        .setMessage("What do you want to do next?")
        .setView(todoEditText)
        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String todoTitle = String.valueOf(todoEditText.getText());
                createTodo(todoTitle);
            }
        })
        .setNegativeButton("Cancel", null)
        .create();
        dialog.show();
    }

    public void loadTodos() {
        TodoService todoService = ServiceGenerator.createService(TodoService.class, Helper.getToken(this));

        todoService.loadTodos().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Todo> todoList = new ArrayList<>();
                    for (Todo todo : response.body()) {
                        todoList.add(todo);
                    }

                    if (arrayAdapter == null) {
                        arrayAdapter = new TodoAdapter(getBaseContext(), todoList);
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

    public void onLinkTaskClick(View view) {
        View parent = (View) view.getParent();
        TextView todoTextView = parent.findViewById(R.id.todo_title);
        Intent myIntent = new Intent(getBaseContext(), ItemActivity.class);
        String todoId = String.valueOf(todoTextView.getTag());
        myIntent.putExtra("todoId", todoId);
        startActivity(myIntent);
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

    public void onClickDoneButton(View view) {
        View parent = (View) view.getParent();
        TextView todoTextView = parent.findViewById(R.id.todo_title);
        String todoId = String.valueOf(todoTextView.getTag());

        deleteTodo(todoId);

        loadTodos();
    }


    public void deleteTodo(String id) {
        TodoService todoService = ServiceGenerator.createService(TodoService.class, Helper.getToken(this));

        todoService.deleteTodo(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call <ResponseBody>call, Response <ResponseBody>response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Unable to delete Todo");
                }
            }

            @Override
            public void onFailure(Call <ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }


}
