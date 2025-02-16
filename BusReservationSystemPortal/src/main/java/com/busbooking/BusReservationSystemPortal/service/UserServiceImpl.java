package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.CurrentUserSession;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserSessionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   public final UserDao userDao;
   public final UserSessionDao userSessionDao;

    @Override
    public User createUser(User user) throws UserException {
        User existingUser = userDao.findByMobileNumber(user.getMobileNumber());
        if (Objects.nonNull(existingUser)) throw new UserException("User with this mobile number already exists!");
        else
            return userDao.save(user);
    }

    @Override
    public User updateUser(User user, String key) throws UserException {

        CurrentUserSession loginUser = userSessionDao.findByUuid(key);
        if (Objects.isNull(loginUser)) throw new UserException("Please provide a valid key to update a User Details!");

        if (Objects.equals(user.getUserId(), loginUser.getUserId())) {
            return userDao.save(user);
        } else
            throw new UserException("Invalid User Details! please login first.");
    }
}
