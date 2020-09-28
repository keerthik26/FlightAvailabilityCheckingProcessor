package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;


public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    static final String originCountry = "Sydney";
    static final int month = 9;
    static final int onwardsDate = 1;
    public static void main(String[] args) {
        logger.info("Account SID: {}, Auth_token: {}, KK_mobile: {}, CV_MOBILE: {}",
                System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"), System.getenv("KK_MOBILE_ENV"),System.getenv("CV_MOBILE"));
        String page = "https://mea.gov.in/phase-6.htm";
        String flightsMessage = "";

        WhatsappSender whatsappSender = new WhatsappSender();
        while (true) {
            try {
                String message = getAvailableFlights(page);
                logger.debug( " *** Message size is: " + message.length() + " ***");
                // System.out.println(message);
                if(!flightsMessage.equalsIgnoreCase(message)){
                    flightsMessage = message;
                    String customerMessage = message.length() == 0 ? "No flights" : message;
                    logger.info("Sending msg: " + customerMessage);
                    whatsappSender.sendMessage(customerMessage);
                    logger.info("Sent message through Whatsapp");
                    //whatsappSender.sendVoiceCall(message);
                }
                Thread.sleep(15 * 60 * 1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static String getAvailableFlights(String page) throws IOException {
        Connection conn = Jsoup.connect(page);
        return conn.get().getElementById("innerContent").getElementsByTag("tr").stream()
                .filter(element -> element.select("td").size() > 0)
                .map(row -> transformToFlight(row))
                .filter(flight -> flight.getOrigin().equalsIgnoreCase(App.originCountry) && flight.getArrDate().isAfter(LocalDate.of(2020, App.month, App.onwardsDate)))
                .sorted(Comparator.comparing(Flight::getArrDate))
                .map(Flight::customerMessage).collect(Collectors.joining("\r\n"));
    }

    private static Flight transformToFlight(Element row) {

        Elements tds = row.select("td");
        return Flight.build(tds.get(0).text(), tds.get(1).text(), tds.get(2).text(), tds.get(3).text(), tds.get(4).text(), tds.get(5).text(), tds.get(6).text(),
                tds.get(7).text(), tds.get(8).text(), tds.get(9).text(), tds.get(10).text(), tds.get(11).text());

    }
}


