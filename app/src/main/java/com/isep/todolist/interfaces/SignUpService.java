package com.isep.todolist.interfaces;

import com.isep.todolist.models.SignUp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignUpService {
    @FormUrlEncoded
    @POST("/signup")
    Call<SignUp> signup(
            @Field("email") String email,
            @Field("name") String name,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );
}
