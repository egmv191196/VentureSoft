package com.egmvdev.venturesoft.iu.main.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.egmvdev.venturesoft.iu.main.repository.mainRepository;

public class mainViewModel extends ViewModel {
    private mainRepository repository;

    private MutableLiveData<String> nombreUsuario;

    public mainViewModel(Context context){
        repository = new mainRepository(context);
        this.nombreUsuario = new MutableLiveData<>();
    }

    public LiveData<String> getNombreUsuario(){
        return nombreUsuario;
    }

    public void obtenerNombre(){
        nombreUsuario.setValue(repository.obtenerNombre());
    }
}
