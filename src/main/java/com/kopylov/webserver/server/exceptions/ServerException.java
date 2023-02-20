package com.kopylov.webserver.server.exceptions;

import com.kopylov.webserver.server.entity.StatusCode;

public class ServerException extends RuntimeException {
    private StatusCode statusCode;

    public ServerException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
