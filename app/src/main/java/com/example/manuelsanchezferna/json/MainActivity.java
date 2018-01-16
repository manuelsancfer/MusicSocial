package com.example.manuelsanchezferna.json;

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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewVid, recyclerViewTop;
    private VideoInfoAdapter adapter1, adapter2;
    private List<VideoInfo> videoList = new ArrayList<VideoInfo>();
    private List<VideoInfo> videoList2 = new ArrayList<VideoInfo>();

    private String[] videosURLs= new String[10];
    private String[] videosURLsTop= new String[10];

    private String usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button top = (Button) findViewById(R.id.btn_topvideos);
        Button vid = (Button) findViewById(R.id.btn_videos);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("KEY_USUARIO");

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topvideosActivity();
            }
        });

        vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videosActivity();
            }
        });


        JvideosRecyclerVid("https://unguled-flash.000webhostapp.com/Consultas/consultavideos.php");
        JvideosRecyclerTop("https://unguled-flash.000webhostapp.com/Consultas/topvideos.php");

        createSpinner();

    }


    private void JvideosRecyclerVid(String url) {

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

                            videosRecyclerVid();
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

    private void videosRecyclerVid(){

        recyclerViewVid = (RecyclerView) findViewById(R.id.RecylerViewVid);
        adapter1 = new VideoInfoAdapter(this,videoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewVid.setHasFixedSize(true);
        recyclerViewVid.setLayoutManager(mLayoutManager);
        recyclerViewVid.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVid.setAdapter(adapter1);

    }

    private void JvideosRecyclerTop(String url) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1) {
                            for (int i = 0; i < 10; i++) {
                                videosURLsTop[i] = c.getVideos().get(i).getUrl();
                                videoList2.add(new VideoInfo(c.getVideos().get(i).getName().toString(),
                                        c.getVideos().get(i).getTittle().toString(),
                                        c.getVideos().get(i).getScore(),
                                        Uri.parse(videosURLsTop[i%videosURLsTop.length]), usuario));
                            }
                            videosRecyclerVidTop();
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

    private void videosRecyclerVidTop() {

        recyclerViewTop = (RecyclerView) findViewById(R.id.RecylerViewTop);
        adapter2 = new VideoInfoAdapter(this,videoList2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTop.setHasFixedSize(true);
        recyclerViewTop.setLayoutManager(mLayoutManager);
        recyclerViewTop.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTop.setAdapter(adapter2);


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

    //onClick del boto topvideos que redirecciona al seu propi Activity
    public void topvideosActivity(){
        Intent intent = new Intent(this,TopVideosActivity.class);
        intent.putExtra("KEY_USUARIO", usuario);
        startActivity(intent);
    }

    //onClick del boto videos que redirecciona al seu propi Activity
    public void videosActivity(){
        Intent intent = new Intent(this,VideosActivity.class);
        intent.putExtra("KEY_USUARIO", usuario);
        startActivity(intent);
    }



}
