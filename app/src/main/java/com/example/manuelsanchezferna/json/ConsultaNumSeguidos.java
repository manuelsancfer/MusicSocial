package com.example.manuelsanchezferna.json;

import java.util.List;

/**
 * Created by DjManko on 7/1/18.
 */

public class ConsultaNumSeguidos extends ConsultaResponse {

    private List<NumSeguidos> NumSeguidos;

    public List<com.example.manuelsanchezferna.json.NumSeguidos> getNumSeguidos() {
        return NumSeguidos;
    }

    public void setNumSeguidos(List<com.example.manuelsanchezferna.json.NumSeguidos> numSeguidos) {
        NumSeguidos = numSeguidos;
    }

    @Override
    public String toString() {
        return "ConsultaNumSeguidos{" +
                "NumSeguidos=" + NumSeguidos +
                '}';
    }

}
