package com.egmvdev.venturesoft.iu.main.repository;

import android.content.Context;

import com.egmvdev.venturesoft.clases.usuarioSinglenton;

public class mainRepository {
    private Context context;

    public mainRepository(Context context){
        this.context = context;
    }

    public String obtenerNombre() {
        return usuarioSinglenton.getInstance().sciaArray.get(0).razonSocial;
    }

}
