package com.busbooking.BusReservationSystemPortal.repositoty;

import com.busbooking.BusReservationSystemPortal.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, Integer> {

    public Admin findByMobileNumber(String mobileNumber);

}
