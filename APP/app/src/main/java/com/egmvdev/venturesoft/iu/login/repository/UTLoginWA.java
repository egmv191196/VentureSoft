package com.egmvdev.venturesoft.iu.login.repository;

import android.util.Log;

import com.egmvdev.venturesoft.clases.Scia;
import com.egmvdev.venturesoft.clases.usuarioSinglenton;
import com.egmvdev.venturesoft.utils.web.UTWebApplication;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class UTLoginWA extends UTWebApplication {

    public UTLoginWA(){
        super();
    }

    public boolean validarUsuario(String email, String password){
        Map<String, String> parametros = new HashMap<>();
        parametros.put("email", email);
        parametros.put("password", password);
        if (responsePostParamssQuery("Authorization/AccesoUsuario/", parametros,false)){
            if (!json.isJsonNull()){
                if (json.get("codigo").getAsString().equals("ET000")){
                    usuarioSinglenton.getInstance().token = json.get("token").getAsString();

                    usuarioSinglenton.getInstance().idUsuario = json.getAsJsonObject("acceso").getAsJsonObject("usuarioeTime").get("idUsuario").getAsInt();
                    usuarioSinglenton.getInstance().nombreCompleto = json.getAsJsonObject("acceso").getAsJsonObject("usuarioeTime").get("nombreCompleto").getAsString();
                    usuarioSinglenton.getInstance().tipoUsuario = json.getAsJsonObject("acceso").getAsJsonObject("usuarioeTime").get("tipoUsuario").getAsInt();
                    if(json.getAsJsonObject("acceso").getAsJsonObject("usuarioeTime").has("scia")) {
                        Log.d("UTLOGIN", json.getAsJsonObject("acceso").getAsJsonObject("usuarioeTime").getAsJsonArray("scia").toString());
                    }
                    JsonArray jsonArray = json.getAsJsonObject("acceso").getAsJsonObject("usuarioeTime").getAsJsonArray("scia");
                    for (JsonElement element : jsonArray) {
                        JsonObject sciaJson = element.getAsJsonObject();
                        Scia scia = new Scia();
                        scia.cia = sciaJson.get("cia").getAsInt();
                        scia.nombre = sciaJson.get("nombre").getAsString();
                        scia.idFacturacion = sciaJson.get("idFacturacion").getAsInt();
                        scia.razonSocial = sciaJson.get("razonSocial").getAsString();
                        usuarioSinglenton.getInstance().sciaArray.add(scia);
                    }
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
}
