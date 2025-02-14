package com.busbooking.BusReservationSystemPortal.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Error {

    private LocalDateTime timestamp;
    private String message;
    private String description;

}
