package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.DTO.AdminLoginDTO;
import com.busbooking.BusReservationSystemPortal.exception.LoginException;
import com.busbooking.BusReservationSystemPortal.models.CurrentAdminSession;
import com.busbooking.BusReservationSystemPortal.service.AdminLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Admin Authentication", description = "Endpoints for Admin Login and Logout")
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    @PostMapping("/admin/login")
    @Operation(summary = "Admin Login", description = "Authenticates an admin and returns a session")
    public ResponseEntity<CurrentAdminSession> logInAdmin(@Valid @RequestBody AdminLoginDTO adminLoginDTO) throws LoginException {
        CurrentAdminSession currentAdminSession = adminLoginService.logIntoAdminAccount(adminLoginDTO);
        return new ResponseEntity<CurrentAdminSession>(currentAdminSession, HttpStatus.ACCEPTED);
    }

    @PostMapping("/admin/logout")
    @Operation(summary = "Admin Logout", description = "Logs out an admin using a session key")
    public String  logOutAdmin(@RequestParam String key)throws LoginException{
        return adminLoginService.logOutFromAdminAccount(key);
    }

}
