package com.example.manuelsanchezferna.json;

import android.os.AsyncTask;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Created by DjManko on 11/1/18.
 */



public class MyAsyncTask extends AsyncTask <String,Void,Boolean> {

    private ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    private Context context;
    private String usuario;


    /**Constructor de clase */
    public MyAsyncTask(Context context, String user) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        this.usuario = user;
    }
    /**
     * Antes de comenzar la tarea muestra el progressDialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Por favor espere", "Subiendo...");
    }

    /**
     * @param
     * */
    @Override
    protected Boolean doInBackground(String... params) {
        Boolean r = false;
        UploadImage apiRest = new UploadImage();
        try {
            r = apiRest.uploadPhoto(params[0], usuario);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * Cuando se termina de ejecutar, cierra el progressDialog y avisa
     * **/
    @Override
    protected void onPostExecute(Boolean resul) {
        progressDialog.dismiss();
        if( resul )
        {
            builder.setMessage("Imagen subida al servidor")
                    .setTitle("Se ha subido correctamente")
                    .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.cancel();
                        }
                    }).create().show();
            txttojpg();
            //updateimage();

            String url="https://unguled-flash.000webhostapp.com/Consultas/updateconfig_foto.php?user="+usuario;
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Configuración", "updateee");

                            Gson gson = new Gson();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("No se ha cargado", "no");
                        }
                    });

            Volley.newRequestQueue(this.context).add(jsObjRequest);
        }
        else
        {
            builder.setMessage("No se pudo subir la imagen")
                    .setTitle("Error")
                    .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.cancel();
                        }
                    }).create().show();
        }
    }
    private void txttojpg(){
        String url="https://unguled-flash.000webhostapp.com/images/photo.php?user="+usuario;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Configuración", "onResponseBotonEmail");

                        Gson gson = new Gson();

                    }
                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Update imagen", "No se ha convertido");
                    }
                });

        Volley.newRequestQueue(this.context).add(jsObjRequest);
    }

}
