package org.example.system.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.core.domain.QueryPage;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryPage extends QueryPage {
    private String username;
}