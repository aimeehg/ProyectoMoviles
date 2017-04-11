package com.redes.boui.tabbed;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Boui on 26/03/2017.
 */

public class Tab1 extends android.support.v4.app.Fragment {
    private Button btnFecha, btnHora;
    private TextView efecha,ehora;
    private int dia, mes, anio, hora,minutos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_log_glucosa,container,false);
        btnFecha = (Button)view.findViewById(R.id.fechaBtn);
        btnHora = (Button)view.findViewById(R.id.horaBtn);
        efecha = (TextView) view.findViewById(R.id.fecha);
        ehora = (TextView) view.findViewById(R.id.hora);

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
                        efecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year );
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        ehora.setText(String.format("%02d:%02d", i, i1));
                    }
                },hora,minutos,false);
                timePickerDialog.show();
            }
        });

        return view;
    }
}
