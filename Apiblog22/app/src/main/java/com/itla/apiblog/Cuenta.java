package com.itla.apiblog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itla.apiblog.entity.Post;
import com.itla.apiblog.entity.Users;
import com.itla.apiblog.service.BlogApiServices;
import com.itla.apiblog.service.PostService;
import com.itla.apiblog.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cuenta extends AppCompatActivity implements AdaptadorPost.OnNoteListener {
    int id;
    TextView nombre;
    TextView email;
    TextView posts ;
    TextView fecha;
    RecyclerView recyclerView;
String  token;
    List<Post> listpost;
    private static final String TAG = "Blog";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.micuenta);
        nombre = (TextView) (findViewById(R.id.Perfilname));
        email = (TextView) (findViewById(R.id.PerfilCorreo));
        fecha = (TextView) (findViewById(R.id.PerfilFechaCreacion));
        posts = (TextView) (findViewById(R.id.PerfilNumPost));


        UserService userService = (UserService) BlogApiServices
                .getInstance()
                .getUserService();
        SharedPreferences pref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        token = pref.getString("Api", "");
        id = pref.getInt("id", 0);
        //Buscar Mi Usuario por shared//
        Call<Users> cuser = userService.getUsers(id, "Bearer " + token);
        cuser.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                if (response.code() == 200) {
                    Users users = response.body();
                    nombre.setText(users.getName());
                    email.setText(users.getEmail());
                    posts.setText(String.valueOf(users.getPosts()));
                    Date creacion = new Date(users.getCreatedAt());
                    fecha.setText(String.valueOf(creacion));
                    Log.i("User", users.toString());
                } else {
                    Log.i("User", "Error");
                    Log.i("User", response.message());
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        recyclerView = findViewById(R.id.recyclerpostuser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LlenarLayout(this);


    }

    private void LlenarLayout(final AdaptadorPost.OnNoteListener onNoteListener) {
        PostService postService = (PostService) BlogApiServices
                .getInstance()
                .getPostService();
        Call<List<Post>> cpost = postService.llamarPost("Bearer " + token);
        cpost.enqueue(new Callback<List<Post>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    listpost = response.body();
                    Log.i("post", response.toString());
                    List<Post> profilepost = new ArrayList<>();
                    for (Post busca : listpost) {
                        if (busca.getUserId() == id) {
                            profilepost.add(busca);
                        }
                    }
                    listpost = profilepost;
                    AdaptadorPost adaptadorPost = new AdaptadorPost(listpost, onNoteListener);
                    recyclerView.setAdapter(adaptadorPost);


                } else {
                    Log.i("Post", "ERROL!" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }

        });
    }

    @Override
    public void onNoteClick(int position) {
        int postid = listpost.get(position).getId();
        listpost.get(position);
        Intent intent = new Intent(this,PostActivity.class);
        intent.putExtra("posi",String.valueOf(position));
        intent.putExtra("id",postid);
        startActivity(intent);
       Log.i(TAG, "clicked"+position+postid);

    }
    @Override
    public void onResume(){
        super.onResume();
        LlenarLayout(this);
        Log.i(TAG, "DALEE on resume");

    }

}
