package com.isep.todolist.interfaces;

import com.isep.todolist.models.User;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("/auth/login")
    Call<User> login(@Field("email") String email, @Field("password") String password);
}
