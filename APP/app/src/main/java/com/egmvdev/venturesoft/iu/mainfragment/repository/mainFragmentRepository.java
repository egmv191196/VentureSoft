package com.egmvdev.venturesoft.iu.mainfragment.repository;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.egmvdev.venturesoft.R;
import com.egmvdev.venturesoft.clases.usuarioSinglenton;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mainFragmentRepository {
    private Context context;
    private UTMainFragmentWA utWA = new UTMainFragmentWA();
    public mainFragmentRepository(Context context){
        this.context = context;
    }

    public String obtenerRazonSocial(){
        return usuarioSinglenton.getInstance().nombreCompleto;
    }

    public Pair<BarData, ArrayList<String>> obtenerDatosEstadisticas() {
        Pair<ArrayList<BarEntry>, ArrayList<String>> pair1 = generarDatosG1();
        BarDataSet barDataSet = new BarDataSet(pair1.first, "Procesados");
        barDataSet.setDrawIcons(false);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);
        Pair<BarData, ArrayList<String>> datos = new Pair<>(barData, pair1.second);
        return datos;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public PieData obtenerDatosConsumos() {
        PieDataSet pieDataSet = new PieDataSet(null, null);
        pieDataSet.setValues(generarDatosG2());
        pieDataSet.setLabel("Grafico de consumos");
        List<Integer> colors = new ArrayList<>();
        colors.add(context.getColor(R.color.blue1));
        colors.add(context.getColor(R.color.blue2));
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        PieData pieData = new PieData(pieDataSet);
        Log.d("MAINFRAGREPOSITRY", "Se asigna el pie DATA" + pieData);
        return pieData;
    }

    public Pair<ArrayList<BarEntry>, ArrayList<String>> generarDatosG1(){
        return utWA.getDatosEstadisticas();
    }

    public ArrayList<PieEntry> generarDatosG2(){
        return utWA.getDatosConsumo();
    }
}
