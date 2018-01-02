package com.lf.gw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="sharding.jdbc",ignoreUnknownFields=false)
public class ShardingJdbcProperties {
}
