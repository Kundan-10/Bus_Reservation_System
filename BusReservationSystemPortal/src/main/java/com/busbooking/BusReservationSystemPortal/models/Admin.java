package com.busbooking.BusReservationSystemPortal.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer adminId;

    @NotNull(message = "Name cannot be null!")
    @NotBlank(message = "Name connot be blank!")
    private String firstName;

    private String lastName;

    @NotNull(message="Password cannot be null!")
    @NotBlank(message= "Password cannot be blank!")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include alphanumerics and special characters")
    private String adminPassword;

    @NotNull(message="Mobile number cannot be null!")
    @NotBlank(message= "Mobile number cannot be blank!")
    @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
    @Size(min = 10, max = 10)
    private String mobileNumber;

    @Email
    private String email;

}
