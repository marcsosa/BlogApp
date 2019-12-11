package com.itla.apiblog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.itla.apiblog.entity.Login;
import com.itla.apiblog.entity.Register;
import com.itla.apiblog.entity.User;
import com.itla.apiblog.service.BlogApiServices;
import com.itla.apiblog.service.SecurityService;

public class Registro extends AppCompatActivity
{
    Button registrar;
    EditText nombre;
    EditText correo;
    EditText password;
    EditText repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_activity);

    registrar = (Button)(findViewById(R.id.BtnRegistrarseAceptar));
    nombre = (EditText)(findViewById(R.id.usuarionombre));
        correo = (EditText)(findViewById(R.id.usuariocorreo));
        password = (EditText)(findViewById(R.id.usuariopass));
        repassword = (EditText)(findViewById(R.id.usuariorepass));

        registrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (nombre.getText().toString().isEmpty())
            {
                nombre.setError("Campo Vacio");
                nombre.requestFocus();
                return;
            }
            if (correo.getText().toString().isEmpty())
            {
                correo.setError("Campo Vacio");
                correo.requestFocus();
                return;
            }        if (password.getText().toString().isEmpty())
            {
                password.setError("Campo Vacio");
                password.requestFocus();
                return;
            }
            if (repassword.getText().toString().isEmpty())
            {
                repassword.setError("Campo Vacio");
                repassword.requestFocus();
                return;
            }
            if (repassword.getText().toString() != password.getText().toString())
            {
                nombre.setError("Clave no es la misma");
                nombre.requestFocus();
                return;
            }


                SecurityService securityService = BlogApiServices
                        .getInstance()
                        .getSecurityService();
                Register register = new Register();
                register.setName(nombre.getText().toString());
                register.setEmail(correo.getText().toString());
                register.setPassword(password.getText().toString());
                Call<User> cuser = securityService.register(register);
                cuser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.i("msj",response.message());
                        if (response.code() == 201) {
                            User user = response.body();
                            Log.i("Registrar", user.toString());
                        } else {
                            Log.i("Registrar","Error en el registro");
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }





    });

    }
}
