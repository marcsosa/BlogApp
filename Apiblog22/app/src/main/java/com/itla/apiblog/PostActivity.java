package com.itla.apiblog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itla.apiblog.entity.CrearComentario;
import com.itla.apiblog.entity.Comment;
import com.itla.apiblog.entity.Post;
import com.itla.apiblog.service.BlogApiServices;
import com.itla.apiblog.service.PostService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    private static final String TAG = "NOTAMOENGENTE";
    Handler handler = new Handler(); // En esta zona creamos el objeto Handler
    private ProgressBar _progressBar;
    TextView titulo, fecha, contenido, likes, comentarios, visitas, tags, nombreusuario;
    RecyclerView recyclerComentarios;
    Post post;
    Button like;
    Button comentar;
    int id;
    String token;
    List<Comment> comments;
    EditText edcomentar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postsact);
        titulo = findViewById(R.id.pvtitulo);
        comentar = findViewById(R.id.btncomentar);
        fecha = findViewById(R.id.pvfecha);
        contenido = findViewById(R.id.pvcontenido);
        likes = findViewById(R.id.pvlikes);
        comentarios = findViewById(R.id.pvnumcomentario);
        visitas = findViewById(R.id.pvVisitas);
        edcomentar = findViewById(R.id.edbox);
        tags = findViewById(R.id.pvtags);
        recyclerComentarios = findViewById(R.id.recyclercomentarios);
        nombreusuario = findViewById(R.id.pvnombreusuario);
        like = findViewById(R.id.buttonlike);
        SharedPreferences pref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        token = pref.getString("Api", "");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
        }
        Context context = this;
        recyclerComentarios.setHasFixedSize(true);
        recyclerComentarios.setLayoutManager(new LinearLayoutManager(this));
        Llenarview();
        Callcomments();
        onMapReady();
        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edcomentar.getText().toString() == null) {
                    new AlertDialog.Builder(PostActivity.this)
                            .setMessage("Comentario Vacio")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PostActivity.super.onBackPressed();
                                }
                            })
                            .show();
                } else {
                    PostService postService = (PostService) BlogApiServices
                            .getInstance()
                            .getPostService();
                    final CrearComentario crearComentario = new CrearComentario();
                    crearComentario.setBody(edcomentar.getText().toString());
                    Call<Void> call = postService.crearComentario(id, crearComentario, "Bearer " + token);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i(TAG, "CrearComentario Codigo: " + response.code());
                            edcomentar.setText("");
                            Callcomments();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i(TAG, "Fallido: ");

                        }
                    });
                }
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!post.isLiked()) {
                    PostService postService = (PostService) BlogApiServices
                            .getInstance()
                            .getPostService();
                    Call<Void> call = postService.DarLike(id, "Bearer " + token);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            like.setBackgroundResource(R.drawable.ic_thumb_up_red_24dp);
                            post.setLiked(true);
                            post.setLikes(post.getLikes() + 1);
                            likes.setText(post.getLikes() + " Likes");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                } else {
                    PostService postService = (PostService) BlogApiServices
                            .getInstance()
                            .getPostService();
                    Call<Void> call = postService.EliminarLike(id, "Bearer " + token);
                    call.enqueue(new Callback<Void>() {
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i(TAG, "Codigo  en like2: " + response.code());
                            like.setBackgroundResource(R.drawable.ic_thumb_up_black_24dp);
                            post.setLiked(false);
                            post.setLikes(post.getLikes() - 1);
                            likes.setText(post.getLikes() + " Likes");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i(TAG, "Fallido" + t.getMessage());
                        }
                    });


                }
            }
        });

    }

    public void Callcomments() {
        PostService postService = (PostService) BlogApiServices
                .getInstance()
                .getPostService();
        Call<List<Comment>> call = postService.getcomment(id, "Bearer " + token);
        call.enqueue(new Callback<List<Comment>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                Log.i(TAG, "Code: " + response.code());
                comments = response.body();
                Collections.reverse(comments);
                AdaptadorComentarios adaptadorComentarios = new AdaptadorComentarios(comments);
                recyclerComentarios.setAdapter(adaptadorComentarios);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    public void Llenarview() {
        PostService postService = (PostService) BlogApiServices
                .getInstance()
                .getPostService();
        Call<Post> call = postService.llamarPostID(id, "Bearer " + token);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                post = response.body();
                titulo.setText(post.getTitle());
                contenido.setText(post.getBody());
                likes.setText(post.getLikes() + " Likes");
                comentarios.setText(post.getComments() + " Comentarios");
                visitas.setText(post.getViews() + " Visitas");
                Date creacion = new Date(post.getCreatedAt());
                fecha.setText(String.valueOf(creacion));
                tags.setText(Arrays.toString(post.getTags()));
                nombreusuario.setText(post.getUserName());
                if (post.isLiked()) {
                    like.setBackgroundResource(R.drawable.ic_thumb_up_red_24dp);
                } else {
                    like.setBackgroundResource(R.drawable.ic_thumb_up_black_24dp2);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.i(TAG, "No lleno el llenar view");
            }
        });
    }


    @Override
    public void onBackPressed() {
        PostActivity.super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private final int TIEMPO = 30000;

    public void onMapReady() {

        ejecutarTarea();

    }

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {

                // función a ejecutar
                Callcomments();
                ; // función para refrescar la ubicación, creada en otra línea de código

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }
}
