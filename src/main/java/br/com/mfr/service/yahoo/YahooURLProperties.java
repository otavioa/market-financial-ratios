package br.com.mfr.service.yahoo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static br.com.mfr.ConfigProperties.APP_EXTERNAL_PREFIX;

@ConfigurationProperties(APP_EXTERNAL_PREFIX + ".yahoo.url")
public record YahooURLProperties(String cookies, String crumb, String etfScreener){ }