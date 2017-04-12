package com.redes.boui.tabbed;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Boui on 26/03/2017.
 */

public class Tab4 extends android.support.v4.app.Fragment {
    private EditText nombre, apellidos, direccion, email, celular;
    private Button btnEnviar;
    private bd base;
    private final String TAG = "TAB4";
    private boolean validacion = true;
    private boolean existe = false;
    private MainActivity main;
    Medico medico = null;
    private int id_med, id_pac;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4_medico,container,false);

            nombre = (EditText)view.findViewById(R.id.nombreMedico);
            apellidos = (EditText)view.findViewById(R.id.apellidosMedico);
            direccion = (EditText)view.findViewById(R.id.direccionConsultorio);
            email = (EditText)view.findViewById(R.id.emailMedico);
            celular = (EditText)view.findViewById(R.id.celularMedico);
            btnEnviar = (Button)view.findViewById(R.id.enviarBtnMedico);
        main = (MainActivity)getActivity();
        //base de datos
        base = new bd(getActivity().getApplicationContext());
        medico = base.getMedico(main.getIduser());
        //si existe medico
        if(medico != null){
            Log.d(TAG, "hay");
            existe = true;
            btnEnviar.setText("Editar");
            llenarCampos();
            id_med = medico.getId_med();
            id_pac = medico.getIdPaciente();
        }else
            Log.d(TAG, "no hay");



        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (validar()) {
                        //base de datos
                        if (!existe)
                            insertarbd();
                        else
                            editarCampos();
                    } else
                        Dialog("Por favor, escribe todos los campos");

            }
        });
        return view;
    }
    private void llenarCampos(){
        //Dialog("llenar campos");
        Log.d(TAG, "llenar campos");
        nombre.setText(medico.getNombre());
        apellidos.setText(medico.getApellidos());
        direccion.setText(medico.getDirConsultorio());
        email.setText(medico.getEmail());
        celular.setText(medico.getCelular());
    }
    private void editarCampos(){

        Medico medico = new Medico(id_med,nombre.getText().toString(), apellidos.getText().toString(),
                direccion.getText().toString(), email.getText().toString(), celular.getText().toString(),
                id_pac);
        base.editarMedico(medico);
        Dialog("Datos de médico editados");
    }
    private void insertarbd(){
        //Log.d(TAG,dia + "/" + mes +"/" +anio);
        Log.d(TAG, String.valueOf(main.getIduser()));
             //   Integer.parseInt(glucosa.getText().toString()), main.getIduser());


            base.insertarMedico(nombre.getText().toString(), apellidos.getText().toString(),
                    direccion.getText().toString(), celular.getText().toString(),
                    email.getText().toString(), main.getIduser());
            Dialog("Registro de médico exitoso");

        // finish();
        // startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
    }
    public void Dialog(String mensaje) {
        Dialog newFragment = Dialog.newInstance(mensaje);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }

    private boolean validar(){
        if (TextUtils.isEmpty(nombre.getText().toString())){
            nombre.setError("Escribe el nombre");
            return false;
        }
        if (TextUtils.isEmpty(apellidos.getText().toString())){
            apellidos.setError("Escribe los apellidos");
            return false;
        }
        if (TextUtils.isEmpty(direccion.getText().toString())){
            direccion.setError("Escribe la dirección");
            return false;
        }
        if (TextUtils.isEmpty(email.getText().toString())){
            email.setError("Escribe el e-mail");
            return false;
        }
        if (TextUtils.isEmpty(celular.getText().toString())){
            celular.setError("Escribe el celular");
            return false;
        }
        return  validacion;
    }
}
