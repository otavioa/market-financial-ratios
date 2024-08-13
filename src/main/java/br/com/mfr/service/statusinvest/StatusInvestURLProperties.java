package br.com.mfr.service.statusinvest;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static br.com.mfr.ConfigProperties.APP_EXTERNAL_PREFIX;

@Validated
@ConfigurationProperties(APP_EXTERNAL_PREFIX + ".statusinvest.url")
public record StatusInvestURLProperties(@NotBlank String ticker, @NotBlank String advanced){ }