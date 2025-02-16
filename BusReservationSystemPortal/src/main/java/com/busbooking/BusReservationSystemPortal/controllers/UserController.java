package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Management", description = "Endpoints for managing users") // API Group
public class UserController {
    private final UserService userService;

    @PostMapping("addUser")
    public ResponseEntity<User>saveUser(@Valid @RequestBody User user) throws UserException {
        User result = userService.createUser(user);
        return new ResponseEntity<User>(result, HttpStatus.CREATED);
    }



}
