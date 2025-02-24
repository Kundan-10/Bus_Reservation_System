package com.busbooking.BusReservationSystemPortal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reservationId;
    private String reservationStatus;

    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private String source;
    private String destination;
    private LocalDate journeyDate;
    private Integer noOfSeatsBooked;
    private Integer fare;

    @ManyToOne
    private Bus bus;

    @ManyToOne
    private User user;

    public Reservation(Bus bus, String reservationStatus, LocalDate reservationDate,
                       LocalTime reservationTime, String source, String destination,
                       int noOfSeatsBooked, Integer fare, LocalDate journeyDate, User user) {
        this.bus = bus;
        this.reservationStatus = reservationStatus;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.source = source;
        this.destination = destination;
        this.noOfSeatsBooked = noOfSeatsBooked;
        this.fare = fare;
        this.journeyDate = journeyDate;
        this.user = user;
    }
}
