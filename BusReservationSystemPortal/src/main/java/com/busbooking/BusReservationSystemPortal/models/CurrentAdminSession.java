package com.busbooking.BusReservationSystemPortal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentAdminSession {
    @Id
    @Column(unique = true)
    private Integer adminId;

    private String uuid;

    private LocalDateTime dateTime;
}
