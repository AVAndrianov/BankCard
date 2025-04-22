package ru.minusd.security.exception;

public class Response {

    private String message;
    private String debugMessage;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }
}
