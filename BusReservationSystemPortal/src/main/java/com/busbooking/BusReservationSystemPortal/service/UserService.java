package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.User;

public interface UserService {

    public User createUser(User user)throws UserException;

    public  User updateUser(User user, String key)throws UserException;

}
