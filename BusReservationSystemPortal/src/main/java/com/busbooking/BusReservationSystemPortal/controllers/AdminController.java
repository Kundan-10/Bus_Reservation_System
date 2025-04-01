//package com.busbooking.BusReservationSystemPortal.controllers;
//
//import com.busbooking.BusReservationSystemPortal.exception.AdminException;
//import com.busbooking.BusReservationSystemPortal.models.Admin;
//import com.busbooking.BusReservationSystemPortal.models.User;
//import com.busbooking.BusReservationSystemPortal.service.AdminService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@Tag(name = "Admin Management", description = "Endpoints for Admin createAdmin and updateAdmin")
//@RequestMapping("/auth/admin")
//public class AdminController {
//
//    private final AdminService adminService;
//
//    @PostMapping("/createAdmin")
//    @Operation(summary = "Add Admin", description = "Save a new Admin in the system")
//    ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) throws AdminException {
//        Admin createadmin = adminService.createAdmin(admin);
//        return new ResponseEntity<Admin>(createadmin, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/updateAdmin")
//    @Operation(summary = "Update Admin", description = "Update an existing Admin details")
//    ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin, @RequestParam String key) throws AdminException {
//        Admin admin1 = adminService.updateAdmin(admin);
//        return new ResponseEntity<Admin>(admin1, HttpStatus.OK);
//    }
//
//}
