package com.busbooking.BusReservationSystemPortal.DTO;

import com.busbooking.BusReservationSystemPortal.Enum.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobileNumber;
    private Role role;
}
