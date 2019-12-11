package com.itla.apiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.itla.apiblog.entity.Login;
import com.itla.apiblog.entity.User;
import com.itla.apiblog.service.BlogApiServices;
import com.itla.apiblog.service.SecurityService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button registrar;
    String respuesta;
    EditText email;
    EditText password;
    Button aceptar2;
    String valor;
    String valor2;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        registrar = (Button) (findViewById(R.id.btnnull));
        aceptar2 = (Button) (findViewById(R.id.btnLogin));
        email = (EditText) (findViewById(R.id.correo));
        password = (EditText) (findViewById(R.id.password));
        SharedPreferences pref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        valor = pref.getString("Api", "");
        valor2 = pref.getString("Correo", "");
        if (valor == ""){
            Toast.makeText(getApplicationContext(), " Sin Token Inicie Session: " + valor2 + valor, Toast.LENGTH_SHORT).show();
        }
            else{
            Intent intent = new Intent(MainActivity.this, Inicio.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        aceptar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecurityService securityService = BlogApiServices
                        .getInstance()
                        .getSecurityService();
                Login login = new Login();
                login.setEmail(email.getText().toString());
                login.setPassword(password.getText().toString());
                Call<User> cuser = securityService.login(login);
                cuser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.code() == 201) {
                            User user = response.body();
                            Log.i("login", user.toString());
                            SharedPreferences pref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
                            String correo = email.getText().toString();
                            String tokenapi = user.getToken();
                            int id = user.getId();
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("Correo", correo);
                            editor.putInt("id", id);
                            editor.putString("Api", tokenapi);
                            editor.commit();
                            pantallaprincipal();
                        } else {
                            Log.i("LOGIN", "Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }

            private void pantallaprincipal() {
                Toast.makeText(getApplicationContext(), " Bienvenido : " + valor2 , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Inicio.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });


    }

}


//Me funciona mejor otro//
   /** private void guardarpreferencias() {
        SharedPreferences pref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);

        String tokenapi = respuesta;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Correo", correo);
        editor.putString("id", id);
        editor.putString("Api", tokenapi);
        editor.commit();
   } **/
