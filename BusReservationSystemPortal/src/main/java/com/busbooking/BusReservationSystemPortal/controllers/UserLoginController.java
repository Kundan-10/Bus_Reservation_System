package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.DTO.UserLoginDTO;
import com.busbooking.BusReservationSystemPortal.exception.LoginException;
import com.busbooking.BusReservationSystemPortal.models.CurrentUserSession;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Authentication", description = "Endpoints for User Login and Logout")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/user/login")
    @Operation(summary = "User Login", description = "Authenticates an User and returns a session")
    ResponseEntity<CurrentUserSession> userLogin(@RequestBody UserLoginDTO userLoginDTO) throws LoginException {
        CurrentUserSession currentUserSession = userLoginService.logIntoUserAccount(userLoginDTO);
        return  new ResponseEntity<CurrentUserSession>(currentUserSession, HttpStatus.ACCEPTED);
    }

    @PostMapping("/user/logout")
    @Operation(summary = "User Logout", description = "Logs out an user using a session key")
    public String userLogOut (@RequestParam String key) throws LoginException {
         return userLoginService.logOutFromUserAccount(key);
    }

}
