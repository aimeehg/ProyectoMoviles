package com.redes.boui.tabbed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boui on 26/03/2017.
 */


    public class Tab2 extends android.support.v4.app.Fragment {
        private Spinner mes;
        private MainActivity main;
        private bd base;
        private int idMes;
        Tabla tabla = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_tabla,container,false);
        tabla = new Tabla(getActivity(), (TableLayout)view.findViewById(R.id.tabla));

        mes = (Spinner)view.findViewById(R.id.spinnerMes);
        base = new bd(getActivity().getApplicationContext());
        main = (MainActivity)getActivity();
       // medico = base.getMedico(main.getIduser());
        mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                tabla.eliminarTabla();

                Log.d("tablita","seleccionado");

                idMes = mes.getSelectedItemPosition();
                if(idMes != 0) {
                    Log.d("tablita", String.valueOf(idMes));
                    List<RegistroGlucosa> reg = base.getRegistros(main.getIduser(), String.valueOf(idMes));
                    if (reg.isEmpty()) {
                        Log.d("tablita", "nadaaaaa");
                        Dialog("No hay resultados para el mes seleccionado.");
                    } else {
                        tabla.agregarCabecera(R.array.cabecera_tabla);
                        Log.d("tablita", "algooooo");
                        for (RegistroGlucosa todo : reg) {
                            ArrayList<String> elementos = new ArrayList<String>();

                            elementos.add(todo.getDd() + "/" + todo.getMm() + "/" + todo.getYyyy());
                            elementos.add(todo.getHh());
                            if (Integer.parseInt(todo.getCuando()) == 1)
                                elementos.add("Antes");
                            else
                                elementos.add("Después");

                            elementos.add(String.valueOf(todo.getNivel()) + " mg/dl");

                            tabla.agregarFilaTabla(elementos);
                            Log.d("REGISTROS", String.valueOf(
                                    "Fecha: " + todo.getDd() + "/" + todo.getMm() + "/" + todo.getYyyy()
                                            + " Hora: " + todo.getHh() + " Antes/Después: " + todo.getCuando()
                                            + " Nivel: " + todo.getNivel()));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Log.d("tablita","no seleccionado");
            }

        });

        return view;
    }
    public void Dialog(String mensaje) {
        Dialog newFragment = Dialog.newInstance(mensaje);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }
}
