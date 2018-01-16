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

public class Perfil extends AppCompatActivity {
    public static String  KEY_NAME = "KEY_NAME";
    public static int REQUEST_NAME = 1;

    private ImageView fotoperfil;
    private TextView user, email, gustos, numamigos, numsiguiendo;


    private RecyclerView recyclerView;
    private VideoInfoAdapter adapter;
    private List<VideoInfo> videoList = new ArrayList<VideoInfo>();

    private String[] vidf =  new String[4];
    private String[] vidt = new String[4];
    private String[] vidv = new String[4];
    private float[] vids = new float[4];

    private String usuarioName;
    private String g_musicales, mail,foto;

    private  TextView btn;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("gustos_musicales", g_musicales);
        outState.putString("email",mail);
        outState.putString("foto_perfil",foto);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_usuari);

        user = (TextView) findViewById(R.id.usernamee);
        email = (TextView) findViewById(R.id.email);
        gustos = (TextView) findViewById(R.id.gustos_musicales);
        numamigos =(TextView) findViewById(R.id.numamigos);
        numsiguiendo = (TextView) findViewById(R.id.numseguidos);
        fotoperfil = (ImageView) findViewById(R.id.imagen_perfil);
        btn = (TextView) findViewById(R.id.title);

        Intent intent = getIntent();
        usuarioName = intent.getStringExtra("KEY_USUARIO");

        if (savedInstanceState != null){
            Bundle state = savedInstanceState;
            gustos.setText(state.getString("gustos_musicales"));
            email.setText(state.getString("mail"));
            makeImageRequest(state.getString("foto_perfil"));
        }


        makeJsonUser("https://unguled-flash.000webhostapp.com/Consultas/consultaperfilpropio.php?user="
                +usuarioName);
        makeJsonNumAmigos("https://unguled-flash.000webhostapp.com/Consultas/ConsultaAmigos.php?user="
                +usuarioName);
        makeJsonNumSeguidos("https://unguled-flash.000webhostapp.com/Consultas/ConsultaSeguidoresU.php?user="
                +usuarioName);


        createSpinner();
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
                            numamigos.setText(Integer.toString(c.getNumAmigos().get(0).getAmigos()));
                            int sum = c.getNumAmigos().get(0).getAmigos();

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
    }

    private void makeJsonUser(String url2) {
        Log.i("Perfil","makeJsonUser");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Perfil","onResponse");

                Gson gson = new Gson();

                ConsultaUserPropi c = gson.fromJson(response.toString(),ConsultaUserPropi.class);

                if(c.getSuccess()==1){
                    Log.i("Perfil","makeJsonRequest: onResponse - get Success");

                    user.setText(c.getUsers().get(0).getUser());
                    email.setText(c.getUsers().get(0).getEmail());
                    gustos.setText(c.getUsers().get(0).getGustos_musicales());

                    g_musicales = c.getUsers().get(0).getGustos_musicales();
                    mail = c.getUsers().get(0).getEmail();


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

                    videosRecyclerVid();

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

    private void videosRecyclerVid() {

        videoList.add(new VideoInfo(vidv[0],vidt[0], vids[0], Uri.parse(vidf[0%vidf.length]),usuarioName));
        videoList.add(new VideoInfo(vidv[1],vidt[1], vids[1], Uri.parse(vidf[1%vidf.length]),usuarioName));
        videoList.add(new VideoInfo(vidv[2],vidt[2], vids[2], Uri.parse(vidf[2%vidf.length]),usuarioName));
        videoList.add(new VideoInfo(vidv[3],vidt[3], vids[3], Uri.parse(vidf[3%vidf.length]),usuarioName));


        recyclerView = (RecyclerView) findViewById(R.id.RecylerViewFavoritos);
        adapter = new VideoInfoAdapter(this,videoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void createSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(this,
                R.array.desplegable2,android.R.layout.simple_spinner_item);

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

        if(pos == 1){
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("KEY_USUARIO", usuarioName);
            startActivity(intent);
        }

        if(pos == 2){
            //Lista rep
        }

        if(pos == 3){
            //Categorías
            Intent intent = new Intent(this,Genero.class);
            intent.putExtra("KEY_USUARIO", usuarioName);
            startActivity(intent);
        }

        if (pos == 4){
            //Agenda
        }

        if (pos == 5){
            //Configuración
            Intent intent = new Intent(this,ConfigUsuario.class);
            intent.putExtra("KEY_USUARIO", usuarioName);
            intent.putExtra("KEY_FOTO",foto);
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
        Log.i("Perfil","Error imagen imagen bien");
    }


}



