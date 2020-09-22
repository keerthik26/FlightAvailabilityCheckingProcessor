package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
<th>No.</th>
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
<th>Remarks</th>
*/
public class Flight {
    private final int sNo;
    private final String receivingState;
    private final String countryOfOrigin;
    private final String flightNo;
    private final String origin;
    private final LocalDate depDate;
    private final String depTime;
    private final String destination;
    private final LocalDate arrDate;
    private final String arrTime;
    private final String capacity;
    private final String remarks;

    private Flight(int sNo, String receivingState, String countryOfOrigin, String flightNo, String origin, LocalDate depDate, String depTime, String destination, LocalDate arrDate, String arrTime, String capacity, String remarks) {
        this.sNo = sNo;
        this.receivingState = receivingState;
        this.countryOfOrigin = countryOfOrigin;
        this.flightNo = flightNo;
        this.origin = origin;
        this.depDate = depDate;
        this.depTime = depTime;
        this.destination = destination;
        this.arrDate = arrDate;
        this.arrTime = arrTime;
        this.capacity = capacity;
        this.remarks = remarks;
    }

    public static Flight build(String sNo, String receivingState, String countryOfOrigin, String flightNo, String origin, String depDate, String depTime, String destination, String arrDate, String arrTime, String capacity, String remarks) {
        return new Flight(Integer.parseInt(sNo), receivingState, countryOfOrigin, flightNo, origin, LocalDate.parse(depDate, DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH)), depTime, destination, LocalDate.parse(arrDate, DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH)), arrTime, capacity, remarks);
    }

    public String getOrigin() {
        return origin;
    }

    public LocalDate getArrDate() {
        return arrDate;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "sNo=" + sNo +
                ", receivingState='" + receivingState + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", origin='" + origin + '\'' +
                ", depDate=" + depDate +
                ", depTime='" + depTime + '\'' +
                ", destination='" + destination + '\'' +
                ", arrDate=" + arrDate +
                ", arrTime='" + arrTime + '\'' +
                ", capacity='" + capacity + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public String customerMessage() {
        return
                "Flight *" + flightNo + '*' +
                " departs on *" + depDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy")) + '*' +
                " at *" + depTime + '*' +
                " reaches *" + destination + '*' +
                " on *" + arrDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy")) + '*' +
                " at *" + arrTime + '*' ;
    }
}
