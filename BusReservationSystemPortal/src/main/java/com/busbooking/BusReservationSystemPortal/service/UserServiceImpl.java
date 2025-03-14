package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.CurrentUserSession;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserSessionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

       CurrentUserSession loginUser= validateUserSession(key);
        if (Objects.equals(user.getUserId(), loginUser.getUserId())) {
            return userDao.save(user);
        } else
            throw new UserException("Invalid User Details! please login first.");
    }

    @Override
    public User deleteUser(Integer userId, String key) throws UserException {
       validateUserSession(key);
        User user = userDao.findById(userId)
                .orElseThrow(() -> new UserException("Invalid user Id!"));
        userDao.delete(user);
        return user;
    }

    @Override
    public User viewUserById(Integer userId, String key) throws UserException {
        validateUserSession(key);
        return userDao.findById(userId)
                .orElseThrow(() -> new UserException("Invalid user Id!"));
    }

    @Override
    public List<User> viewUsers(String key) throws UserException {
        validateUserSession(key);
        return Optional.ofNullable(userDao.findAll())
                .filter(list -> !list.isEmpty())
                .orElse(Collections.emptyList());
    }

    private CurrentUserSession validateUserSession(String key) throws UserException {
        return Optional.ofNullable(userSessionDao.findByUuid(key))
                .orElseThrow(() -> new UserException("Invalid session key! Please provide a valid key!"));
      }
}
