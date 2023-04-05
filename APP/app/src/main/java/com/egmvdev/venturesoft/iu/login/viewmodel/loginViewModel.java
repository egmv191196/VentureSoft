package com.egmvdev.venturesoft.iu.login.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.egmvdev.venturesoft.iu.login.repository.loginRepository;

public class loginViewModel extends ViewModel {

    private loginRepository repository;

    private MutableLiveData<Integer> campoVacio;
    private MutableLiveData<Boolean> accesoUsuario;

    public loginViewModel(Context context){
        repository = new loginRepository(context);
        this.campoVacio = new MutableLiveData<>();
        this.accesoUsuario = new MutableLiveData<>();
    }

    public LiveData<Integer> getCampoVacio(){
        return campoVacio;
    }

    public LiveData<Boolean> getAccesoUsuario(){
        return accesoUsuario;
    }

    public void validarCampos(String email, String password){
        if (email.length() == 0){
            campoVacio.setValue(1);
        } else if (password.length() == 0){
            campoVacio.setValue(2);
        } else {
            campoVacio.setValue(0);
        }
    }
    public void validarUsuario(String email, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                 accesoUsuario.postValue(repository.validarUsuario(email, password));
            }
        }).start();
    }
}