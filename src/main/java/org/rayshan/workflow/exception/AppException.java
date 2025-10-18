package org.rayshan.workflow.exception;

public class AppException extends Exception {
    private int errorCode;
    public AppException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
