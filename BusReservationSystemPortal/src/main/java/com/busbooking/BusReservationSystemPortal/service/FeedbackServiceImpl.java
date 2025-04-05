package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.FeedbackException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Bus;
import com.busbooking.BusReservationSystemPortal.models.Feedback;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.BusDao;
import com.busbooking.BusReservationSystemPortal.repositoty.FeedbackDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserSessionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{
    
    private final UserSessionDao userSessionDao;
    private final FeedbackDao feedbackDao;
    private final UserDao userDao;
    private final BusDao busDao;
    
    @Override
    public Feedback addFeedBack(Feedback feedBack, Integer busId) throws BusException, UserException {
        User loggedInUser = getAuthenticatedUser();
        User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found!"));

        Bus bus = busDao.findById(busId).orElseThrow(() -> new BusException("Bus is not present with Id: "+ busId));

        feedBack.setBus(bus);
        feedBack.setUser(user);
        feedBack.setFeedbackDateTime(LocalDateTime.now());
        return feedbackDao.save(feedBack);
    }

    @Override
    public Feedback updateFeedBack(Feedback feedback) throws FeedbackException, UserException {
        User loggedInUser = getAuthenticatedUser();
        User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found!"));
        Feedback opt = feedbackDao.findById(feedback.getFeedbackId()).orElseThrow(() -> new FeedbackException("No feedback found!"));
        Bus bus = busDao.findById(opt.getBus().getBusId()).orElseThrow(() -> new FeedbackException("Invalid bus details!"));

        feedback.setBus(bus);
        feedback.setUser(user);
        feedback.setFeedbackDateTime(LocalDateTime.now());

        return feedbackDao.save(feedback);
    }

    @Override
    public Feedback deleteFeedBack(Integer feedbackId) throws FeedbackException, UserException {
        User loggedInUser = getAuthenticatedUser();
        userDao.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found!"));
        Feedback feedback = feedbackDao.findById(feedbackId).orElseThrow(() -> new FeedbackException("No feedback found!"));
        feedbackDao.delete(feedback);
        return feedback;
    }

    @Override
    public Feedback viewFeedback(Integer feedbackId) throws FeedbackException {
        return feedbackDao.findById(feedbackId).orElseThrow(() -> new FeedbackException("No feedback found!"));
    }

    @Override
    public List<Feedback> viewFeedbackAll() throws FeedbackException {
        List<Feedback> feedbacks = feedbackDao.findAll();
        if(feedbacks.isEmpty()){
            throw  new FeedbackException("No feedbacks found");
        }
        return  feedbacks;
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
