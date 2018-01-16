package com.example.manuelsanchezferna.json;

import java.util.List;

/**
 * Created by DjManko on 12/1/18.
 */

public class ConsultaArtista extends ConsultaResponse {

    public List<Artista> getUsers() {
        return users;
    }

    public void setUsers(List<Artista> users) {
        this.users = users;
    }

    private List<Artista> users;

}
