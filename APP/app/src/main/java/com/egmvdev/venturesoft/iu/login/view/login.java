package com.egmvdev.venturesoft.iu.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.egmvdev.venturesoft.R;
import com.egmvdev.venturesoft.base.baseActivity;
import com.egmvdev.venturesoft.databinding.ActivityLoginBinding;
import com.egmvdev.venturesoft.iu.login.viewmodel.loginViewModel;
import com.egmvdev.venturesoft.iu.main.view.main;
import com.egmvdev.venturesoft.utils.web.UTWebApplication;

import java.util.Arrays;
import java.util.List;

public class login extends baseActivity {

    private ActivityLoginBinding binding;
    private UTWebApplication utWebApplication = new UTWebApplication();

    private loginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        viewModel = new loginViewModel(this);
        setContentView(binding.getRoot());
        addActions();
        observer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarVista();
    }

    public void cargarVista() {
        List<String> items = Arrays.asList("Español", "English", "Portugues");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, items);
        binding.actvLenguaje.setAdapter(adapter);
    }

    private void observer() {
        viewModel.getLoader().observe(this, this::showLoader);
        viewModel.getCampoVacio().observe(this, this::resultValidacion);
        viewModel.getAccesoUsuario().observe(this, this::resultValidacion);
    }

    public void addActions() {
        binding.btnLogin.setOnClickListener(v -> validarCampos());
        binding.tiPassword.getEditText().setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                validarCampos();
            }
            return true;
        });
    }

    public void showLoader(Boolean show) {
        showLoader(show, "Iniciando sesión");
    }

    public void validarCampos() {
        viewModel.validarCampos(binding.tiEmail.getEditText().getText().toString(),
                binding.tiPassword.getEditText().getText().toString());
    }

    private void resultValidacion(Integer resultado) {
        if (resultado == 1) {
            binding.tiEmail.setError(getString(R.string.campoVacio));
        } else if (resultado == 2) {
            binding.tiPassword.setError(getString(R.string.campoVacio));
        } else {
            viewModel.validarUsuario(binding.tiEmail.getEditText().getText().toString(),
                    binding.tiPassword.getEditText().getText().toString());
        }
    }

    private void resultValidacion(boolean resultado) {
        if (!resultado) {
            showAlert(getString(R.string.espere), getString(R.string.errorAut));
//            Toast.makeText(this, "Datos incorrectos para inicio de sesion", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(this, main.class));
            finish();
        }
    }

}