package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.FeedbackException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Bus;
import com.busbooking.BusReservationSystemPortal.models.CurrentUserSession;
import com.busbooking.BusReservationSystemPortal.models.Feedback;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.BusDao;
import com.busbooking.BusReservationSystemPortal.repositoty.FeedbackDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserSessionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{
    
    private final UserSessionDao userSessionDao;
    private final FeedbackDao feedbackDao;
    private final UserDao userDao;
    private final BusDao busDao;
    
    @Override
    public Feedback addFeedBack(Feedback feedBack, Integer busId, String key) throws BusException, UserException {
        CurrentUserSession loggedInUser = validateUserSession(key);
        User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found!"));

        Bus bus = busDao.findById(busId).orElseThrow(() -> new BusException("Bus is not present with Id: "+ busId));

        feedBack.setBus(bus);
        feedBack.setUser(user);
        feedBack.setFeedbackDateTime(LocalDateTime.now());
        return feedbackDao.save(feedBack);
    }

    @Override
    public Feedback updateFeedBack(Feedback feedback, String key) throws FeedbackException, UserException {
        CurrentUserSession loggedInUser = validateUserSession(key);
        User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found!"));
        Feedback opt = feedbackDao.findById(feedback.getFeedBackId()).orElseThrow(() -> new FeedbackException("No feedback found!"));
        Bus bus = busDao.findById(opt.getBus().getBusId()).orElseThrow(() -> new FeedbackException("Invalid bus details!"));

        feedback.setBus(bus);
        feedback.setUser(user);
        feedback.setFeedbackDateTime(LocalDateTime.now());

        return feedbackDao.save(feedback);
    }

    @Override
    public Feedback deleteFeedBack(Integer feedbackId, String key) throws FeedbackException, UserException {
        CurrentUserSession loggedInUser = validateUserSession(key);
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

    private CurrentUserSession validateUserSession(String key) throws UserException {
        return Optional.ofNullable(userSessionDao.findByUuid(key))
                .orElseThrow(() -> new UserException("Invalid session key! Please provide a valid key!"));
    }
}
