package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.UserDTO;
import com.busbooking.BusReservationSystemPortal.Enum.Role;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   public final UserDao userDao;
   public final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO request) throws UserException {

        if (userDao.findByEmail(request.getEmail()).isPresent()) {
            throw new UserException("User with this email already exists!");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(encodedPassword);
        user.setRole(request.getRole());
        return userDao.save(user);

    }

    @Override
    public User updateUser(UserDTO userDTO) throws UserException {
        User authenticatedUser = getAuthenticatedUser();

        if (!Objects.equals(userDTO.getUserId(), authenticatedUser.getUserId())) {
            throw new UserException("Invalid User Details! Please log in first.");
        }
        if (userDTO.getFirstName() != null) authenticatedUser.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null) authenticatedUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) authenticatedUser.setEmail(userDTO.getEmail());
        if (userDTO.getMobileNumber() != null) authenticatedUser.setMobileNumber(userDTO.getMobileNumber());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            authenticatedUser.setPassword(encodedPassword);
        }
        authenticatedUser.setRole(Role.USER);
        return userDao.save(authenticatedUser);
    }

    @Override
    public User deleteUser(Integer userId) throws UserException {
        User authenticatedUser = getAuthenticatedUser();
        if(!authenticatedUser.getRole().equals(Role.ADMIN)){
            throw new UserException("Unauthorized! Only admin can delete users.");
        }
        User user =  userDao.findById(userId)
                .orElseThrow(() -> new UserException("Invalid user Id!"));
        userDao.delete(user);
        return user;
    }

    @Override
    public User viewUserById(Integer userId) throws UserException {
        User authenticatedUser = getAuthenticatedUser();
        if(!authenticatedUser.getRole().equals(Role.ADMIN)){
            throw new UserException("Unauthorized! Only admin can view user.");
        }
        return userDao.findById(userId)
                .orElseThrow(() -> new UserException("Invalid user Id!"));
    }

    @Override
    public List<User> viewUsers() throws UserException {
        User authenticatedUser = getAuthenticatedUser();
        if(!authenticatedUser.getRole().equals(Role.ADMIN)){
            throw new UserException("Unauthorized! Only admin can view users.");
        }
        return Optional.ofNullable(userDao.findAll())
                .filter(list -> !list.isEmpty())
                .orElse(Collections.emptyList());
    }

    private User getAuthenticatedUser() throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Unauthorized request.");
        }

        String email = authentication.getName(); // Get logged-in user's email
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found!"));
    }

}
