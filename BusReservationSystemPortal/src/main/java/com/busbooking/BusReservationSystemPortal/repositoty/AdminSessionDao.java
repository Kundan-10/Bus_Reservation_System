package com.busbooking.BusReservationSystemPortal.repositoty;

import com.busbooking.BusReservationSystemPortal.models.CurrentAdminSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminSessionDao extends JpaRepository<CurrentAdminSession, Integer> {

    public CurrentAdminSession findByUuid(String uuid);

}
