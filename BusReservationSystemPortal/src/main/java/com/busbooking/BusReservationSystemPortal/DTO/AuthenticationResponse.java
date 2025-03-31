package com.busbooking.BusReservationSystemPortal.DTO;

import com.busbooking.BusReservationSystemPortal.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private User user;
}
