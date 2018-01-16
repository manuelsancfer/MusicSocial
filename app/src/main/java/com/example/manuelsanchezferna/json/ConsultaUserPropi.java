package com.example.manuelsanchezferna.json;

import java.util.List;

/**
 * Created by DjManko on 27/12/17.
 */

public class ConsultaUserPropi extends ConsultaResponse {

    private List<User> users;

    public List<User> getUsers() {

        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
