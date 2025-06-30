package com.weiz.spb.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(
        prefix = "jwt",
        ignoreUnknownFields = false
)
public class JwtProperties {

    private String secretKey;
    private int expiration;
    private int refreshDuration;
    private String issuer;
    private String audience;
}
