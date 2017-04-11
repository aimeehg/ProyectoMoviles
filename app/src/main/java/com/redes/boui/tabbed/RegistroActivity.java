package com.redes.boui.tabbed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Boui on 26/03/2017.
 */

public class RegistroActivity extends Activity{
    protected EditText nombre, paterno, materno, direccion, edad, peso, altura, usuario, pass;
    protected RadioGroup genero;
    protected Spinner medicamento;
    protected Button aceptar, cancelar;
    protected int idGenero, idMedicamento;
    protected boolean validacion = true;
    protected final String TAG = "RegistroActivity";
    protected bd base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //variables del registro
        nombre = (EditText)findViewById(R.id.Nombre);
        paterno = (EditText)findViewById(R.id.Paterno);
        materno = (EditText)findViewById(R.id.Materno);
        direccion = (EditText)findViewById(R.id.Direccion);
        edad = (EditText)findViewById(R.id.Edad);
        peso = (EditText)findViewById(R.id.Peso);
        altura = (EditText)findViewById(R.id.Altura);
        usuario = (EditText)findViewById(R.id.UsuarioR);
        pass = (EditText)findViewById(R.id.PasswordR);
        genero = (RadioGroup)findViewById(R.id.radioGenero);
        medicamento = (Spinner)findViewById(R.id.spinnerMedicamento);
        //botones
        aceptar = (Button)findViewById(R.id.btnAceptar);
        cancelar = (Button)findViewById(R.id.btnCancelar);

        genero.clearCheck();
        //base de datos
        base = new bd(getApplicationContext());

        //cuando se hace click al btnAceptar
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()) {
                    //base de datos
                    insertarbd();
                }
                else
                    Dialog("Por favor, escribe todos los campos");

            }
        });
        //cuando se hace click al btnCancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarFormulario((ViewGroup)findViewById(R.id.activity_registro));
                genero.clearCheck();
                medicamento.setSelection(0);
                Log.d(TAG, "Borrar campos");
            }
        });

    }
    private void insertarbd(){
        idGenero = genero.getCheckedRadioButtonId();
        idMedicamento = medicamento.getSelectedItemPosition();
        int eda = Integer.parseInt(edad.getText().toString());
        int pes = Integer.parseInt(peso.getText().toString());
        float alt = Float.parseFloat(altura.getText().toString());

        base.insertarUsuario(nombre.getText().toString(),paterno.getText().toString(),materno.getText().toString(),
                direccion.getText().toString(),eda,pes,alt,usuario.getText().toString(),pass.getText().toString(),
                idGenero,idMedicamento);
        Dialog("Paciente registrado exitosamente");
       // finish();
       // startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
    }
    private boolean validar(){
        if (TextUtils.isEmpty(nombre.getText().toString())){
            nombre.setError("Escribe tu nombre");
            return false;
        }

        if (TextUtils.isEmpty(paterno.getText().toString())){
            paterno.setError("Escribe tu apellido paterno");
            return false;
        }
        if (TextUtils.isEmpty(materno.getText().toString())){
            materno.setError("Escribe tu apellido materno");
            return false;
        }
        if (TextUtils.isEmpty(direccion.getText().toString())){
            direccion.setError("Escribe tu apellido dirección");
            return false;
        }
        if (TextUtils.isEmpty(edad.getText().toString())){
            edad.setError("Escribe tu edad");
            return false;
        }
        if (TextUtils.isEmpty(peso.getText().toString())){
            peso.setError("Escribe tu peso");
            return false;
        }
        if (TextUtils.isEmpty(altura.getText().toString())){
            altura.setError("Escribe tu altura");
            return false;
        }
        if (TextUtils.isEmpty(usuario.getText().toString())){
            usuario.setError("Escribe tu nombre de usuario");
            return false;
        }

        if (TextUtils.isEmpty(pass.getText().toString())){
            pass.setError("Escribe tu contraseña");
            return false;
        }

        if(genero.getCheckedRadioButtonId() <= 0){
            ((TextView) findViewById(R.id.txtViewGenero)).requestFocus();
            ((TextView) findViewById(R.id.txtViewGenero)).setError("Selecciona tu género");
            Log.d(TAG, "v gen");
            return false;
        }else{
            ((TextView) findViewById(R.id.txtViewGenero)).setError(null);
            validacion = true;
        }

        if (medicamento.getSelectedItemPosition() == 0){
            ((TextView) findViewById(R.id.textViewMedicamento)).requestFocus();
            ((TextView) findViewById(R.id.textViewMedicamento)).setError("Selecciona tu medicamento");
            Log.d(TAG, "v med");
            return false;
        }else{
            ((TextView) findViewById(R.id.textViewMedicamento)).setError(null);
            validacion = true;

        }
        return  validacion;
    }

    public void Dialog(String mensaje) {
        Dialog newFragment = Dialog.newInstance(mensaje);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private void borrarFormulario(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                borrarFormulario((ViewGroup)view);
        }
    }
}
