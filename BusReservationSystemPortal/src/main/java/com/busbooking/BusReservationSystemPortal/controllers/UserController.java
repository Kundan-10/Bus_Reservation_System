package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.DTO.UserDTO;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @Operation(summary = "Add User", description = "Save a new user in the system")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserDTO user) throws UserException {
        User saveuser = userService.createUser(user);
        return new ResponseEntity<User>(saveuser, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser")
    @Operation(summary = "Update User", description = "Update an existing user's details")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserDTO user) throws UserException {
        User udpateuser = userService.updateUser(user);
        return new ResponseEntity<>(udpateuser, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{userId}")
    @Operation(summary = "Delete User", description = "Delete a user by ID (Admin access required)")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId) throws UserException, AdminException {
        User deleteuser = userService.deleteUser(userId);
        return new ResponseEntity<User>(deleteuser, HttpStatus.OK);
    }

    @GetMapping("/admin/{userId}")
    @Operation(summary = "View User by ID", description = "View a user's details by ID (Admin access required)")
    public ResponseEntity<User> viewUser(@PathVariable("userId") Integer userId) throws AdminException, UserException {
        User viewuser = userService.viewUserById(userId);
        return new ResponseEntity<User>(viewuser, HttpStatus.OK);
    }

    @GetMapping("/admin")
    @Operation(summary = "View All Users", description = "View a list of all users (Admin access required)")
    public ResponseEntity<List<User>> viewUsers() throws AdminException, UserException {
        List<User> users = userService.viewUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

}
