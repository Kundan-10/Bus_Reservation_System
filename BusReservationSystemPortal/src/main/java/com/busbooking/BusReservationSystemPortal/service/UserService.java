package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.UserDTO;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    public User createUser(@Valid UserDTO user)throws UserException;

    public  User updateUser(@Valid UserDTO user)throws UserException;

    public User deleteUser(Integer userId) throws UserException, AdminException;

    public User viewUserById(Integer userId) throws UserException, AdminException;

    public List<User> viewUsers() throws UserException, AdminException;

}
