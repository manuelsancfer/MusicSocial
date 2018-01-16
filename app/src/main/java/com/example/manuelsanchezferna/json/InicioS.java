package com.example.manuelsanchezferna.json;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class InicioS extends AppCompatActivity {

    private EditText user, pass, nuser, npass, npass2, nmail;
    private String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_s);

        user = (EditText) findViewById(R.id.usuario);
        pass = (EditText) findViewById(R.id.pass);
        nuser = (EditText) findViewById(R.id.nuser);
        npass = (EditText) findViewById(R.id.npass);
        npass2 = (EditText) findViewById(R.id.npass2);
        nmail = (EditText) findViewById(R.id.nmail);
    }

    public void iniciars(View view) {

        String url = "https://unguled-flash.000webhostapp.com/Consultas/iniciosesion.php?user="
                + user.getText() +"&pass="+pass.getText();
        Jinicia(url);
        loading();
    }

    private void Jinicia(String url){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaResponse c = gson.fromJson(response.toString(),ConsultaResponse.class);

                        if(c.getSuccess()==1) {
                            usuario = user.getText().toString();
                            Intent intent = new Intent(InicioS.this,MainActivity.class);
                            intent.putExtra("KEY_USUARIO", usuario);
                            startActivity(intent);
                            finish_loading();
                            finish(); //Elimina de la pila de Actividades.

                            }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.e_inicio),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();

                    }
                });


        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    public void nuevouser(View view) {

            if(npass.length()>7 && npass.length()<15){
                String pass1 = npass.getText().toString(), pass2=npass2.getText().toString();

            if (pass1.equals(pass2)) {

                String url = "https://unguled-flash.000webhostapp.com/Consultas/nuevousuario.php?user="
                +nuser.getText()+"&pass="+pass1+"&email="+nmail.getText();
                Jnuevo(url);
                loading();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.e_pass),
                        Toast.LENGTH_LONG).show();

            }
        }


        else{
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.e_passw),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void Jnuevo(String url){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaResponse c = gson.fromJson(response.toString(),ConsultaResponse.class);

                        if(c.getSuccess()==1) {
                            usuario = nuser.getText().toString();
                            Intent intent = new Intent(InicioS.this,MainActivity.class);
                            intent.putExtra("KEY_USUARIO", usuario);
                            startActivity(intent);
                            finish_loading();
                            finish(); //Elimina de la pila de Actividades.

                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.e_inicio),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();

                    }
                });


        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    private ProgressDialog pDialog;

    public void loading(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
    }

    public void finish_loading(){ pDialog.hide(); }

}
