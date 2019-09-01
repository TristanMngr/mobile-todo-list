package com.isep.todolist.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.isep.todolist.R;

public class TodoActivity extends AppCompatActivity {
    private static final String TAG = "AuthenticateActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.action_add_task:
                Log.d(TAG, "open alert");
                Log.d(TAG, "wow");


                return true;
                default:
                    Log.d(TAG, "nothing happen");
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
                String task = String.valueOf(taskEditText.getText());
                Log.d(TAG, "Task to add: " + task);
            }
        })
        .setNegativeButton("Cancel", null)
        .create();
        dialog.show();
    }
}
