package com.example.manuelsanchezferna.json;

/**
 * Created by manuel.sanchez.ferna on 01/12/2017.
 */

public class Videos {
    private String tittle;
    private String name;
    private String genre;
    private String tags;
    private float score;
    private String url;
    private String calidad;


    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    @Override
    public String toString() {
        return "TopVideoResponse{" +
                "tittle='" + tittle + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", tags='" + tags + '\'' +
                ", score=" + score +
                ", url='" + url + '\'' +
                ", calidad='" + calidad + '\'' +
                '}';
    }
}
