package com.example.manuelsanchezferna.json;

/**
 * Created by DjManko on 7/1/18.
 */

public class NumSeguidos {

    private int seguidos;


    public int getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(int seguidos) {
        this.seguidos = seguidos;
    }

    @Override
    public String toString() {
        return "NumSeguidos{" +
                "seguidos=" + seguidos +
                '}';
    }
}
