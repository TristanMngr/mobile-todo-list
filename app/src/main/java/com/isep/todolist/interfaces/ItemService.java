package com.isep.todolist.interfaces;

import com.isep.todolist.models.Item;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ItemService {
    @GET("/todos/{todo_id}/items")
    Call<List<Item>> loadItems(@Path("todo_id") String todoId);

    @FormUrlEncoded
    @POST("/todos/{todo_id}/items")
    Call<Item> create(@Path("todo_id") String todo_id, @Field("name") String name);

    @DELETE("/todos/{todo_id}/items/{id}")
    Call<ResponseBody> deleteItem(@Path("todo_id") String todoId, @Path("id") String id);

}
