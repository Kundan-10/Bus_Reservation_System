package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.AdminLoginDTO;
import com.busbooking.BusReservationSystemPortal.exception.LoginException;
import com.busbooking.BusReservationSystemPortal.models.CurrentAdminSession;

public interface AdminLoginService {

    public CurrentAdminSession logIntoAdminAccount(AdminLoginDTO dto)throws LoginException;

    public String logOutFromAdminAccount(String key)throws LoginException;

}
