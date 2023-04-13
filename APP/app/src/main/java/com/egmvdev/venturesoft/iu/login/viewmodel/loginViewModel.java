package com.egmvdev.venturesoft.iu.login.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.egmvdev.venturesoft.base.baseViewModel;
import com.egmvdev.venturesoft.iu.login.repository.loginRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class loginViewModel extends baseViewModel {
    protected CompositeDisposable compositeDisposable;

    private loginRepository repository;

    private MutableLiveData<Integer> campoVacio;
    private MutableLiveData<Boolean> accesoUsuario;

    public loginViewModel(Context context){
        repository = new loginRepository(context);
        this.campoVacio = new MutableLiveData<>();
        this.accesoUsuario = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
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
        loader.setValue(true);
        compositeDisposable.add(Single.create((SingleOnSubscribe<Boolean>) emitter ->
                        emitter.onSuccess(repository.validarUsuario(email, password)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    loader.setValue(false);
                    if (resp != null){
                        accesoUsuario.setValue(resp);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    loader.setValue(false);
                }));
    }
}