package com.capgemini.CineBuzz.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String DETAILS = "details";
    private static final String ERRORS = "errors";

    private Map<String, Object> createErrorDetails(String message, int statusCode) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put(TIMESTAMP, LocalDateTime.now());
        errorDetails.put(MESSAGE, message);
        errorDetails.put(STATUS, statusCode);
        return errorDetails;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<Object> handleMessageNotFound(MessageNotFoundException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<Object> handleAdminNotFound(AdminNotFoundException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Object> handleBookingNotFound(BookingNotFoundException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShowtimeNotFoundException.class)
    public ResponseEntity<Object> handleShowtimeNotFound(ShowtimeNotFoundException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Object> handleMovieNotFound(MovieNotFoundException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<Object> handleMethodNotAllowed(MethodNotAllowedException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED.value()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<Object> handleMissingParameter(MissingParameterException ex) {
        return new ResponseEntity<>(createErrorDetails(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, 
            HttpStatusCode status, WebRequest request) {

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put(TIMESTAMP, LocalDateTime.now());
        errorDetails.put(STATUS, HttpStatus.BAD_REQUEST.value());

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        errorDetails.put(ERRORS, fieldErrors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put(TIMESTAMP, LocalDateTime.now());
        errorDetails.put(MESSAGE, "Unexpected error occurred");
        errorDetails.put(DETAILS, ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}