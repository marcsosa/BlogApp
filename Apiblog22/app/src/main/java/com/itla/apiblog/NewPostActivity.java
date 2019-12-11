package com.itla.apiblog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.itla.apiblog.entity.CrearComentario;
import com.itla.apiblog.entity.CrearPost;
import com.itla.apiblog.entity.Post;
import com.itla.apiblog.service.BlogApiServices;
import com.itla.apiblog.service.PostService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostActivity extends AppCompatActivity {
    private static final String TAG = "NEWPOST";

    EditText contenido, titulo,tags;
    Button guardar, cancelar;
    List<String> separador;
String token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpost);

        contenido = findViewById(R.id.postedbox);
        titulo = findViewById(R.id.postededittitulo);
        tags = findViewById(R.id.postedtag);
        guardar = findViewById(R.id.btnpost);
        cancelar = findViewById(R.id.btnback);
        SharedPreferences pref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        token = pref.getString("Api", "");
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = tags.getText().toString();
                String[] parts = string.split(",");
                List<String> al = new ArrayList<String>();
                al = Arrays.asList(parts);
                separador = al;


                if (contenido.getText().toString().isEmpty())
                {
                    contenido.setError("Campo Vacio");
                    contenido.requestFocus();
                    return;

                }

                if (tags.getText().toString().isEmpty())
                {
                    tags.setError("Campo Vacio");
                    tags.requestFocus();
                    return;
                }

                if (titulo.getText().toString().isEmpty())
                {
                    titulo.setError("Campo Vacio");
                    titulo.requestFocus();
                    return;
                }
                final CrearPost crearPost = new CrearPost();
                String[] tagtag = separador.toArray(new String[0]);

                crearPost.setBody(contenido.getText().toString());
                crearPost.setTitle(titulo.getText().toString());
                crearPost.setTags(tagtag);

                PostService postService = (PostService) BlogApiServices
                        .getInstance()
                        .getPostService();
                Call<Post> call = postService.crearpost(crearPost, "Bearer " + token);
                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        Log.d(TAG, "Creacion "+ response.code());
                    contenido.setText("");
                    tags.setText("");
                    titulo.setText("");
                        Toast.makeText(getApplicationContext(), "Post Creado" , Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });


            }
        });

cancelar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        NewPostActivity.super.onBackPressed();
    }
});
    }
}
