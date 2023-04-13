package com.egmvdev.venturesoft.iu.mainfragment.repository;

import android.util.Log;
import android.util.Pair;

import com.egmvdev.venturesoft.clases.Scia;
import com.egmvdev.venturesoft.clases.usuarioSinglenton;
import com.egmvdev.venturesoft.utils.web.UTWebApplication;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UTMainFragmentWA extends UTWebApplication {

    public UTMainFragmentWA(){
        super();
    }

    public Pair<ArrayList<BarEntry>, ArrayList<String>> getDatosEstadisticas(){
        ArrayList<String> dia = new ArrayList<>();
        ArrayList<BarEntry> datos = new ArrayList<>();
        String token = usuarioSinglenton.getInstance().token;
        if (responsePOSTAuthorization("Desvinculado/ConsultaEstadisticas", crearObjetoConsulta(), false, token)){
            if (!json.isJsonNull()){
                if (json.get("codigo").getAsString().equals("ET000")){
                    JsonArray jsonArray = json.getAsJsonArray("diasEstadistica");
                    int i = 0;
                    for (JsonElement element : jsonArray) {
                        JsonObject dato = element.getAsJsonObject();
                        dia.add(dato.get("fecha").getAsString().replace("-","/").substring(5,10));
                        datos.add(new BarEntry(i, dato.get("porcentaje").getAsFloat()));
                        i++;
                    }
                    Log.d("UTMAINFRAGMENT", dia.toString());
                }
            }
        }
        Pair<ArrayList<BarEntry>, ArrayList<String>> pair = new Pair<>(datos, dia);
        return pair;
    }

    private JsonObject crearObjetoConsulta() {
        JsonObject datosConsulta = new JsonObject();
        datosConsulta.addProperty("idCia", String.valueOf(usuarioSinglenton.getInstance().sciaArray.get(0).cia));
        datosConsulta.addProperty("diasEstadistica", "5");
        return datosConsulta;
    }

    public ArrayList<PieEntry> getDatosConsumo(){
        ArrayList<PieEntry> dataValues = new ArrayList<>();
        Map<String, Integer> parametros = new HashMap<>();
        parametros.put("idUsuario", usuarioSinglenton.getInstance().idUsuario);
        String token = usuarioSinglenton.getInstance().token;
        if (responsePOSTAuthorization("Desvinculado/ConsultaSaldo/", parametros, false, token)){
            if (!json.isJsonNull()){
                if (json.get("codigo").getAsString().equals("ET000")){
                    dataValues.add(new PieEntry(json.get("saldoTotal").getAsInt(),"Saldo total"));
                    dataValues.add(new PieEntry(json.get("saldoConsumido").getAsInt(),"Saldo consumido"));
                    Log.d("UTMAINWA", "Se envian datos" + dataValues);
                    return dataValues;
                }
            }
        }

        return dataValues;
    }
}
