package org.example.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.model.QueryPage;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryQueryPage extends QueryPage {
    private String name;
}