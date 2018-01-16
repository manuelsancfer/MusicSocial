package com.example.manuelsanchezferna.json;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**Layout individual del vídeo (horizontal)**/


public class Video extends AppCompatActivity {

    private VideoView videoView;

    private List<VideoInfo> videoList = new ArrayList<VideoInfo>();
    private String[] videosURLs= new String[2];

    private String cancionName, web;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent1 = getIntent();
        cancionName = intent1.getStringExtra("KEY_CANCION_NAME");
        usuario = intent1.getStringExtra("KEY_USUARIO");

        Toast.makeText(getApplicationContext(),cancionName, Toast.LENGTH_LONG).show();

        cancionName = cancionName.replaceAll(" ","+");

        makeJsonVid("https://unguled-flash.000webhostapp.com/Consultas/consultasolovideo.php?tittle="
                +cancionName);

    }

    private void makeJsonVid(String url) {
        Log.i("VideoSolo","makeJsonVid");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Video","onResponse");

                        Gson gson = new Gson();

                        ConsultaVideos c = gson.fromJson(response.toString(),ConsultaVideos.class);

                        if(c.getSuccess()==1){
                            Log.i("Video","makeJsonRequest: onResponse - get Success");
                            videosURLs[0] = c.getVideos().get(0).getUrl();
                            web = c.getVideos().get(0).getUrl().toString();
                            videoList.add(new VideoInfo(c.getVideos().get(0).getName().toString(),
                                    c.getVideos().get(0).getTittle().toString(),
                                    c.getVideos().get(0).getScore(),
                                    Uri.parse(videosURLs[0%videosURLs.length]),usuario));

                            videosRecyclerVid();
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
                                getResources().getString(R.string.i_song),
                                Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);
    }

    private void videosRecyclerVid() {

        Uri uri = Uri.parse(videosURLs[0%videosURLs.length]);

        videoView = (VideoView) findViewById(R.id.SoloVideo);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }


}
