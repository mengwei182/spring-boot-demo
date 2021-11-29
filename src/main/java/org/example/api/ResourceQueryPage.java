package org.example.api;

import org.example.common.model.QueryPage;

public class ResourceQueryPage extends QueryPage {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        super.addQueryParam("name", "%".concat(this.name).concat("%"));
    }
}