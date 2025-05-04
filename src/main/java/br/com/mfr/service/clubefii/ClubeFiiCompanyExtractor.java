package br.com.mfr.service.clubefii;

import br.com.mfr.entity.Company;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.util.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class ClubeFiiCompanyExtractor {

    private ClubeFiiCompanyExtractor(){}

    public static List<Company> extractCompaniesFrom(Document document, DataSourceType sourceType) {
        var table = document.getElementById("tabela_profile");
        Elements rows = table.child(1).getElementsByClass("tabela_principal");

        return rows.stream()
                .map(row -> {
                    var cells = row.select("td");
                    return convert(cells, sourceType);
                }).toList();
    }

    private static Company convert(Elements rowCells, DataSourceType type) {
        return Company.builder()
                .withSource(type)
                .withName(rowCells.get(1).text())
                .withTicker(rowCells.get(0).text())
                .withPrice(getPrice(rowCells))
                .withDy(normalizeToDouble(rowCells.get(9).text()))
                .withPatrimonio(normalizeToDouble(rowCells.get(4).text()))
                .withVpa(normalizeToDouble(rowCells.get(5).text()))
                .withValorMercado(normalizeToDouble(rowCells.get(6).text()))
                .withPvp(normalizeToDouble(rowCells.get(10).text()))
                .withNumeroCotistas(normalizeToDouble(rowCells.get(11).text()))
                .withLiquidezMediaDiaria(normalizeToDouble(rowCells.get(13).text()))
                .build();
    }

    private static Double getPrice(Elements cells) {
        try {
            return normalizeToDouble(cells.get(2).getElementById("valor_atual_cota").text());
        } catch (IndexOutOfBoundsException | NullPointerException _) {
            return NumberUtils.DOUBLE_ZERO;
        }
    }

    private static Double normalizeToDouble(String value){
        String replacePattern = value.replace("R$", "").replace("%", "").replace(".", "").replace(",", ".");
        return NumberUtils.format(replacePattern);
    }
}
