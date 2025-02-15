package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.UserLoginDTO;
import com.busbooking.BusReservationSystemPortal.exception.LoginException;
import com.busbooking.BusReservationSystemPortal.models.CurrentUserSession;

public interface UserLoginService {

    public CurrentUserSession logIntoUserAccount(UserLoginDTO dto)throws LoginException;

    public String logOutFromUserAccount(String key)throws LoginException;
}
