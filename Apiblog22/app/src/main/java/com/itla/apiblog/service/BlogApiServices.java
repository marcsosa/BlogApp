package com.itla.apiblog.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class BlogApiServices {

    private static final String PATH_API = "http://itla.hectorvent.com/api/";
    private static BlogApiServices BAS;
    Retrofit retrofit;

   public BlogApiServices(){

       retrofit = new Retrofit.Builder()
               .baseUrl(PATH_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return;
    }

    public SecurityService getSecurityService(){
        return retrofit.create(SecurityService.class);

    }
    public PostService getPostService(){
        return retrofit.create(PostService.class);

    }
    public UserService getUserService(){
        return retrofit.create(UserService.class);
    }

    public static BlogApiServices getInstance(){
        if (BAS == null){
            BAS = new BlogApiServices();
        }
        return BAS;
    }




}
