package com.busbooking.BusReservationSystemPortal.repositoty;

import com.busbooking.BusReservationSystemPortal.models.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionDao extends JpaRepository<CurrentUserSession , Integer> {

    public CurrentUserSession findByUuid(String uuid);

}
