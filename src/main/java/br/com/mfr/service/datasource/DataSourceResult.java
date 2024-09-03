package br.com.mfr.service.datasource;

import java.util.Objects;

public record DataSourceResult(DataSourceType type, String result) {

    public static Object newResult(DataSourceType type, String result) {
        return new DataSourceResult(type, result);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DataSourceResult otherResult = (DataSourceResult) obj;
        return Objects.equals(this.type, otherResult.type) && this.result.equals(otherResult.result);
    }
}
