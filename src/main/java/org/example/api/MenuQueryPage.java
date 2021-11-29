package org.example.api;

import org.example.common.model.QueryPage;

public class MenuQueryPage extends QueryPage {
    private String name;

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
        super.addQueryParam("name", "%".concat(this.name).concat("%"));
    }
}