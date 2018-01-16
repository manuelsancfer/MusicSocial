package com.example.manuelsanchezferna.json;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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

public class TopVideosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VideoInfoAdapter adapter1;
    private List<VideoInfo> videoList = new ArrayList<VideoInfo>();

    private GridLayoutManager layoutManager;

    private String[] videosURLs= new String[10];

    private String usuario;

    private ProgressDialog pDialogImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topvideos);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("KEY_USUARIO");

        videosGridVid("https://unguled-flash.000webhostapp.com/Consultas/topvideos.php");

        createSpinner();
    }

    private void videosGridVid(String url) {
        pDialogImage2 = new ProgressDialog(this);
        pDialogImage2.setMessage("Loading...");
        pDialogImage2.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1) {
                            for (int i = 0; i < 10; i++) {
                                videosURLs[i] = c.getVideos().get(i).getUrl();
                                videoList.add(new VideoInfo(c.getVideos().get(i).getName().toString(),
                                        c.getVideos().get(i).getTittle().toString(),
                                        c.getVideos().get(i).getScore(),
                                        Uri.parse(videosURLs[i%videosURLs.length]),usuario));
                            }
                            videos();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                    response.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.i_videos),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("app","makeJsonObj: onErrorResponse List2");
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.i_json),
                                Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);




    }

    private void videos(){

        recyclerView = (RecyclerView) findViewById(R.id.RecylerView2);
        layoutManager = new GridLayoutManager(TopVideosActivity.this,2);
        adapter1 = new VideoInfoAdapter(this,videoList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter1);
        pDialogImage2.hide();

    }

    private void createSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.desplegable,android.R.layout.simple_spinner_item);

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
            public void onNothingSelected(AdapterView<?> adapterView) {Log.i("app","onNothing");}
        });
    }

    private void Seleccio(int pos) {

        if (pos == 0){

        }

        if (pos == 1){
            //Perfil propio usuario
            Intent intent = new Intent(this,Perfil.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
        }

        if(pos == 2){
            //Lista rep
        }

        if(pos == 3){
            //Categorías
            Intent intent = new Intent(this,Genero.class);
            intent.putExtra("KEY_USUARIO", usuario);
            startActivity(intent);
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
