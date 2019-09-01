package com.isep.todolist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUp {

@SerializedName("message")
@Expose
private String message;
@SerializedName("auth_token")
@Expose
private String authToken;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getAuthToken() {
return authToken;
}

public void setAuthToken(String authToken) {
this.authToken = authToken;
}

}