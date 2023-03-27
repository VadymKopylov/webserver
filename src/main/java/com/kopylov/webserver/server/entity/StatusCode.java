package com.kopylov.webserver.server.entity;

public enum StatusCode {
    NOT_FOUND(404, "Not found"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    OK (200,"OK");

    private final int code;
    private final String status;

    StatusCode(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }


}
