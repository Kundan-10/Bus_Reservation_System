package com.busbooking.BusReservationSystemPortal.repositoty;

import com.busbooking.BusReservationSystemPortal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    public User findByMobileNumber(String mobileNumber);
}
