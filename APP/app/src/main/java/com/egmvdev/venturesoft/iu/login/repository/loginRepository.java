package com.egmvdev.venturesoft.iu.login.repository;

import android.content.Context;

public class loginRepository {
    private Context context;
    public loginRepository(Context context){
        this.context = context;
    }

    public boolean validarUsuario(String email, String password){
        UTLoginWA loginWA = new UTLoginWA();
        return loginWA.validarUsuario(email, password);
    }
}
