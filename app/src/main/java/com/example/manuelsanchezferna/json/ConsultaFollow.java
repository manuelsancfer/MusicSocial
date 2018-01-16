package com.example.manuelsanchezferna.json;

import java.util.List;

/**
 * Created by DjManko on 13/1/18.
 */

public class ConsultaFollow extends ConsultaResponse {

    private List<Follow> follow;

    public List<Follow> getFollow() {
        return follow;
    }

    public void setFollow(List<Follow> follow) {
        this.follow = follow;
    }

    @Override
    public String toString() {
        return "ConsultaFollow{" +
                "follow=" + follow +
                '}';
    }

}
