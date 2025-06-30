package com.weiz.spb.config;

import com.weiz.spb.config.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties({JwtProperties.class})
public class PropertyConfig {
}
