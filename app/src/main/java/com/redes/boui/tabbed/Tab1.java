package com.redes.boui.tabbed;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.klinker.android.send_message.Message;
import com.klinker.android.send_message.Settings;
import com.klinker.android.send_message.Transaction;

import java.util.Calendar;

/**
 * Created by Boui on 26/03/2017.
 */

public class Tab1 extends android.support.v4.app.Fragment {
    private Button btnFecha, btnHora, btnEnviar;
    private TextView efecha,ehora;
    private int dia, mes, anio, hora, minutos, idCuando, otrodia;
    private EditText glucosa;
    private Spinner cuando;
    private bd base;
    private final String TAG = "TAB1";
    private boolean validacion = true;
    private MainActivity main;
    Medico medico = null;
    private String mensaje = "";
    private String numero = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_log_glucosa,container,false);
        btnFecha = (Button)view.findViewById(R.id.fechaBtn);
        btnHora = (Button)view.findViewById(R.id.horaBtn);
        efecha = (TextView) view.findViewById(R.id.fecha);
        ehora = (TextView) view.findViewById(R.id.hora);
        btnEnviar = (Button)view.findViewById(R.id.enviarBtn);
        glucosa = (EditText)view.findViewById(R.id.glucosa);
        cuando = (Spinner)view.findViewById(R.id.spinnerAlimentos);
        main = (MainActivity)getActivity();
        //base de datos
        base = new bd(getActivity().getApplicationContext());

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()) {
                    //base de datos
                    idCuando = cuando.getSelectedItemPosition();
                    revisarNivel();
                    insertarbd();
                }
                else
                    Dialog("Por favor, escribe todos los campos");

            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        otrodia = dayOfMonth;
                        efecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        ehora.setText(String.format("%02d:%02d", i, i1));
                    }
                }, hora, minutos, false);
                timePickerDialog.show();
            }

        });



        return view;
    }
    private void insertarbd(){

        Log.d(TAG, otrodia + "/" + (mes+1) +"/" +anio);
        Log.d(TAG,idCuando + " :cuando");
        Log.d(TAG, ehora.getText().toString() + " :hora");
        Log.d(TAG, glucosa.getText().toString() + " :lvl glucosa");
        Log.d(TAG, String.valueOf(main.getIduser()));
        base.insertarRegistro(anio,(mes+1),otrodia,ehora.getText().toString(),idCuando,
                                Integer.parseInt(glucosa.getText().toString()), main.getIduser());
       // base.insertarUsuario(glucosa.getText().toString(),idMedicamento);

        Dialog("Registro de glucosa registrado exitosamente");
        // finish();
        // startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
    }


    public void Dialog(String mensaje) {
        Dialog newFragment = Dialog.newInstance(mensaje);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }
    public void revisarNivel(){
        EnviarEmail em = null;
        int lvlglu = Integer.parseInt(glucosa.getText().toString());
        medico = base.getMedico(main.getIduser());
        Paciente paciente = base.getPacienteShort(main.getIduser());
        String nombreCompleto = "";
        //si obtuvo datos
        if(paciente != null){
            nombreCompleto = paciente.getNom() + " " + paciente.getPat() + " " + paciente.getMat();
        }
        //si es antes de comer
        if(idCuando == 1){
            //si está fuera del límite, enviar mensaje e email al medico
            if(!(lvlglu >= 70 && lvlglu <= 130)){
                //revisar si el paciente tiene un medico registrado
                if(medico != null){
                    Log.d(TAG, "hay medico");
                    //filtrar mensaje dependiendo de nivel alto o bajo
                    if (lvlglu > 70) {
                        em = new EnviarEmail(getActivity().getApplicationContext(),
                                medico.getEmail(), "Alerta de nivel de glucosa alto", "El paciente " + nombreCompleto +
                                " registró un nivel de glucosa de "+lvlglu+ " mg/dl. Atender de inmediato.");
                        //EnviarMensaje(medico.getCelular(), "El paciente " + nombreCompleto +
                        //        " registró un nivel de glucosa alto de "+lvlglu+ " mg/dl");
                        mensaje = "El paciente " + nombreCompleto +
                                " registró un nivel de glucosa alto de "+lvlglu+ " mg/dl";
                        numero = medico.getCelular();
                        sendMessage();
                        em.execute();
                    }else{
                        em = new EnviarEmail(getActivity().getApplicationContext(),
                                medico.getEmail(), "Alerta de nivel de glucosa bajo", "El paciente " + nombreCompleto +
                                " registró un nivel de glucosa de "+lvlglu+ " mg/dl. Atender de inmediato.");
                        mensaje = "El paciente " + nombreCompleto +
                                " registró un nivel de glucosa bajo de "+lvlglu+ " mg/dl";
                        numero = medico.getCelular();
                        sendMessage();
                        em.execute();
                    }
                }else
                    Dialog("Por favor regístre su médico.");
            }
            //si es fespués de comer
        }else{
            //si está fuera del límite, enviar mensaje e email al medico
            if(!(lvlglu >= 70 && lvlglu <= 180)){
                //revisar si el paciente tiene un medico registrado
                if(medico != null){
                    Log.d(TAG, "hay medico");
                    if (lvlglu > 70) {
                        em = new EnviarEmail(getActivity().getApplicationContext(),
                                medico.getEmail(), "Alerta de nivel de glucosa alto", "El paciente " + nombreCompleto +
                                " registró un nivel de glucosa de "+lvlglu+ " mg/dl. Atender de inmediato.");
                        em.execute();
                        mensaje = "El paciente " + nombreCompleto +
                                " registró un nivel de glucosa alto de "+lvlglu+ " mg/dl";
                        numero = medico.getCelular();
                        sendMessage();
                    }else{
                        em = new EnviarEmail(getActivity().getApplicationContext(),
                                medico.getEmail(), "Alerta de nivel de glucosa bajo", "El paciente " + nombreCompleto +
                                " registró un nivel de glucosa de "+lvlglu+ " mg/dl. Atender de inmediato.");
                        mensaje = "El paciente " + nombreCompleto +
                                " registró un nivel de glucosa bajo de "+lvlglu+ " mg/dl";
                        numero = medico.getCelular();
                        sendMessage();
                        em.execute();
                    }
                }else
                    Dialog("Por favor regístre su médico.");
            }
        }

    }

    public void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                com.klinker.android.send_message.Settings sendSettings = new com.klinker.android.send_message.Settings();

                sendSettings.setUseSystemSending(true);

                Transaction transaction = new Transaction(getActivity().getApplicationContext(), sendSettings);

                Message message = new Message(mensaje, numero);


                transaction.sendNewMessage(message, Transaction.NO_THREAD_ID);
            }
        }).start();
    }
    private boolean validar(){
        if (TextUtils.isEmpty(glucosa.getText().toString())){
            glucosa.setError("Escribe nivel de glucosa");
            return false;
        }

        if (efecha.getText() != null && efecha.getText().equals("")){
            ((TextView) getView().findViewById(R.id.tvfecha)).requestFocus();
            ((TextView) getView().findViewById(R.id.tvfecha)).setError("Selecciona fecha");
            return false;
        }
        else{
            ((TextView) getView().findViewById(R.id.tvfecha)).setError(null);
            validacion = true;
        }

        if (ehora.getText() != null && ehora.getText().equals("")){
            ((TextView) getView().findViewById(R.id.tvhora)).requestFocus();
            ((TextView) getView().findViewById(R.id.tvhora)).setError("Selecciona hora");
            return false;
        }
        else{
            ((TextView) getView().findViewById(R.id.tvhora)).setError(null);
            validacion = true;
        }

        if (cuando.getSelectedItemPosition() == 0){
            ((TextView) getView().findViewById(R.id.textViewAlimentos)).requestFocus();
            ((TextView) getView().findViewById(R.id.textViewAlimentos)).setError("Selecciona una opción");
            Log.d(TAG, "v med");
            return false;
        }else{
            ((TextView) getView().findViewById(R.id.textViewAlimentos)).setError(null);
            validacion = true;

        }
        return  validacion;
    }
}
