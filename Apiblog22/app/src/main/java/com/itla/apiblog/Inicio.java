package com.itla.apiblog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.itla.apiblog.entity.Post;
import com.itla.apiblog.service.BlogApiServices;
import com.itla.apiblog.service.PostService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inicio extends AppCompatActivity implements AdaptadorPost.OnNoteListener {

    private RecyclerView.LayoutManager layoutManager;
    Button prueba;
    TextView resultado2;
    RecyclerView recyclerView;
    AdaptadorPost adapter;
    List<Post> posts;
    List<Post> Busquedaposts;

    Button like;
    String token;
    String busqueda;
    private static final String TAG = "Blog";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        like = (Button) (findViewById(R.id.likebutton));
        recyclerView = findViewById(R.id.recyclerpost);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Context context = this;
        SharedPreferences pref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        token = pref.getString("Api", "");
        if (getIntent().hasExtra("buscame") && savedInstanceState == null) {
            busqueda = getIntent().getExtras().getString("buscame");
        }

        LlenarRecycler(this);
    }


    private void LlenarRecycler(final AdaptadorPost.OnNoteListener onNoteListener) {

        final PostService postService = (PostService) BlogApiServices
                .getInstance()
                .getPostService();
        Call<List<Post>> cpost = postService.llamarPost("Bearer " + token);
        cpost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts = response.body();
                    Busquedaposts = response.body();
                    Log.d(TAG, " llego" + busqueda + Busquedaposts);
                    if (busqueda == null) {
                        AdaptadorPost adaptadorPost2 = new AdaptadorPost(posts, onNoteListener);
                        recyclerView.setAdapter(adaptadorPost2);
                    } else {

                        List<Post> buscapost = new ArrayList<>();
                        for (Post busca : Busquedaposts) {
                            Log.i(TAG, "" + busca.getTitle() + Arrays.toString(busca.getTags()));

                            if (busca.getTitle().toLowerCase().contains(busqueda.toLowerCase()) | Arrays.toString(busca.getTags()).toLowerCase().contains(busqueda.toLowerCase())) {
                                buscapost.add(busca);
                            }
                        }
                        if (!buscapost.contains(busqueda)) {
                            posts = buscapost;
                        } else {
                            posts = response.body();
                        }
                        AdaptadorPost adaptadorPost2 = new AdaptadorPost(posts, onNoteListener);
                        recyclerView.setAdapter(adaptadorPost2);

                    }
                } else {
                }
            }


            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                resultado2.setVisibility(View.VISIBLE);
                resultado2.setText(t.getMessage());
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchIcon = menu.findItem(R.id.search);
        final MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                findViewById(android.R.id.content).invalidate();
                Intent intent = new Intent(getIntent());
                intent.putExtra("buscame", query);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Cerrar sesion**/
        if (id == R.id.action_favorite) {
            SharedPreferences pref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            finish();
            Toast.makeText(getApplicationContext(), " Adios ", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.action_usuario) {
            Intent intent = new Intent(this, Cuenta.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            return true;
        }
        if (id == R.id.agregarpost) {
            Intent intent = new Intent(this, NewPostActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            return true;
        }


        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onNoteClick(int position) {
        posts.get(position);
        int postid = posts.get(position).getId();
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra("posi", String.valueOf(position));
        intent.putExtra("id", postid);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        return;

    }

    @Override
    public void onResume() {
        super.onResume();
        LlenarRecycler(this);
    }
}
