package com.example.manuelsanchezferna.json;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;


/**
 * Created by Maria on 27/12/2017.
 * Adaptador línia de vídeos a la pantalla principal
 */

class VideoInfoAdapter extends RecyclerView.Adapter<VideoInfoAdapter.ViewHolder>{

    private List<VideoInfo> videoInfoList;
    private Context context;
    public String artista, cancion,usuario;


    public VideoInfoAdapter(Context context, List<VideoInfo> videoInfoList) {
        this.context = context;
        this.videoInfoList = videoInfoList;
    }

    @Override
    public VideoInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoInfoAdapter.ViewHolder holder, final int position) {
        holder.cancion.setText(videoInfoList.get(position).getCancion() + " - " +
                videoInfoList.get(position).getPuntuacion()); //passem a la activitat del video en gran
        holder.cancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(context,Video.class);
                intent2.putExtra("KEY_CANCION_NAME", videoInfoList.get(position).getCancion());
                intent2.putExtra("KEY_USUARIO", videoInfoList.get(0).getUsuario());
                context.startActivity(intent2);
            }
        });

        holder.artista.setText(videoInfoList.get(position).getArtista());
        holder.artista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PerfilArtista.class);
                intent.putExtra("KEY_ARTISTA_NAME",videoInfoList.get(position).getArtista());
                intent.putExtra("KEY_USUARIO",  videoInfoList.get(0).getUsuario());
                context.startActivity(intent);
            }
        });

        artista = videoInfoList.get(position).getArtista();
        cancion = videoInfoList.get(position).getCancion();
        cancion = cancion.replaceAll(" ","+");
        usuario = videoInfoList.get(position).getUsuario();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(context);
            mediacontroller.setAnchorView(holder.videoview);
            
            holder.videoview.setMediaController(mediacontroller);
            holder.videoview.setVideoURI(videoInfoList.get(position).getVideoUri());


        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        holder.videoview.requestFocus();
    }

    @Override
    public int getItemCount() {
        return videoInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private VideoView videoview;
        private Button artista, cancion;
        private Spinner spinner;

        public ViewHolder(View itemView) {
            super(itemView);
            artista = (Button) itemView.findViewById(R.id.btn_artista);
            cancion = (Button) itemView.findViewById(R.id.btn_cancion);
            videoview = (VideoView) itemView.findViewById(R.id.videoView);
            spinner = (Spinner) itemView.findViewById(R.id.spinnerv);

            createSpinner(spinner);

        }
    }

    private void createSpinner(Spinner spinner) {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.addfav,android.R.layout.simple_spinner_item);

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

    private String idv;

    private void Seleccio(int pos) {
        /**ESTA CONSULTA PERMITE CAMBIAR LOS 4 VIDEOS FAVORITOS. LOS PHP ESTÁN NECHOS Y FUNCIONAN
            PERO NO HEMOS CONSEGUIDO IMPLEMENTARLA EN ANDROID
         **/
        if (pos == 0){


            }

        if (pos == 1){
            String url ="https://unguled-flash.000webhostapp.com/Consultas/ConsultaFav.php?artista="+
                    artista+"&cancion="+cancion;

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Configuración", "onResponse");

                            Gson gson = new Gson();

                            ConsultaFav c = gson.fromJson(response.toString(), ConsultaFav.class);
                            //idv = c.getIdvideo().get(1).getId();

                            Log.i("holacaracola","ey"+c);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("VideoInfoAdapt", "error en el servidor, intentelo más tarde");
                        }
                    });

            Volley.newRequestQueue(this.context).add(jsObjRequest);
            //UpdateFav(pos);
        }

        if(pos == 2){
            UpdateFav(pos);
        }

        if(pos == 3){
            UpdateFav(pos);
        }

        if (pos == 4){
            UpdateFav(pos);
        }

    }

    private void UpdateFav(int pos){
        String url2 =
                "https://unguled-flash.000webhostapp.com/Consultas/updateconfig_favoritos.php?favorito="
                        +pos+"&user=" +usuario+"&idvideo=1";
        Log.i("prueba","hola "+usuario+"-"+idv+"-"+pos);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Configuración", "onResponse update");

                        Gson gson = new Gson();

                        ConsultaResponse c = gson.fromJson(response.toString(), ConsultaResponse.class);
                        if(c.getSuccess() ==1){
                           Log.i("Update favorito", "No se ha podido conectar con el servidor");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VideoInfoAdapt", "error en el servidor, intentelo más tarde");
                    }
                });
        Volley.newRequestQueue(context).add(jsObjRequest);

    }

}
