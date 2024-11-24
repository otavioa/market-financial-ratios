package br.com.mfr.service.clubefii;

import br.com.mfr.service.datasource.DataSourceType;

import java.util.Arrays;

public enum ClubeFiiSourceType {

    FII_INFRA(DataSourceType.BRL_FII_INFRA) {

        @Override
        public String getURL(ClubeFiiURLProperties urlProps) {
            return urlProps.fiiInfra();
        }
    },
    FII_AGRO(DataSourceType.BRL_FII_AGRO) {

        @Override
        public String getURL(ClubeFiiURLProperties urlProps) {
            return urlProps.fiiAgro();
        }
    };

    private final DataSourceType type;

    public abstract String getURL(ClubeFiiURLProperties urlProps);

    ClubeFiiSourceType(DataSourceType type) {
        this.type = type;
    }

    public static ClubeFiiSourceType valueOf(DataSourceType type) {
        return Arrays.stream(values())
                .filter(v -> v.type.equals(type))
                .findFirst()
                .orElseThrow();
    }

}
