package com.example.manuelsanchezferna.json;

import java.util.List;

/**
 * Created by manuel.sanchez.ferna on 01/12/2017.
 */

public class ConsultaResponse {
    private int success;

    public int getSuccess() {return success;}

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ConsultaResponse{success=" + success + '}';
    }

}
