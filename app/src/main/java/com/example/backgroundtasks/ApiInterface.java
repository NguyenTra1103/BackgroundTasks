package com.example.backgroundtasks;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("users")
    Call<ArrayList<User>> getAllUsers();
    @GET("users/{id}")
    Call<User> getUsersByID(@Path("id") int id);
    String token = "f7e5488940e369e2edf21409e99abd6951b8a66e53e2f236e7de528f306c83e2";
    @POST("users?access-token=" + token)
    Call<User> addUser(@Body() User user);
    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") int id ,@Body() User user);
    @DELETE("users/{id}")
    Call<User> deleteUser(@Path("id") int id);
}
