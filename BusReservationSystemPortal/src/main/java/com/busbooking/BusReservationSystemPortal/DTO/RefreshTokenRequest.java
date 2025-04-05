package com.busbooking.BusReservationSystemPortal.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RefreshTokenRequest {
    private String refreshToken;
}
