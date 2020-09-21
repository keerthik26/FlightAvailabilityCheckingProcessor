package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        String page = "https://mea.gov.in/phase-6.htm";
        final String originCountry = "Sydney";
        int month = 9;
        int onwardsDate = 1;
        try {
            while (true) {
                Connection conn = Jsoup.connect(page);
                conn.get().getElementById("innerContent").getElementsByTag("tr").stream()
                        .filter(element -> element.select("td").size() > 0)
                        .map(row -> transformToFlight(row))
                        .filter(flight -> flight.getOrigin().equalsIgnoreCase(originCountry) && flight.getArrDate().isAfter(LocalDate.of(2020, month, onwardsDate)))
                        .map(Flight::customerMessage).forEach(System.out::println);
                Thread.sleep(15 * 60 * 1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Flight transformToFlight(Element row) {

        Elements tds = row.select("td");
        return Flight.build(tds.get(0).text(), tds.get(1).text(), tds.get(2).text(), tds.get(3).text(), tds.get(4).text(), tds.get(5).text(), tds.get(6).text(),
                tds.get(7).text(), tds.get(8).text(), tds.get(9).text(), tds.get(10).text(), tds.get(11).text());

    }
}


