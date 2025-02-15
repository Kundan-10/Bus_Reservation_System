package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.AdminLoginDTO;
import com.busbooking.BusReservationSystemPortal.exception.LoginException;
import com.busbooking.BusReservationSystemPortal.models.Admin;
import com.busbooking.BusReservationSystemPortal.models.CurrentAdminSession;
import com.busbooking.BusReservationSystemPortal.repositoty.AdminDao;
import com.busbooking.BusReservationSystemPortal.repositoty.AdminSessionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import net.bytebuddy.utility.RandomString;

@Service
@RequiredArgsConstructor
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminDao adminDao;
    private final AdminSessionDao adminSessionDao;

    @Override
    public CurrentAdminSession logIntoAdminAccount(AdminLoginDTO dto) throws LoginException {
        Admin existingAdmin = adminDao.findByMobileNumber(dto.getMobileNumber());

        if(Objects.isNull(existingAdmin)) throw new LoginException("Please Enter a valid mobile number!");

        Optional<CurrentAdminSession> validAdminSessionOpt =  adminSessionDao.findById(existingAdmin.getAdminId());
        if (validAdminSessionOpt.isPresent()) throw new LoginException("Admin already Logged-In with this number");

        if (existingAdmin.getAdminPassword().equals(dto.getAdminPassword())) {
            String key = RandomString.make(6);
            CurrentAdminSession currentAdminSession = new CurrentAdminSession(existingAdmin.getAdminId(), key, LocalDateTime.now());
            adminSessionDao.save(currentAdminSession);
            return currentAdminSession;
        }else
            throw new LoginException("Please Enter a valid password!");

    }

    @Override
    public String logOutFromAdminAccount(String key) throws LoginException {
        CurrentAdminSession currentAdminSession = adminSessionDao.findByUuid(key);

        if(Objects.isNull(currentAdminSession)) throw new LoginException("Admin Not Logged In with this number");
        adminSessionDao.delete(currentAdminSession);
        return "Admin Logged Out !";
    }

}
