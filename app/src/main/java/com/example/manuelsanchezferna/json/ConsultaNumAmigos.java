package com.example.manuelsanchezferna.json;

import java.util.List;

/**
 * Created by DjManko on 7/1/18.
 */

public class ConsultaNumAmigos extends ConsultaResponse {

    private List<NumAmigos> NumAmigos;

    public List<com.example.manuelsanchezferna.json.NumAmigos> getNumAmigos() {
        return NumAmigos;
    }

    public void setNumAmigos(List<com.example.manuelsanchezferna.json.NumAmigos> numAmigos) {
        NumAmigos = numAmigos;
    }

    @Override
    public String toString() {
        return "ConsultaNumAmigos{" +
                "NumAmigos=" + NumAmigos +
                '}';
    }

}
