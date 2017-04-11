package com.redes.boui.tabbed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Boui on 26/03/2017.
 */

public class LoginActivity extends Activity{
    protected EditText usuario, pass;
    protected Button aceptar, cancelar, registro;
    protected boolean validacion = true;
    protected bd base;
    protected Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        usuario = (EditText)findViewById(R.id.Usuario);
        pass = (EditText)findViewById(R.id.Password);

        aceptar = (Button)findViewById(R.id.btnAceptar);
        cancelar = (Button)findViewById(R.id.btnCancelar);
        registro = (Button)findViewById(R.id.btnRegistro);

        base = new bd(getApplicationContext());
        session = new Session(getApplicationContext());
        if(session.loggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        //cuando se hace click al btnAceptar
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()) {
                    //autenticar
                    int id = Login(usuario.getText().toString(), pass.getText().toString());
                    if (id == -1)
                        Dialog("Usuario o contraseña incorrectos");
                    else {
                        session.setLoggedIn(true, id);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }
                else
                    Dialog("Por favor, escribe todos los campos");

            }
        });
        //cuando se hace click al btnCancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setText("");
                pass.setText("");
            }
        });
        //cuando se hace click al btnRegistro
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    public int Login(String usuario, String password){
        return base.Login(usuario, password);
    }

    private boolean validar(){
        if (TextUtils.isEmpty(usuario.getText().toString())){
            usuario.setError("Escribe tu usuario");
            return false;
        }

        if (TextUtils.isEmpty(pass.getText().toString())){
            pass.setError("Escribe tu contraseña");
            return false;
        }

        return validacion;
    }

    public void Dialog(String mensaje) {
        Dialog newFragment = Dialog.newInstance(mensaje);
        newFragment.show(getFragmentManager(), "dialog");
    }


}
