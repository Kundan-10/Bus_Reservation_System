package com.busbooking.BusReservationSystemPortal.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer busId;

    @NotNull(message = "Bus name cannot be null!")
    private String busName;

    @NotNull(message = "Bus driver name cannot be null!")
    private String driverName;

    private String busType;

    @NotNull(message = "Bus registration number cannot be null!")
    private String busRegNumber;

    @NotNull(message = "Start point cannot be null!")
    @NotBlank(message = "Start point cannot be blank!")
    private String routeFrom;

    @NotNull(message = "Destination point cannot be null!")
    @NotBlank(message = "Destination point cannot be blank!")
    private String routeTo;

    @NotNull(message = "Arrival time cannot be null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime arrivalTime;

    @NotNull(message = "Departure time cannot be null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime departureTime;

    @NotNull(message = "Total Seats cannot be null!")
    private Integer seats;

    @NotNull(message = "Available seats cannot be null!")
    private Integer availableSeats;

    @NotNull(message = "Fare cannot be null!")
    private Integer farePerSeat;

    @NotNull(message = "Bus journey date cannot be null!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate busJourneyDate;

    @ManyToOne
    private Route route;

}
