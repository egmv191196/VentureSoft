package com.egmvdev.venturesoft.base;

import android.app.ProgressDialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.egmvdev.venturesoft.R;

public class baseActivity extends AppCompatActivity {
    private ProgressDialog pDialog;

    public void showLoader(Boolean show) {
        showLoader(show, getString(R.string.consulta));
    }

    public void showLoader(Boolean show, String message) {
        if (show) {
            if (pDialog == null) {
                pDialog = new ProgressDialog(this);
            }
            pDialog.setTitle(getString(R.string.atencion));
            pDialog.setMessage(message);
            pDialog.setCancelable(false);
            pDialog.show();
        } else if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    protected void showAlert(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.aceptar, (dialog, id) -> dialog.dismiss());
        builder.show();
    }
}
