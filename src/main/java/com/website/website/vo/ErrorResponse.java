package com.website.website.vo;

/**
 * Created by dineshk on 9/25/19.
 */
public class ErrorResponse {

    public int code;
    public String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String message) {
        this.error = message;
    }
}
