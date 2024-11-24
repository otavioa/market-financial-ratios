package br.com.mfr.service.clubefii;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static br.com.mfr.PropertiesConfiguration.APP_EXTERNAL_PREFIX;

@Validated
@ConfigurationProperties(APP_EXTERNAL_PREFIX + ".clubefii.url")
public record ClubeFiiURLProperties(@NotBlank String fiiInfra, @NotBlank String fiiAgro){ }