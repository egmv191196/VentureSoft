package com.egmvdev.venturesoft.iu.mainfragment.view;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.egmvdev.venturesoft.base.baseFragment;
import com.egmvdev.venturesoft.databinding.FragmentMainBinding;
import com.egmvdev.venturesoft.iu.mainfragment.viewmodel.mainFragmentViewModel;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class mainFragment extends baseFragment {

    private FragmentMainBinding binding;

    private mainFragmentViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new mainFragmentViewModel(requireContext());
        iniciarVista();
        observer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void iniciarVista() {
        viewModel.obtenerRazonSocial();
        viewModel.obtenerEstadisticas();
        viewModel.obtenerConsumos();
    }

    private void observer() {
        viewModel.getRazonSocial().observe(this, this::cargarRazon);
        viewModel.getEstadisticas().observe(this, this::cargarGrafica1);
        viewModel.getConsumo().observe(this, this::cargarGrafica2);
        viewModel.getLoader().observe(this, this::showLoader);

    }

    public void cargarRazon(String razonSocial) {
        binding.titleFragment.setText(razonSocial);
    }

    public void cargarGrafica1(Pair<BarData, ArrayList<String>> datos) {
        if (datos.second.size() > 0 ){
            binding.chart1.getDescription().setEnabled(false);
            binding.chart1.setMaxVisibleValueCount(120);
            binding.chart1.setDrawGridBackground(false);
            binding.chart1.setData(datos.first);

            XAxis xAxis = binding.chart1.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(datos.second));
            xAxis.setPosition(XAxis.XAxisPosition.TOP);
            xAxis.setLabelCount(datos.second.size());

            YAxis yAxisl = binding.chart1.getAxisLeft();
            yAxisl.setAxisMaximum(100f);
            yAxisl.setTextSize(8f);
            YAxis yAxisr = binding.chart1.getAxisRight();
            yAxisr.setEnabled(false);

            Legend l = binding.chart1.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setXEntrySpace(4f);
            binding.chart1.animate();
            binding.chart1.invalidate();
        }

    }

    public void cargarGrafica2(PieData pieData) {
        binding.chart2.getDescription().setEnabled(false);
        binding.chart2.animate();
        binding.chart2.setData(pieData);
        binding.chart2.invalidate();
    }
}