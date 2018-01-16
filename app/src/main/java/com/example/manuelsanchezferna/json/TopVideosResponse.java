package com.example.manuelsanchezferna.json;

/**
 * Created by manuel.sanchez.ferna on 01/12/2017.
 */

public class TopVideosResponse {
    private String videos;
    private int success;

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "TopVideoResponse{" +
                "videos='" + videos + '\'' +
                ", success=" + success +
                '}';
    }
}
