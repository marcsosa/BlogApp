package com.itla.apiblog.service;

import com.itla.apiblog.entity.User;
import com.itla.apiblog.entity.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface UserService {


    @GET("users/{id}")
    Call<Users> getUsers(@Path("id") int id,@Header("Authorization") String authHeader);

    @GET("users/me")
    Call<Users> getMyprofile(@Header("Authorization") String authHeader);
}
