package org.example.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.model.QueryPage;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceQueryPage extends QueryPage {
    private String name;
}