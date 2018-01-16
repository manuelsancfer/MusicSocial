package com.example.manuelsanchezferna.json;

import java.util.List;

/**
 * Created by DjManko on 14/12/17.
 */

public class ConsultaVideos extends ConsultaResponse {

    private List<Videos> videos;

    public List<Videos> getVideos() {
        return videos;
    }

    public void setVideos(List<Videos> videos) {
        this.videos = videos;
    }

}
