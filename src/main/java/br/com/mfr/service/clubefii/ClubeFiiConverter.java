package br.com.mfr.service.clubefii;

import br.com.mfr.entity.Company;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.util.NumberUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ClubeFiiConverter {

    public static Company convert(Element row, DataSourceType type) {
        Elements cells = row.select("td");

        Company company = Company.builder()
                .withSource(type)
                .withName(cells.get(1).text())
                .withTicker(cells.get(0).text())
                .withPrice(getPrice(cells))
                .withDy(normalizeToDouble(cells.get(9).text()))
                .withPatrimonio(normalizeToDouble(cells.get(4).text()))
                .withVpa(normalizeToDouble(cells.get(5).text()))
                .withValorMercado(normalizeToDouble(cells.get(6).text()))
                .withPvp(normalizeToDouble(cells.get(10).text()))
                .withNumeroCotistas(normalizeToDouble(cells.get(11).text()))
                .withLiquidezMediaDiaria(normalizeToDouble(cells.get(13).text()))
                .build();

        return company;
    }

    private static Double getPrice(Elements cells) {
        try {
            return normalizeToDouble(cells.get(2).getElementById("valor_atual_cota").text());
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return NumberUtils.DOUBLE_ZERO;
        }
    }

    private static Double normalizeToDouble(String value){
        String replacePattern = value.replace("R$", "").replace("%", "").replace(".", "").replace(",", ".");
        return NumberUtils.format(replacePattern);
    }
}
