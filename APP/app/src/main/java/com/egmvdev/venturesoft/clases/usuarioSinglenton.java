package com.egmvdev.venturesoft.clases;

import java.util.ArrayList;

public class usuarioSinglenton {
    public String token;
    public int idUsuario;
    public String nombreCompleto;
    public int tipoUsuario;
    public ArrayList<Scia> sciaArray;

    private static usuarioSinglenton usuario;

    private usuarioSinglenton(){
        token = "";
        idUsuario = 0;
        nombreCompleto = "";
        tipoUsuario = 0;
        sciaArray = new ArrayList<>();
    }

    public static usuarioSinglenton getInstance(){
        if (usuario == null){
            usuario = new usuarioSinglenton();
        }
        return usuario;
    }
}


