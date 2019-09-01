package com.isep.todolist.interfaces;

import com.isep.todolist.models.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TodoService {
    @GET("/todos")
    Call<List<Todo>> loadTodos();

    @FormUrlEncoded
    @POST("/todos")
    Call<Todo> create(@Field("title") String title);
}
