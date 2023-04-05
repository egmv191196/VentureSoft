package com.egmvdev.venturesoft.iu.mainfragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.egmvdev.venturesoft.clases.usuarioSinglenton;
import com.egmvdev.venturesoft.databinding.FragmentMainBinding;

public class mainFragment extends Fragment {

    private FragmentMainBinding binding;

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
    }
}