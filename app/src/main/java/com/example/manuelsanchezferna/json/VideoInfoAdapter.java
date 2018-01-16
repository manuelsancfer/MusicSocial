package com.example.manuelsanchezferna.json;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

    private void Seleccio(int pos) {

        if (pos == 0){

        }

        if (pos == 1){
            Log.i("VideoInfoAdapter","Peñíscola");

        }

        if(pos == 2){


        }

        if(pos == 3){

        }

        if (pos == 4){

        }

    }

}
