package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.UserLoginDTO;
import com.busbooking.BusReservationSystemPortal.exception.LoginException;
import com.busbooking.BusReservationSystemPortal.models.CurrentUserSession;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserSessionDao;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService{

    private final UserSessionDao userSessionDao;
    private final UserDao userDao;

    @Override
    public CurrentUserSession logIntoUserAccount(UserLoginDTO dto) throws LoginException {

        User existingUser = userDao.findByMobileNumber(dto.getMobileNumber());
        if (Objects.isNull(existingUser)) throw new LoginException("Please Enter a valid mobile number");

        Optional<CurrentUserSession> validUserSessionOpt = userSessionDao.findById(existingUser.getUserId());

        if (validUserSessionOpt.isPresent()) throw new LoginException("User already Logged In with this number");

        if (existingUser.getPassword().equals(dto.getPassword())) {
            String key = RandomString.make(6);
            CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getUserId(), key, LocalDateTime.now());
            userSessionDao.save(currentUserSession);
            return currentUserSession;
        } else
            throw new LoginException("Please Enter a valid password!");

    }

    @Override
    public String logOutFromUserAccount(String key) throws LoginException {
        CurrentUserSession currentUserSession = userSessionDao.findByUuid(key);

        if (Objects.isNull(currentUserSession)) throw new LoginException("User Not Logged In with this number");

        userSessionDao.delete(currentUserSession);
        return "User Logged Out !";
    }
}
