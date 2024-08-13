package br.com.mfr.service.yahoo;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static br.com.mfr.ConfigProperties.APP_EXTERNAL_PREFIX;

@Validated
@ConfigurationProperties(APP_EXTERNAL_PREFIX + ".yahoo.url")
public record YahooURLProperties(@NotBlank String cookies, @NotBlank String crumb, @NotBlank String etfScreener){ }