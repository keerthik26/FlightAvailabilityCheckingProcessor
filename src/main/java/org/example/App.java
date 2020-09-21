package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args)  {

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
                        .forEach(System.out::println);
            Thread.sleep(15 * 60 * 1000);
        }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Flight transformToFlight(Element row) {

        Elements tds = row.select("td");
        Flight flight  = new Flight();
        flight.setCountryOfOrigin(tds.get(2).text());
        flight.setFlightNo(tds.get(3).text());
        flight.setOrigin(tds.get(4).text());
        flight.setDepDate(LocalDate.parse(tds.get(5).text(), DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH)));
        flight.setDepTime(tds.get(6).text());
        flight.setDestination(tds.get(7).text());
        flight.setArrDate(LocalDate.parse(tds.get(8).text(),DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH)));
        flight.setArrTime(tds.get(9).text());
        flight.setCapacity(tds.get(10).text());
        flight.setRemarks(tds.get(11).text());

        return flight;
    }
}

/*<th>No.</th>
<th width="92">Receiving State / UT</th>
<th>Country of Origin</th>
<th>Flight No.</th>
<th>Origin</th>
<th>Dep Date</th>
<th>Dep Time</th>
<th>Destination</th>
<th>Arrv Date</th>
<th>Arrv Time</th>
<th>Capacity</th>
<th>Remarks</th>*/

class Flight{
    int sNo;
    String receivingState;
    String countryOfOrigin;
    String flightNo;
    String origin;
    LocalDate depDate;
    String depTime;
    String destination;
    LocalDate arrDate;
    String arrTime;
    String capacity;
    String remarks;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getReceivingState() {
        return receivingState;
    }

    public void setReceivingState(String receivingState) {
        this.receivingState = receivingState;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDate getDepDate() {
        return depDate;
    }

    public void setDepDate(LocalDate depDate) {
        this.depDate = depDate;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getArrDate() {
        return arrDate;
    }

    public void setArrDate(LocalDate arrDate) {
        this.arrDate = arrDate;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "countryOfOrigin='" + countryOfOrigin + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", origin='" + origin + '\'' +
                ", depDate='" + depDate + '\'' +
                ", depTime='" + depTime + '\'' +
                ", destination='" + destination + '\'' +
                ", arrDate='" + arrDate + '\'' +
                ", arrTime='" + arrTime + '\'' +
                ", capacity='" + capacity + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}