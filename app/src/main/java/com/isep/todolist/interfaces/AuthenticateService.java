package com.isep.todolist.interfaces;

import com.isep.todolist.models.Authenticate;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthenticateService {

    @FormUrlEncoded
    @POST("/auth/login")
    Call<Authenticate> login(@Field("email") String email, @Field("password") String password);
}
