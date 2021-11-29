package org.example.api;

import org.example.common.model.QueryPage;

public class UserQueryPage extends QueryPage {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        super.addQueryParam("username", "%".concat(this.username).concat("%"));
    }
}