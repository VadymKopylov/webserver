package com.kopylov.webserver.server.entity;

public enum StatusCode {
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method not allowed");

    private final int code;
    private final String status;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    StatusCode(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
