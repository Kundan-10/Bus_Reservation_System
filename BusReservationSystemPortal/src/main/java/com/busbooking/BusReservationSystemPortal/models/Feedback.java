package com.busbooking.BusReservationSystemPortal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedBackId;

    private Integer driverRating;

    @Min(value=1, message="Rating must be in range of 1-5")
    @Max(value=5, message="Rating must be in range of 1-5")
    private Integer serviceRating;

    private Integer overallRating;

    private String comments;

    private LocalDateTime feedbackDateTime;

    @OneToOne
    private Bus bus;
}
