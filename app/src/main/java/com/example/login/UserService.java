package com.example.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @GET("api/users")
    Call<List<User>> getData();

    @POST("api/users")
    Call<User> Login(@Body User user);
    @POST("login")
    Call<User> login(@Body User user);
    @POST("/api/users/signup")
    Call<Void> signupUser(@Body User user);

}
