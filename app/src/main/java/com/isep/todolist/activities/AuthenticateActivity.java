package com.isep.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.isep.todolist.R;
import com.isep.todolist.Utils.Helper;
import com.isep.todolist.interfaces.AuthenticateService;
import com.isep.todolist.models.Authenticate;
import com.isep.todolist.remote.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthenticateActivity extends AppCompatActivity {
    private static final String TAG = "AuthenticateActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on Create");

        setContentView(R.layout.activity_authenticate);
    }

    public void onLinkClick(View view) {
        Intent myIntent = new Intent(getBaseContext(), SignUpActivity.class);
        startActivity(myIntent);
    }

    public void onButtonSubmitClick(View view) {
        final EditText emailEditText = findViewById(R.id.email);
        String email = emailEditText.getText().toString();

        final EditText passwordEditText = findViewById(R.id.password);
        String password = passwordEditText.getText().toString();

        login(email, password);
    }

    public void login(String email, String password) {
        final AuthenticateActivity authenticateActivity = this;
        AuthenticateService authenticateService = ServiceGenerator.createService(AuthenticateService.class);


        authenticateService.login(email, password).enqueue(new Callback<Authenticate>() {
            @Override
            public void onResponse(Call<Authenticate> call, Response<Authenticate> response) {


                if (response.isSuccessful()) {
                    Authenticate authenticate = response.body();

                    if (authenticate.getAuthToken() != null) {
                        Log.d("token", authenticate.getAuthToken());

                        Helper.setToken(authenticateActivity, authenticate.getAuthToken());

                        Intent myIntent = new Intent(getBaseContext(), TodoActivity.class);
                        startActivity(myIntent);
                    }
                } else {
                    final TextView messageTextView = findViewById(R.id.message);
                    messageTextView.setText("Sorry, wrong email or password !");
                }
            }

            @Override
            public void onFailure(Call<Authenticate> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }
}
