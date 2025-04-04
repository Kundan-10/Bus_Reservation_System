package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.FeedbackException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Feedback;

import java.util.List;

public interface FeedbackService {

    public Feedback addFeedBack(Feedback feedBack, Integer busId) throws BusException, UserException;

    public Feedback updateFeedBack(Feedback feedback) throws FeedbackException, UserException;

    public Feedback deleteFeedBack(Integer feedbackId)throws FeedbackException, UserException;

    public Feedback viewFeedback(Integer feedbackId) throws FeedbackException;

    public List<Feedback> viewFeedbackAll() throws FeedbackException;
}
