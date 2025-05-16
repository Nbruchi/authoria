package com.bruce.auth.exceptions;

import com.bruce.auth.constants.ErrorMessages;
import com.bruce.auth.dtos.ErrorResponse;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public GlobalExceptionHandler(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    // Utility method to create error response
    private ResponseEntity<ErrorResponse> createErrorResponse(
            String errorCode,
            Object[] args,
            HttpStatus status,
            HttpServletRequest request
    ) {
        Locale locale = localeResolver.resolveLocale(request);
        String message = messageSource.getMessage(errorCode, args, locale);

        ErrorResponse error = new ErrorResponse(
                status.getReasonPhrase(),
                message,
                status.value()
        );
        return new ResponseEntity<>(error, status);
    }

    // Unauthorized Access Exception
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(
            UnauthorizedAccessException ex,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                ex.getErrorCode(),
                ex.getArgs(),
                HttpStatus.FORBIDDEN,
                request
        );
    }

    // Resource Not Found Exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {
        return createErrorResponse(
                ErrorMessages.Client.NOT_FOUND,
                null,
                HttpStatus.NOT_FOUND,
                request
        );
    }

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        return createErrorResponse(
                ErrorMessages.General.VALIDATION_ERROR,
                new Object[]{errors},
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    // Catch-all for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception ex,
            HttpServletRequest request
    ) {
        // Log the full error for server-side tracking
        logger.error("Unexpected error", ex);

        return createErrorResponse(
                ErrorMessages.General.INTERNAL_SERVER_ERROR,
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorResponse> handleMessagingException(
            MessagingException ex,
            HttpServletRequest request
    ){
        return createErrorResponse(
          ErrorMessages.Email.SENDING_FAILED,
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(
            EmailNotFoundException ex,
            HttpServletRequest request){
        return createErrorResponse(
                ErrorMessages.Email.EMAIL_NOT_FOUND,
                null,
                HttpStatus.NOT_FOUND,
                request
        );
    }
}