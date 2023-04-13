package com.egmvdev.venturesoft.iu.mainfragment.viewmodel;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.egmvdev.venturesoft.base.baseViewModel;
import com.egmvdev.venturesoft.iu.mainfragment.repository.mainFragmentRepository;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class mainFragmentViewModel extends baseViewModel {
    private mainFragmentRepository repository;

    private MutableLiveData<Pair<BarData, ArrayList<String>>> estadisticasData;
    private MutableLiveData<PieData> consumoData;
    private MutableLiveData<String> razonSocial;

    public mainFragmentViewModel(Context context) {
        repository = new mainFragmentRepository(context);
        this.estadisticasData = new MutableLiveData<>();
        this.consumoData = new MutableLiveData<>();
        this.razonSocial = new MutableLiveData<>();
    }

    public LiveData<String> getRazonSocial() {
        return razonSocial;
    }

    public LiveData<Pair<BarData, ArrayList<String>>> getEstadisticas() {
        return estadisticasData;
    }

    public LiveData<PieData> getConsumo() {
        return consumoData;
    }

    public void obtenerRazonSocial() {
        razonSocial.setValue(repository.obtenerRazonSocial());
    }

    public void obtenerEstadisticas() {
        loader.setValue(true);
        compositeDisposable.add(Single.create((SingleOnSubscribe<Pair<BarData, ArrayList<String>>>) emitter ->
                        emitter.onSuccess(repository.obtenerDatosEstadisticas()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
//                    if (resp != null) {
                        estadisticasData.setValue(resp);
//                    }
                    obtenerConsumos();
                }, throwable -> {
                    throwable.printStackTrace();
                    obtenerConsumos();
                }));

    }

    public void obtenerConsumos() {
        compositeDisposable.add(Single.create((SingleOnSubscribe<PieData>) emitter ->
                        emitter.onSuccess(repository.obtenerDatosConsumos()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    loader.setValue(false);
                    if (resp != null) {
                        consumoData.setValue(resp);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    loader.setValue(false);
                }));
    }
}
