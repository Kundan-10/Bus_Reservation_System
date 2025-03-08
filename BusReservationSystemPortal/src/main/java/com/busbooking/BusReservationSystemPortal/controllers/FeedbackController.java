package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.FeedbackException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Feedback;
import com.busbooking.BusReservationSystemPortal.service.FeedbackService;
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
@RequestMapping("/feedback")
@Tag(name = "Feedback Controller", description = "Handles Feedback operations")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(summary = "Add Feedback", description = "Adds feedback for a specific bus")
    @PostMapping("/user/{busid}")
    public ResponseEntity<Feedback> addFeedback(@Valid @RequestBody Feedback feedback,
                                                @PathVariable("busid") Integer busId,
                                                @RequestParam(required = false) String key) throws UserException, BusException {

        Feedback feedback2 = feedbackService.addFeedBack(feedback, busId, key);
        return new ResponseEntity<>(feedback2, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Update Feedback", description = "Updates an existing feedback entry")
    @PutMapping("/user")
    public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback,
                                                   @RequestParam(required = false) String key)
            throws FeedbackException, UserException {
        Feedback feedback2 = feedbackService.updateFeedBack(feedback, key);
        return new ResponseEntity<>(feedback2, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete Feedback", description = "Deletes a feedback entry")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Feedback> deleteFeedback(@PathVariable("id") Integer feedbackId,
                                                   @RequestParam(required = false) String key)
            throws FeedbackException, UserException {
        Feedback feedback2 = feedbackService.deleteFeedBack(feedbackId, key);
        return new ResponseEntity<>(feedback2, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "View Feedback", description = "Retrieves a specific feedback by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> viewFeedback(@PathVariable("id") Integer id) throws FeedbackException {
        Feedback feedback2 = feedbackService.viewFeedback(id);
        return new ResponseEntity<>(feedback2, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "View All Feedback", description = "Retrieves all feedback")
    @GetMapping
    public ResponseEntity<List<Feedback>> viewFeedbackAll() throws FeedbackException {
        List<Feedback> feedback2 = feedbackService.viewFeedbackAll();
        return new ResponseEntity<>(feedback2, HttpStatus.ACCEPTED);
    }
}
