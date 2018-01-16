package com.example.manuelsanchezferna.json;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PerfilArtista extends AppCompatActivity {

    private ImageView fotoperfil;
    private TextView user, email, descripcion, numamigos, numsiguiendo,numseguidores, score;

    private RecyclerView recyclerViewMis, recyclerFav;
    private VideoInfoAdapter adapter1,adapter2;
    private List<VideoInfo> videoList = new ArrayList<VideoInfo>();
    private List<VideoInfo> videoList2 = new ArrayList<VideoInfo>();


    private String[] vidf =  new String[4];
    private String[] vidt = new String[4];
    private String[] vidv = new String[4];
    private float[] vids = new float[4];

    private String[] videosURLs= new String[10];
    private String[] videosURLsTop= new String[10];

    private ToggleButton priva;
    private String urlf;
    private boolean estado;

    private String artistName;
    private String descrip, mail,puntuacion,foto;

    private String usuario;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("descripcion", descrip);
        outState.putString("email",mail);
        outState.putString("puntuacion",puntuacion);
        outState.putString("foto_perfil",foto);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_artista);

        user = (TextView) findViewById(R.id.username_artista);
        email = (TextView) findViewById(R.id.emailArtista);
        descripcion = (TextView) findViewById(R.id.descripcion);
        numamigos =(TextView) findViewById(R.id.numamigos);
        numsiguiendo = (TextView) findViewById(R.id.numsiguiendo);
        numseguidores = (TextView) findViewById(R.id.numseguidores);
        fotoperfil = (ImageView) findViewById(R.id.imagen_perfilArtista);
        score = (TextView) findViewById(R.id.puntuacion);
        priva = (ToggleButton) findViewById(R.id.btn_follow);

        Intent intent1 = getIntent();
        artistName = intent1.getStringExtra("KEY_ARTISTA_NAME");
        Intent intent = getIntent();
        usuario = intent.getStringExtra("KEY_USUARIO");
        Log.i("holaquetal","hola "+usuario);

        if (savedInstanceState != null){
            Bundle state = savedInstanceState;
            descripcion.setText(state.getString("descripcion"));
            email.setText(state.getString("mail"));
            score.setText(state.getString("puntuacion"));
            makeImageRequest(state.getString("foto_perfil"));
        }

       makeJsonUser("https://unguled-flash.000webhostapp.com/Consultas/consultaperfilartista.php?user="
               +artistName);
        makeVideo("https://unguled-flash.000webhostapp.com/Consultas/consultamisvideos.php?user="
            +artistName);

        makeJsonNumAmigos("https://unguled-flash.000webhostapp.com/Consultas/ConsultaAmigos.php?user="
                +artistName);
        makeJsonNumSeguidos("https://unguled-flash.000webhostapp.com/Consultas/ConsultaSeguidoresU.php?user="
                +artistName);
        makeJsonNumSeguidores("https://unguled-flash.000webhostapp.com/Consultas/ConsultaSeguidos.php?user="
                +artistName);   //url nombre de ConsultaSeguidoresU y ConsultaSeguidos están bien pasados


        follow();

        createSpinner();
    }

    private void follow(){

        String url =
                "https://unguled-flash.000webhostapp.com/Consultas/update_follow.php?user="+usuario
                        +"&follow="+artistName;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Configuración", "onResponseBotonGustos");

                        Gson gson = new Gson();

                        ConsultaFollow c = gson.fromJson(response.toString(),ConsultaFollow.class);

                        if (c.getSuccess() == 1) {
                            priva.setChecked(true);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        priva.setChecked(false);
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);
        Log.i("Configuración", "volley");
    }

    private void makeVideo(String url){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);
                        Log.i("holaa", "holaaa"+c.getVideos().size());

                        if(c.getSuccess()==1) {
                            for (int i = 0; i < c.getVideos().size(); i++) {
                                Log.i("holaa", "holaaa funciona2"+ c.getVideos().get(i).getUrl());
                                videosURLs[i] = c.getVideos().get(i).getUrl();
                                videoList2.add(new VideoInfo(c.getVideos().get(i).getName(),
                                        c.getVideos().get(i).getTittle(),
                                        c.getVideos().get(i).getScore(),
                                        Uri.parse(videosURLs[i%videosURLs.length]),usuario));
                            }

                            misvideosRecyclerVid();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();
                            Log.i("holaa", "holaa fallo");

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                        Log.i("app","makeJsonObj: onErrorResponse List22");
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    private void misvideosRecyclerVid(){
        recyclerViewMis = (RecyclerView) findViewById(R.id.RecylerMisVideos2);
        adapter2 = new VideoInfoAdapter(this,videoList2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewMis.setHasFixedSize(true);
        recyclerViewMis.setLayoutManager(mLayoutManager);
        recyclerViewMis.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMis.setAdapter(adapter2);
    }

    private void makeJsonNumAmigos(String url){

        Log.i("Perfil","makeJsonUser");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Perfil", "onResponse");

                        Gson gson = new Gson();

                        ConsultaNumAmigos c = gson.fromJson(response.toString(), ConsultaNumAmigos.class);

                        if (c.getSuccess() == 1) {

                            Log.i("Perfil", "makeJsonRequest: onResponse - get Success");
                            Log.i("Perfil", "aham"+c.getNumAmigos().get(0).getAmigos());
                            numamigos.setText(Integer.toString(c.getNumAmigos().get(0).getAmigos()));
                            int sum = c.getNumAmigos().get(0).getAmigos();
                            sum = sum+4;

                        }
                        else{

                            Log.i("Perfil","makeJsonRequest: onResponse - NOT Success");
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_json),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Perfil","makeJsonObj: onErrorResponse - no funciona volley");
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);
    }

    private void makeJsonNumSeguidos(String url){
        Log.i("Perfil","makeJsonUser");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Perfil", "onResponse");

                        Gson gson = new Gson();

                        ConsultaNumSeguidos c = gson.fromJson(response.toString(), ConsultaNumSeguidos.class);


                        if (c.getSuccess() == 1) {
                            Log.i("Perfil", "makeJsonRequest: onResponse - get Success");
                            Log.i("Perfil", "aham2 -"+c.getNumSeguidos().get(0).getSeguidos());
                            numsiguiendo.setText(Integer.toString(c.getNumSeguidos().get(0).getSeguidos()));

                        }
                        else{
                            Log.i("Perfil","makeJsonRequest: onResponse - NOT Success");
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_json),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Perfil","makeJsonObj: onErrorResponse - no funciona volley");
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);
        Log.i("Perfil","volley");
    } //SIGUIENDO

    private void makeJsonUser(String url2) {
        Log.i("Perfil","makeJsonUser");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Perfil","onResponse");

                        Gson gson = new Gson();

                        ConsultaArtista c = gson.fromJson(response.toString(),ConsultaArtista.class);

                        if(c.getSuccess()==1){
                            Log.i("Perfil","makeJsonRequest: onResponse - get Success");

                            user.setText(c.getUsers().get(0).getUser());
                            email.setText(c.getUsers().get(0).getEmail());
                            descripcion.setText(c.getUsers().get(0).getDescripcion());
                            score.setText(Float.toString(c.getUsers().get(0).getScore()));

                            descrip = c.getUsers().get(0).getDescripcion();
                            mail = c.getUsers().get(0).getEmail();
                            puntuacion = Float.toString(c.getUsers().get(0).getScore());

                            vidf[0] = c.getUsers().get(0).getF1();
                            vidf[1] = c.getUsers().get(0).getF2();
                            vidf[2] = c.getUsers().get(0).getF3();
                            vidf[3] = c.getUsers().get(0).getF4();

                            vidv[0] = c.getUsers().get(0).getV1();
                            vidv[1] = c.getUsers().get(0).getV2();
                            vidv[2] = c.getUsers().get(0).getV3();
                            vidv[3] = c.getUsers().get(0).getV4();

                            vidt[0] = c.getUsers().get(0).getT1();
                            vidt[1] = c.getUsers().get(0).getT2();
                            vidt[2] = c.getUsers().get(0).getT3();
                            vidt[3] = c.getUsers().get(0).getT4();

                            vids[0] = c.getUsers().get(0).getS1();
                            vids[1] = c.getUsers().get(0).getS2();
                            vids[2] = c.getUsers().get(0).getS3();
                            vids[3] = c.getUsers().get(0).getS4();

                            favoritosRecyclerVid();

                            makeImageRequest(c.getUsers().get(0).getFoto_perfil());
                            foto = c.getUsers().get(0).getFoto_perfil();

                        }
                        else{
                            Log.i("Perfil","makeJsonRequest: onResponse - NOT Success");
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Perfil","makeJsonObj: onErrorResponse - no funciona volley");
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);
    }

    private void makeJsonNumSeguidores(String url){ //SEGUIDORES
        Log.i("PerfilArtista","makeJsonUser");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("PerfilArtista", "onResponse");

                        Gson gson = new Gson();

                        ConsultaNumSeguidos c = gson.fromJson(response.toString(), ConsultaNumSeguidos.class);


                        if (c.getSuccess() == 1) {
                            numseguidores.setText(Integer.toString(c.getNumSeguidos().get(0).getSeguidos()));

                        }
                        else{
                            Log.i("Perfil","makeJsonRequest: onResponse - NOT Success");
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_json),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Perfil","makeJsonObj: onErrorResponse - no funciona volley");
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);
        Log.i("Perfil","volley");
    }

    private void favoritosRecyclerVid() {

        videoList.add(new VideoInfo(vidv[0],vidt[0], vids[0], Uri.parse(vidf[0%vidf.length]),usuario));
        videoList.add(new VideoInfo(vidv[1],vidt[1], vids[1], Uri.parse(vidf[1%vidf.length]),usuario));
        videoList.add(new VideoInfo(vidv[2],vidt[2], vids[2], Uri.parse(vidf[2%vidf.length]),usuario));
        videoList.add(new VideoInfo(vidv[3],vidt[3], vids[3], Uri.parse(vidf[3%vidf.length]),usuario));


        recyclerFav = (RecyclerView) findViewById(R.id.RecylerFavoritos1);
        adapter1 = new VideoInfoAdapter(this,videoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerFav.setLayoutManager(mLayoutManager);
        recyclerFav.setItemAnimator(new DefaultItemAnimator());
        recyclerFav.setAdapter(adapter1);

    }

    private void createSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(this,
                R.array.desplegableA,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapterS);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Seleccio(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("app","onNothing");
            }
        });
    }

    private void Seleccio(int pos) {

        if (pos == 0){

        }

        if(pos == 1){
            //Main
            Intent intent = new Intent(this,Perfil.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
        }

        if(pos == 2){
            //Perfil
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
        }

        if(pos == 4){
            //Categorías
            Intent intent = new Intent(this,Genero.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
        }

        if (pos == 5){
            //Agenda
        }

        if (pos == 6){
            //Configuración
            Intent intent = new Intent(this,ConfigUsuario.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
        }
    }

    private ProgressDialog pDialogImage;

    private void makeImageRequest(String url) {
        pDialogImage = new ProgressDialog(this);
        pDialogImage.setMessage("Loading...");
        pDialogImage.show();

        final ImageRequest imageReq = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        fotoperfil.setImageBitmap(response);
                        pDialogImage.hide();
                    }
                },
                400,350,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        fotoperfil.setImageResource(R.mipmap.ic_launcher);
                        pDialogImage.hide();
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_foto),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        Volley.newRequestQueue(this).add(imageReq);
    }

    public void cfollow(View view) {


        if(priva.isChecked()){
            urlf= "https://unguled-flash.000webhostapp.com/Consultas/follow.php?user="
                            +usuario+"&follow="+artistName;
            estado = true;
            int num = Integer.parseInt(numseguidores.getText().toString())+1;
            numseguidores.setText(Integer.toString(num));

        }

        else {
            urlf = "https://unguled-flash.000webhostapp.com/Consultas/unfollow.php?user="
                    + usuario + "&follow=" + artistName;
            estado = false;
            int num = Integer.parseInt(numseguidores.getText().toString())-1;
            numseguidores.setText(Integer.toString(num));
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urlf, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Configuración", "onResponseBotonGustos");

                        Gson gson = new Gson();

                        ConsultaResponse c = gson.fromJson(response.toString(), ConsultaUserPropi.class);


                        if (c.getSuccess() == 1) {
                            Log.i("Configuracion", "makeJsonRequest: onResponse - get Success");

                            if(estado==true) {
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.a_follow) +" "+ artistName,
                                        Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.a_unfollow) +" "+ artistName,
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.i("Configuracion", "makeJsonRequest: onResponse - NOT Success");
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_follow),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Configuración", "makeJsonObj: onErrorResponse - boton privacidad");
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);
        Log.i("Configuración", "volley");

    }
}
