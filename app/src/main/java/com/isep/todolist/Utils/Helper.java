package com.isep.todolist.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Helper {
    public static void setToken(Activity activity, String token) {
        SharedPreferences preferences = activity.getSharedPreferences(
      "com.isep.todolist", Context.MODE_PRIVATE);

        preferences.edit().putString("token", token).apply();
    }

    public static String getToken(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(
      "com.isep.todolist", Context.MODE_PRIVATE);

        return preferences.getString("token", null);
    }
}
