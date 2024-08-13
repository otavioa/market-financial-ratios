package br.com.mfr.service.statusinvest;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static br.com.mfr.ConfigProperties.APP_EXTERNAL_PREFIX;

@ConfigurationProperties(APP_EXTERNAL_PREFIX + ".statusinvest.url")
public record StatusInvestURLProperties( String ticker, String advanced){ }