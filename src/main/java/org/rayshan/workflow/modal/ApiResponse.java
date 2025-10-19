package org.rayshan.workflow.modal;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int statusCode;
    private String statusText;
    private T data;

//    public ApiResponse() {
//    }

    public ApiResponse(int statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }
}