package com.redes.boui.tabbed;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Boui on 26/03/2017.
 */

public class Tab3 extends android.support.v4.app.Fragment {
    //LineChart linechart;
    ScatterChart chart;
    private Spinner mes;
    private MainActivity main;
    private bd base;
    private int idMes;
    private TextView ejex, ejey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_grafica, container, false);
        ejex = (TextView)view.findViewById(R.id.EjeX);
        ejey = (TextView)view.findViewById(R.id.EjeY);
        chart = (ScatterChart)view.findViewById(R.id.lineChart);
        mes = (Spinner)view.findViewById(R.id.spinnerMesG);
        base = new bd(getActivity().getApplicationContext());
        main = (MainActivity)getActivity();

        mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

               // tabla.eliminarTabla();

                Log.d("tablita","seleccionado");

                idMes = mes.getSelectedItemPosition();
                if(idMes != 0) {
                    Log.d("tablita", String.valueOf(idMes));
                    List<RegistroGlucosa> reg = base.getRegistros(main.getIduser(), String.valueOf(idMes));
                    if (reg.isEmpty()) {
                        Log.d("tablita", "nadaaaaa");
                        Dialog("No hay resultados para el mes seleccionado.");
                    } else {

                        Log.d("tablita", "algooooo");
                        ArrayList <Entry> entries = new ArrayList<>();
                        int i = 0;
                        for (RegistroGlucosa todo : reg) {
                         //   entries.add(new Entry(0, 2f));
                            entries.add(new Entry(Float.valueOf(todo.getDd()),(float)todo.getNivel()));
                            i++;
                            Log.d("REGISTROS", String.valueOf(
                                    "Fecha: " + todo.getDd() + "/" + todo.getMm() + "/" + todo.getYyyy()
                                            + " Hora: " + todo.getHh() + " Antes/Después: " + todo.getCuando()
                                            + " Nivel: " + todo.getNivel()));
                        }
                      //  LineDataSet dataset = new LineDataSet(entries, "Datos del mes");
                        ScatterDataSet dataset = new ScatterDataSet(entries, "Datos del mes");
                        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                        //String[] values = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio"};
                        //XAxis xAxis = linechart.getXAxis();
                        // xAxis.setValueFormatter(new MyCustomXAxisValueFormatter(values));

                        //LineData data = new LineData(dataset);
                        ScatterData data = new ScatterData(dataset);
                        //Description description = new Description();
                        //description.setText("");
                        //chart.setDescription(description);
                        XAxis xAxis = chart.getXAxis();
                        xAxis.setAxisMinimum(0);
                        xAxis.setAxisMaximum(32);
                      //  YAxis yAxis = chart.
                        YAxis leftAxis = chart.getAxisLeft();

                        LimitLine high = new LimitLine(170f, "Alto");
                        high.setLineColor(Color.RED);
                        high.setLineWidth(2f);
                        high.setTextColor(Color.BLACK);
                        high.setTextSize(12f);

                        LimitLine bien = new LimitLine(65f, "Excelente");
                        bien.setLineColor(Color.GREEN);
                        bien.setLineWidth(2f);
                        bien.setTextColor(Color.BLACK);
                        bien.setTextSize(12f);

                        //leftAxis.addLimitLine(hyper);
                        leftAxis.addLimitLine(high);
                        //leftAxis.addLimitLine(enControl);
                        leftAxis.addLimitLine(bien);

                        chart.setData(data);
                        ejex.setVisibility(TextView.VISIBLE);
                        ejey.setVisibility(TextView.VISIBLE);
                        chart.animateY(1000);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Log.d("tablita","no seleccionado");
            }

        });
        /*ArrayList <Entry> entries = new ArrayList<>();

            entries.add(new Entry(0, 2f));
            entries.add(new Entry(1, 8f));
            entries.add(new Entry(2, 4f));
            entries.add(new Entry(3, 6f));
            entries.add(new Entry(4, 1f));
            entries.add(new Entry(5, 9f));
*/


        return view;

    }
    public void Dialog(String mensaje) {
        Dialog newFragment = Dialog.newInstance(mensaje);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }
}
