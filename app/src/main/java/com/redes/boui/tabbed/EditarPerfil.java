package com.redes.boui.tabbed;

import android.app.Activity;
import android.net.Uri;
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
 * Created by Boui on 23/04/2017.
 */

public class EditarPerfil extends Activity {
    protected EditText nombre, paterno, materno, direccion, edad, peso, altura, usuario, pass;
    protected RadioGroup genero;
    protected Spinner medicamento;
    protected Button aceptar, cancelar, iniciar;
    protected int idGenero, idMedicamento, idPaciente;
    protected boolean validacion = true;
    protected final String TAG = "EditarActivity";
    protected bd base;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            //TODO here get the string stored in the string variable and do
            idPaciente = bundle.getInt("id_user");
        }
        //variables del registro
        nombre = (EditText)findViewById(R.id.NombreE);
        paterno = (EditText)findViewById(R.id.PaternoE);
        materno = (EditText)findViewById(R.id.MaternoE);
        direccion = (EditText)findViewById(R.id.DireccionE);
        edad = (EditText)findViewById(R.id.EdadE);
        peso = (EditText)findViewById(R.id.PesoE);
        altura = (EditText)findViewById(R.id.AlturaE);
        usuario = (EditText)findViewById(R.id.UsuarioE);
        pass = (EditText)findViewById(R.id.PasswordE);
        genero = (RadioGroup)findViewById(R.id.radioGeneroE);
        medicamento = (Spinner)findViewById(R.id.spinnerMedicamentoE);
        //botones
        aceptar = (Button)findViewById(R.id.btnAceptarE);
        cancelar = (Button)findViewById(R.id.btnCancelarE);
        genero.clearCheck();
        base = new bd(getApplicationContext());
        llenarCampos();
        //base de datos



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
        finish();
            }
        });


    }
    private void llenarCampos(){
            Paciente pac = base.getPaciente(idPaciente);
            if (pac != null) {
                nombre.setText(pac.getNom());
                paterno.setText(pac.getPat());
                materno.setText(pac.getMat());
                direccion.setText(pac.getDir());
                edad.setText(Integer.toString(pac.getEdad()));
                peso.setText(Integer.toString(pac.getPeso()));
                altura.setText(String.valueOf(pac.getAlt()));
                usuario.setText(pac.getUsu());
                pass.setText(pac.getPass());
                genero.check(pac.getGen());
                medicamento.setSelection(pac.getMed());
            }else
                Log.d("EditarP", "NO HAY PACIENTE KHA");

    }
    private void insertarbd(){

        idGenero = genero.getCheckedRadioButtonId();
        idMedicamento = medicamento.getSelectedItemPosition();
        int eda = Integer.parseInt(edad.getText().toString());
        int pes = Integer.parseInt(peso.getText().toString());
        float alt = Float.parseFloat(altura.getText().toString());
        Paciente paciente = new Paciente(idPaciente,nombre.getText().toString(),paterno.getText().toString(),materno.getText().toString(),
                direccion.getText().toString(),eda,pes,alt,usuario.getText().toString(),pass.getText().toString(),
                idGenero,idMedicamento);
        base.editarPaciente(paciente);
        Dialog("Paciente editado exitosamente");
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
            ((TextView) findViewById(R.id.txtViewGeneroE)).requestFocus();
            ((TextView) findViewById(R.id.txtViewGeneroE)).setError("Selecciona tu género");
            Log.d(TAG, "v gen");
            return false;
        }else{
            ((TextView) findViewById(R.id.txtViewGeneroE)).setError(null);
            validacion = true;
        }

        if (medicamento.getSelectedItemPosition() == 0){
            ((TextView) findViewById(R.id.textViewMedicamentoE)).requestFocus();
            ((TextView) findViewById(R.id.textViewMedicamentoE)).setError("Selecciona tu medicamento");
            Log.d(TAG, "v med");
            return false;
        }else{
            ((TextView) findViewById(R.id.textViewMedicamentoE)).setError(null);
            validacion = true;

        }
        return  validacion;
    }

    public void Dialog(String mensaje) {
        Dialog newFragment = Dialog.newInstance(mensaje);
        newFragment.show(getFragmentManager(), "dialog");
    }
}
