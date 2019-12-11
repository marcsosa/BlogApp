package com.itla.apiblog.service;


import com.itla.apiblog.entity.CrearComentario;
import com.itla.apiblog.entity.Comment;
import com.itla.apiblog.entity.CrearPost;
import com.itla.apiblog.entity.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface PostService {


    @GET("post")
    Call<List<Post>> llamarPost(@Header("Authorization") String authHeader);

    @GET("post/{id}")
    Call<Post> llamarPostID(@Path("id") int id, @Header("Authorization") String authHeader);

    @PUT("post/{id}/like")
    Call<Void> DarLike(@Path("id") int id, @Header("Authorization") String authHeader);

    @DELETE("post/{id}/like")
    Call<Void> EliminarLike(@Path("id") int id, @Header("Authorization") String authHeader);

    @GET("post/{id}/comment")
    Call<List<Comment>> getcomment(@Path("id") int id,@Header("Authorization") String authHeader);

    @POST("post/{id}/comment")
    Call<Void> crearComentario(@Path("id") int id, @Body CrearComentario crearComentario, @Header("Authorization") String authHeader);

    @POST("post")
    Call<Post> crearpost(@Body CrearPost crearPost,@Header("Authorization") String authHeader);
}
