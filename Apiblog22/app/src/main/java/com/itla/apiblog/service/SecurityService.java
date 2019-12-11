package com.itla.apiblog.service;



import com.itla.apiblog.entity.Login;
import com.itla.apiblog.entity.Register;
import com.itla.apiblog.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SecurityService {

 //
    @POST("login")
    public Call<User> login(@Body Login login);

    @POST("register")
    public Call<User> register(@Body Register register);

    @DELETE("logout")
    public Call<User> logout(@Header("Authorization") String authHeader);

}

