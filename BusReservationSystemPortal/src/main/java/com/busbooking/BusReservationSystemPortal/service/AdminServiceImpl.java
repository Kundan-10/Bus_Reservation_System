package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.models.Admin;
import com.busbooking.BusReservationSystemPortal.models.CurrentAdminSession;
import com.busbooking.BusReservationSystemPortal.repositoty.AdminDao;
import com.busbooking.BusReservationSystemPortal.repositoty.AdminSessionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminDao adminDao;
    private final AdminSessionDao adminSessionDao;

    @Override
    public Admin createAdmin(Admin admin) throws AdminException {
        Admin existingAdmin = adminDao.findByMobileNumber(admin.getMobileNumber());
        if (Objects.nonNull(existingAdmin))
            throw new AdminException("Admin already registered with this Mobile number!");
        return adminDao.save(admin);
    }

    @Override
    public Admin updateAdmin(Admin admin, String key) throws AdminException {
        CurrentAdminSession loggedInAdmin = adminSessionDao.findByUuid(key);
        if (Objects.isNull(loggedInAdmin))
            throw new AdminException("Please provide a valid key to update Admin Details!");

        if (Objects.equals(admin.getAdminId(), loggedInAdmin.getAdminId())) {
            return adminDao.save(admin);
        } else
            throw new AdminException("Invalid Admin Details! please login first.");
    }
}
