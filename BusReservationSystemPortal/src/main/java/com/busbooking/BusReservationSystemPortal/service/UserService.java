package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.User;

import java.util.List;

public interface UserService {

    public User createUser(User user)throws UserException;

    public  User updateUser(User user, String key)throws UserException;

    public User deleteUser(Integer userId,String key) throws UserException, AdminException;

    public User viewUserById(Integer userId, String key) throws UserException, AdminException;

    public List<User> viewUsers(String key) throws UserException, AdminException;

}
