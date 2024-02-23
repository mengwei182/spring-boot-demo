package org.example.system.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.model.QueryPage;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuQueryPage extends QueryPage {
    private String name;
}