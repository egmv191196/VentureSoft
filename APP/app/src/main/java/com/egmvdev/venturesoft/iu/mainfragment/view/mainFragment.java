package com.egmvdev.venturesoft.iu.mainfragment.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.egmvdev.venturesoft.clases.usuarioSinglenton;
import com.egmvdev.venturesoft.databinding.FragmentMainBinding;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class mainFragment extends Fragment {

    private FragmentMainBinding binding;
    private PieDataSet pieDataSet = new PieDataSet(null, null);
    private BarDataSet barDataSet;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniciarVista();
    }

    public void iniciarVista() {
        binding.titleFragment.setText(usuarioSinglenton.getInstance().sciaArray.get(0).razonSocial);
        cargarGrafica1();
        cargarGrafica2();
    }

    public void cargarGrafica1(){

        barDataSet = new BarDataSet(generarDatosG1(), "Estadsitica");
        barDataSet.setDrawIcons(false);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        binding.chart1.setData(barData);
        binding.chart1.getDescription().setEnabled(false);
        binding.chart1.animate();
    }

    public ArrayList<BarEntry> generarDatosG1(){
        ArrayList<BarEntry> dataValues = new ArrayList<>();
        dataValues.add(new BarEntry(2.5f,3));
        dataValues.add(new BarEntry(4.7f,5));
        dataValues.add(new BarEntry(5.7f,10));
        return dataValues;
    }

    public void cargarGrafica2(){
        pieDataSet.setValues(generarDatosG2());
        pieDataSet.setLabel("Grafico de consumos");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        PieData pieData = new PieData(pieDataSet);
        binding.chart2.setData(pieData);
        binding.chart2.getDescription().setEnabled(false);
        binding.chart2.animate();
    }

    public ArrayList<PieEntry> generarDatosG2(){
        ArrayList<PieEntry> dataValues = new ArrayList<>();
        dataValues.add(new PieEntry(196,"Consumido"));
        dataValues.add(new PieEntry(92,"Restante"));
        return dataValues;
    }
}