package com.example.manuelsanchezferna.json;

/**
 * Created by DjManko on 12/1/18.
 */

public class Artista {

    private String user;
    private String descripcion;
    private String foto_perfil;
    private float score;
    private String email;
    //videos favoritos
    private String f1;
    private String f2;
    private String f3;
    private String f4;
    //artista de videos
    private String v1;
    private String v2;
    private String v3;
    private String v4;
    //título de videos
    private String t1;
    private String t2;
    private String t3;
    private String t4;
    //score de videos
    private float s1;
    private float s2;
    private float s3;
    private float s4;

    @Override
    public String toString() {


        return "Artista{" +
                "user='" + user + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", foto_perfil='" + foto_perfil + '\'' +
                ", score=" + score +
                ", email='" + email + '\'' +
                ", f1='" + f1 + '\'' +
                ", f2='" + f2 + '\'' +
                ", f3='" + f3 + '\'' +
                ", f4='" + f4 + '\'' +
                ", v1='" + v1 + '\'' +
                ", v2='" + v2 + '\'' +
                ", v3='" + v3 + '\'' +
                ", v4='" + v4 + '\'' +
                ", t1='" + t1 + '\'' +
                ", t2='" + t2 + '\'' +
                ", t3='" + t3 + '\'' +
                ", t4='" + t4 + '\'' +
                '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }

    public String getT4() {
        return t4;
    }

    public void setT4(String t4) {
        this.t4 = t4;
    }

    public float getS1() { return s1;}

    public void setS1(float s1) { this.s1 = s1;}

    public float getS2() { return s2;}

    public void setS2(float s2) { this.s2 = s2;}

    public float getS3() { return s3;}

    public void setS3(float s3) { this.s3 = s3;}

    public float getS4() { return s4;}

    public void setS4(float s4) { this.s4 = s4;}
}
