package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.*;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import com.busbooking.BusReservationSystemPortal.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDao userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User register(UserDTO request) {
        log.info("Registering new user with email: {}", request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(encodedPassword);
        user.setRole(request.getRole());
        userRepository.save(user);
        log.info("User registered successfully with ID: {}", user.getUserId());
        return user;

//        return AuthenticationResponse.builder()
//                .accessToken(jwtService.generateToken(request.getEmail(), true))
//                .refreshToken(jwtService.generateToken(request.getEmail(), false))
//                .user(user)
//                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the credentials
        log.info("Authenticating user with email: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", request.getEmail());
                    return new UsernameNotFoundException("User not found");
                });

        log.info("Authentication successful for email: {}", request.getEmail());
        return AuthenticationResponse.builder()
                .accessToken(jwtService.generateToken(request.getEmail(), true))
                .refreshToken(jwtService.generateToken(request.getEmail(), false))
                .user(user)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {

        if (jwtService.validateToken(request.getRefreshToken())) {
            String userNameFromToken = jwtService.getUserNameFromToken(request.getRefreshToken());
            String accessToken = jwtService.generateToken(userNameFromToken, true);
            String refreshToken = jwtService.generateToken(userNameFromToken, false);
            User user = userRepository.findByEmail(userNameFromToken).get();

            return AuthenticationResponse.builder()
                    .accessToken(jwtService.generateToken(accessToken, true))
                    .refreshToken(jwtService.generateToken(refreshToken, false))
                    .user(user)
                    .build();

        }
        return AuthenticationResponse.builder()
                .accessToken("refresh token not found")
                .build();
    }

}
