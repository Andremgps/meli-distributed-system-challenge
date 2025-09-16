package com.meli.store.exception;

import com.meli.store.exception.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleInventoryNotFound(StoreNotFoundException ex) {
        logger.error("Inventory not found: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO("INVENTORY_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponseDTO error = new ErrorResponseDTO("VALIDATION_ERROR", "Invalid request parameters",
                HttpStatus.BAD_REQUEST.value());
        error.setValidationErrors(errors);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        logger.error("Unexpected error: ", ex);
        ErrorResponseDTO error = new ErrorResponseDTO("INTERNAL_ERROR", "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
