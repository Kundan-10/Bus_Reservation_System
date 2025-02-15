package com.busbooking.BusReservationSystemPortal.repositoty;

import com.busbooking.BusReservationSystemPortal.models.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionDao extends JpaRepository<CurrentUserSession , Integer> {

    public CurrentUserSession findByUuid(String uuid);

}
