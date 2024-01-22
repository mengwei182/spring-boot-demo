package org.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Data
@Component
@ConfigurationProperties(prefix = "common")
public class CommonProperties {
    private List<String> skipUrl;
}