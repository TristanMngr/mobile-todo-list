package com.isep.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.isep.todolist.R;
import com.isep.todolist.Utils.Helper;
import com.isep.todolist.interfaces.SignUpService;
import com.isep.todolist.models.SignUp;
import com.isep.todolist.remote.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onButtonSubmitClick(View view) {
        final EditText nameEditText = findViewById(R.id.name);
        String name = nameEditText.getText().toString();

        final EditText emailEditText = findViewById(R.id.email);
        String email = emailEditText.getText().toString();

        final EditText passwordEditText = findViewById(R.id.password);
        String password = passwordEditText.getText().toString();

        final EditText passwordConfirmationEditText= findViewById(R.id.password_confirmation);
        String passwordConfirmation = passwordConfirmationEditText.getText().toString();

        signup(email, name, password, passwordConfirmation);
    }

    public void onLinkClick(View view) {
        Intent myIntent = new Intent(getBaseContext(), AuthenticateActivity.class);
        startActivity(myIntent);
    }

    public void signup(String email, String name, String password, String password_confirmation) {
        final SignUpActivity signUpActivity = this;
        SignUpService signUpService = ServiceGenerator.createService(SignUpService.class);


        signUpService.signup(email, name, password, password_confirmation).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {


                if (response.isSuccessful()) {
                    SignUp signUp = response.body();

                    if (signUp.getAuthToken() != null) {
                        Log.d("token", signUp.getAuthToken());

                        Helper.setToken(signUpActivity, signUp.getAuthToken());

                        Intent myIntent = new Intent(getBaseContext(), TodoActivity.class);
                        startActivity(myIntent);
                    }
                } else {
                    final TextView messageTextView = findViewById(R.id.message);
                    messageTextView.setText("Sorry, something wrong happen");
                }
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }
}
