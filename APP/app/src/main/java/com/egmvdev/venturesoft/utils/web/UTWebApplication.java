package com.egmvdev.venturesoft.utils.web;

import static java.net.HttpURLConnection.HTTP_OK;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UTWebApplication {

    public JsonObject json;
    public String serviceUrl;
    public String lastError;
    public ResponseBody responseBody;
    public int responseCode;
    public String contextUrl;
    public boolean isJsonObject;
    protected long connectTimeout;
    protected long readTimeout;
    protected long writeTimeout;
    protected boolean errorBody;
    public boolean checkConnection;
    protected boolean v2;
    protected boolean esResponseBody;
    protected String username;
    protected String password;

    private static final String TAG = UTWebApplication.class.getSimpleName();
//    public RequestManager requestManager;

    private Retrofit mRestAdapter;
    private UTGenericEndPointInterface endPointInterface;


    public UTWebApplication() {
        json = new JsonObject();
        username = "";
        password = "";
        lastError = "";
        serviceUrl = "https://eland-services.humaneland.net:8443/HumaneTime/api/";
        contextUrl = "";
        responseCode = 0;
        isJsonObject = false;
        connectTimeout = 20;
        readTimeout = 40;
        writeTimeout = 40;
        errorBody = false;
        checkConnection = true;
        v2 = false;
        esResponseBody = false;
    }

    public boolean responseGET(String method, boolean decode) {
        if (!checkConnection()) {
            errorConexion("No se cuenta con cobertura para realizar la operación");
            return false;
        }
        String response = null;
        try {
            Response<ResponseBody> responseBody = retrofitClient.getClient(serviceUrl).create(UTGenericEndPointInterface.class).getResponse("").execute();
            Log.d(TAG, responseBody.toString());
            if (responseBody.code() == 200 && responseBody.body() != null) {
                response = responseBody.body().string();
                Log.d(TAG, response);
            } else if (responseBody.code() != 200 && responseBody.errorBody() != null) {
                response = responseBody.errorBody().string();
                errorBody = true;
            }

            if (responseBody.code() != HTTP_OK) {
                JsonObject data = new JsonObject();
                data.addProperty("URL", responseBody.raw().request().url().toString());
                data.addProperty("HEADERS", responseBody.raw().request().headers().toString());
                data.addProperty("BODY", responseBody.raw().request().body() != null ? responseBody.raw().request().body().toString() : "");
                data.addProperty("MESSAGE", responseBody.message());
                data.addProperty("METHOD", responseBody.raw().request().method());
                data.addProperty("CODE", responseBody.code());
                lastError = responseBody.errorBody() != null ? responseBody.errorBody().string() : lastError;
            }

            if (response != null && !response.isEmpty()) {
                if (decode) {
//                    response = decryptDecompressDataBase64(response);
                    if (response == null || response.isEmpty()) {
                        errorParseo("Error al decodificar la respuesta en Base64");
                        errorBody = false;
                        return false;
                    }
                }
                if (errorBody) {
                    if (!validarRespuesta(response)) {
                        errorBody = false;
                    }
                    return false;
                } else {
                    return validarRespuesta(response);
                }
            } else {
                errorBody = false;
                return false;
            }

        } catch (IOException e) {
            errorConexion("No se cuenta con cobertura para realizar la operacion");
            Log.e(TAG, e.getMessage());
//            ResponseHandler.sendMail(serviceUrl, method, "", null, e.toString());
            return false;
        } catch (Exception e) {
            errorConexion("Error al consultar el servicio");
            Log.e(TAG, e + "Error al consultar sevicio");
//            ResponseHandler.sendMail(serviceUrl, method, "", response, e.toString());
            return false;
        }
    }

    protected Boolean responsePostParamssQuery(String method, Map<String, String> params, boolean decode) {
        if (!checkConnection()) {
            errorConexion("No se cuenta con cobertura para realizar la operación");
            return false;
        }
        String response = null;
        Log.i(TAG, serviceUrl + method + params);
        try {
            Response<ResponseBody> call = retrofitClient.getClient(serviceUrl).create(UTGenericEndPointInterface.class).postResponseParamsQuery(method, params).execute();
            responseBody = call.body();
            if (responseBody != null) {
                response = responseBody.string();
            }

            if (call.code() != HTTP_OK) {
                JsonObject data = new JsonObject();
                data.addProperty("URL", call.raw().request().url().toString());
                data.addProperty("HEADERS", call.raw().request().headers().toString());
                data.addProperty("BODY", call.raw().request().body() != null ? call.raw().request().body().toString() : "");
                data.addProperty("MESSAGE", call.message());
                data.addProperty("METHOD", call.raw().request().method());
                data.addProperty("CODE", call.code());
                lastError = call.errorBody() != null ? call.errorBody().string() : lastError;
            }

            if (response != null && !response.isEmpty()) {
                if (decode) {
//                    response = decryptDecompressDataBase64(response);
                    if (response == null || response.isEmpty()) {
                        errorParseo("Error al decodificar la respuesta en Base64");
                        return false;
                    }
                }
                return validarRespuesta(response);

            } else {
                return false;
            }
        } catch (IOException e) {
            errorConexion("No se cuenta con cobertura para realizar la operacion");
            Log.e(TAG, e.getMessage());
            return false;
        } catch (Exception e) {
            errorConexion("Error al consultar el servicio");
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    protected boolean validarRespuesta(String response) {
        if (UTJson.isJsonObject(response)) {
            json = new Gson().fromJson(response, JsonObject.class);
        } else if (UTJson.isJsonArray(response)) {
            json.add("respuesta", new Gson().fromJson(response, JsonArray.class));
        } else {
            json.addProperty("respuesta", response);
        }
        if (json.size() == 0) {
            errorParseo("Error al decodificar el objeto json");
            return false;
        }
//        Log.d(UTWebApplication.TAG, json.toString());
        return true;
    }


    /**
     * Realiza una petición para verificar la conexion
     *
     * @return true Si el servicio se consumio con éxito. false Para cualquier otro caso.
     */
    public synchronized boolean checkConnection() {
        boolean result = false;
        if (!checkConnection) {
            return true;
        }

        HttpURLConnection con = null;
        try {
            URL urlConnection = new URL("https://www.google.com");
            con = (HttpURLConnection) urlConnection.openConnection();
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                result = true;
                checkConnection = false;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage()+ "Error");
            checkConnection = true;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return result;
    }

    protected void errorConexion(String error) {
        lastError = error;
        Log.e(TAG, error);
    }

    protected void errorParseo(String error) {
        lastError = error;
        Log.e(TAG, error);
    }

    private boolean verificarToken(String authorization) {
        String token = authorization.replace("Bearer", "").replace("Basic", "").trim();
        return token.length() > 0;
    }
}
