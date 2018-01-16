package com.example.manuelsanchezferna.json;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Genero extends AppCompatActivity {


    private RecyclerView recyclerViewRock,recyclerViewBlues,recyclerViewHipHop,
            recyclerViewRapMetal, recyclerViewFusion,recyclerViewScreamo;
    private VideoInfoAdapter adapterRock, adapterBlues, adapterHipHop,
            adapterRapMetal, adapterFusion, adapterScreamo;


    private String[] videosRock;
    private String[] videosBlues;
    private String[] videosHipHop;
    private String[] videosRapMetal;
    private String[] videosFusion;
    private String[] videosScreamo;

    private List<VideoInfo> RockList = new ArrayList<VideoInfo>();
    private List<VideoInfo> BluesList = new ArrayList<VideoInfo>();
    private List<VideoInfo> HipHopList = new ArrayList<VideoInfo>();
    private List<VideoInfo> RapMetalList = new ArrayList<VideoInfo>();
    private List<VideoInfo> FusionList = new ArrayList<VideoInfo>();
    private List<VideoInfo> ScreamoList = new ArrayList<VideoInfo>();

    private String usuario;

    private ProgressDialog pDialog1, pDialog2, pDialog3, pDialog4, pDialog5, pDialog6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genero);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("KEY_USUARIO");

        JBlues("https://unguled-flash.000webhostapp.com/Consultas/consultageneros.php?genre=Blues");
        JHipHop("https://unguled-flash.000webhostapp.com/Consultas/consultageneros.php?genre=Hip%20Hop");
        JRapMetal("https://unguled-flash.000webhostapp.com/Consultas/consultageneros.php?genre=Rap%20Metal");
        JFusion("https://unguled-flash.000webhostapp.com/Consultas/consultageneros.php?genre=Fusion");
        JScreamo("https://unguled-flash.000webhostapp.com/Consultas/consultageneros.php?genre=Screamo");
        JRock("https://unguled-flash.000webhostapp.com/Consultas/consultageneros.php?genre=Rock");

        createSpinner();
    }

    private void JRock(String url){
        pDialog1 = new ProgressDialog(this);
        pDialog1.setMessage("Loading...");
        pDialog1.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1) {
                            videosRock= new String[c.getVideos().size()];
                            for (int i = 0; i < c.getVideos().size(); i++) {

                                videosRock[i] = c.getVideos().get(i).getUrl();
                                RockList.add(new VideoInfo(c.getVideos().get(i).getName().toString(),
                                        c.getVideos().get(i).getTittle().toString(),
                                        c.getVideos().get(0).getScore(),
                                        Uri.parse(videosRock[i%videosRock.length]), usuario));
                            }

                            RockRecyclerVid();
                            pDialog1.hide();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                        Log.i("app","makeJsonObj: onErrorResponse List2");
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    private void RockRecyclerVid(){

        recyclerViewRock = (RecyclerView) findViewById(R.id.Cat1);
        adapterRock = new VideoInfoAdapter(this,RockList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerViewRock.setHasFixedSize(true);
        recyclerViewRock.setLayoutManager(mLayoutManager);
        recyclerViewRock.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRock.setAdapter(adapterRock);

    }

    private void JBlues(String url){
        pDialog2 = new ProgressDialog(this);
        pDialog2.setMessage("Loading...");
        pDialog2.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1) {
                            videosBlues= new String[c.getVideos().size()];
                            for (int i = 0; i < c.getVideos().size(); i++) {

                                videosBlues[i] = c.getVideos().get(i).getUrl();
                                BluesList.add(new VideoInfo(c.getVideos().get(i).getName().toString(),
                                        c.getVideos().get(i).getTittle().toString(),
                                        c.getVideos().get(0).getScore(),
                                        Uri.parse(videosBlues[i%videosBlues.length]),usuario));
                            }

                            BluesRecyclerVid();
                            pDialog2.hide();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                        Log.i("app","makeJsonObj: onErrorResponse List2");
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    private void BluesRecyclerVid(){

        recyclerViewBlues = (RecyclerView) findViewById(R.id.Cat6);
        adapterBlues = new VideoInfoAdapter(this,BluesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerViewBlues.setHasFixedSize(true);
        recyclerViewBlues.setLayoutManager(mLayoutManager);
        recyclerViewBlues.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBlues.setAdapter(adapterBlues);

    }

    private void JHipHop(String url){

        pDialog3 = new ProgressDialog(this);
        pDialog3.setMessage("Loading...");
        pDialog3.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1) {
                            videosHipHop= new String[c.getVideos().size()];
                            for (int i = 0; i < c.getVideos().size(); i++) {

                                videosHipHop[i] = c.getVideos().get(i).getUrl();
                                HipHopList.add(new VideoInfo(c.getVideos().get(i).getName().toString(),
                                        c.getVideos().get(i).getTittle().toString(),
                                        c.getVideos().get(0).getScore(),
                                        Uri.parse(videosHipHop[i%videosHipHop.length]),usuario));
                            }

                            HipHopRecyclerVid();
                            pDialog3.hide();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                        Log.i("app","makeJsonObj: onErrorResponse List2");
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    private void HipHopRecyclerVid(){

        recyclerViewHipHop = (RecyclerView) findViewById(R.id.Cat2);
        adapterHipHop = new VideoInfoAdapter(this,HipHopList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerViewHipHop.setHasFixedSize(true);
        recyclerViewHipHop.setLayoutManager(mLayoutManager);
        recyclerViewHipHop.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHipHop.setAdapter(adapterHipHop);

    }

    private void JRapMetal(String url){

        pDialog4 = new ProgressDialog(this);
        pDialog4.setMessage("Loading...");
        pDialog4.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1) {
                            videosRapMetal= new String[c.getVideos().size()];
                            for (int i = 0; i < c.getVideos().size(); i++) {

                                videosRapMetal[i] = c.getVideos().get(i).getUrl();
                                RapMetalList.add(new VideoInfo(c.getVideos().get(i).getName().toString(),
                                        c.getVideos().get(i).getTittle().toString(),
                                        c.getVideos().get(0).getScore(),
                                        Uri.parse(videosRapMetal[i%videosRapMetal.length]),usuario));
                            }

                            RapMetalRecyclerVid();
                            pDialog4.hide();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                        Log.i("app","makeJsonObj: onErrorResponse List2");
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    private void RapMetalRecyclerVid(){

        recyclerViewRapMetal = (RecyclerView) findViewById(R.id.Cat5);
        adapterRapMetal = new VideoInfoAdapter(this,RapMetalList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerViewRapMetal.setHasFixedSize(true);
        recyclerViewRapMetal.setLayoutManager(mLayoutManager);
        recyclerViewRapMetal.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRapMetal.setAdapter(adapterRapMetal);

    }

    private void JFusion(String url){

        pDialog5 = new ProgressDialog(this);
        pDialog5.setMessage("Loading...");
        pDialog5.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1) {
                            videosFusion= new String[c.getVideos().size()];
                            for (int i = 0; i < c.getVideos().size(); i++) {

                                videosFusion[i] = c.getVideos().get(i).getUrl();
                                FusionList.add(new VideoInfo(c.getVideos().get(i).getName().toString(),
                                        c.getVideos().get(i).getTittle().toString(),
                                        c.getVideos().get(0).getScore(),
                                        Uri.parse(videosFusion[i%videosFusion.length]),usuario));
                            }

                            FusionRecyclerVid();
                            pDialog5.hide();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                        Log.i("app","makeJsonObj: onErrorResponse List2");
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    private void FusionRecyclerVid(){

        recyclerViewFusion = (RecyclerView) findViewById(R.id.Cat3);
        adapterFusion = new VideoInfoAdapter(this,FusionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerViewFusion.setHasFixedSize(true);
        recyclerViewFusion.setLayoutManager(mLayoutManager);
        recyclerViewFusion.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFusion.setAdapter(adapterFusion);

    }

    private void JScreamo(String url){

        pDialog6 = new ProgressDialog(this);
        pDialog6.setMessage("Loading...");
        pDialog6.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1) {
                            videosScreamo= new String[c.getVideos().size()];
                            for (int i = 0; i < c.getVideos().size(); i++) {

                                videosScreamo[i] = c.getVideos().get(i).getUrl();
                                ScreamoList.add(new VideoInfo(c.getVideos().get(i).getName().toString(),
                                        c.getVideos().get(i).getTittle().toString(),
                                        c.getVideos().get(0).getScore(),
                                        Uri.parse(videosScreamo[i%videosScreamo.length]),usuario));
                            }

                            ScreamoRecyclerVid();
                            pDialog6.hide();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                        Log.i("app","makeJsonObj: onErrorResponse List2");
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    private void ScreamoRecyclerVid(){

        recyclerViewScreamo = (RecyclerView) findViewById(R.id.Cat4);
        adapterScreamo = new VideoInfoAdapter(this,ScreamoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerViewScreamo.setHasFixedSize(true);
        recyclerViewScreamo.setLayoutManager(mLayoutManager);
        recyclerViewScreamo.setItemAnimator(new DefaultItemAnimator());
        recyclerViewScreamo.setAdapter(adapterScreamo);

    }

    private void createSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.desplegable4,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Seleccio(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("app","onNothing");}
        });
    }

    private void Seleccio(int pos) {

        if(pos == 1){
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
        }

        if(pos == 2){
            Intent intent = new Intent(this,Perfil.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
        }

        if(pos == 3){
            //Categorías
        }

        if (pos == 4){
            //Agenda
        }

        if (pos == 5){
            //Configuración
            Intent intent = new Intent(this,ConfigUsuario.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
        }
    }
}
