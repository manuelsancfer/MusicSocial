package com.example.manuelsanchezferna.json;

import java.util.List;

/**
 * Created by DjManko on 16/1/18.
 */

public class ConsultaFav extends ConsultaResponse {

    private List<IdVideo> idvideo;

    public List<IdVideo> getIdvideo() {
        return idvideo;
    }

    public void setIdvideo(List<IdVideo> idvideo) {
        this.idvideo = idvideo;
    }

    @Override
    public String toString() {
        return "ConsultaFav{" +
                "idvideo=" + idvideo +
                '}';
    }

}
