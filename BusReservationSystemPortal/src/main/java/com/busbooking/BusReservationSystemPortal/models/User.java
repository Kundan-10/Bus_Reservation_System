package com.busbooking.BusReservationSystemPortal.models;

import com.busbooking.BusReservationSystemPortal.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull(message = "Name cannot be null!")
    @NotBlank(message = "Name connote be blank!")
    private String firstName;

    private String lastName;

    @NotNull(message="Password cannot be null!")
    @NotBlank(message= "Password cannot be blank!")
    @JsonIgnore
//    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include alphanumerics and special characters")
    private String password;


    @NotNull(message="Mobile number cannot be null!")
    @NotBlank(message= "Mobile number cannot be blank!")
    @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
    @Size(min = 10, max = 10)
    private String mobileNumber;

    @Email
    @Column(unique = true)
    private String email;

    private boolean enable = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+ role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Reservation> reservations =new ArrayList<>();
}
