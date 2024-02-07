package org.example.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.model.QueryPage;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryPage extends QueryPage {
    private String username;
}