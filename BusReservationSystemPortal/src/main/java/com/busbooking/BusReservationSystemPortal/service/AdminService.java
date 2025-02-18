package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.LoginException;
import com.busbooking.BusReservationSystemPortal.models.Admin;

public interface AdminService {

    public Admin createAdmin(Admin admin) throws AdminException;

    public Admin updateAdmin(Admin admin, String key) throws AdminException;
}
