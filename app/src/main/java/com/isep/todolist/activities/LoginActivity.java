package com.isep.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.isep.todolist.R;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("start on main activity", "hello");
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view) {
        Intent myIntent = new Intent(getBaseContext(),   TodoActivity.class);
        startActivity(myIntent);
    }
}
