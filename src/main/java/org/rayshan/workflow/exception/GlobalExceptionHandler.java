package org.rayshan.workflow.exception;

import lombok.extern.log4j.Log4j2;
import org.rayshan.workflow.modal.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException(AppException ex) {
        log.error("AppException caught: ", ex);
        ApiResponse<String> response = new ApiResponse<>(ex.getErrorCode(), "Failed");
        response.setData("An unexpected error occurred: " + ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        log.error("Exception caught: ", ex);
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed");
        response.setData("An unexpected error occurred: " + ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
