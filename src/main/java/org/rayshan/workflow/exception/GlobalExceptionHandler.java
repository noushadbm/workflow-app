package org.rayshan.workflow.exception;

import org.rayshan.workflow.modal.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException(AppException ex) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(ex.getErrorCode());
        response.setStatusText("Failed");
        response.setData("An unexpected error occurred: " + ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        //logger.error("Exception caught: ", ex);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setStatusText("Failed");
        response.setData("An unexpected error occurred: " + ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
