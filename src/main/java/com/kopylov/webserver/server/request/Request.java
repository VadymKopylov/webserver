package com.kopylov.webserver.server.request;

import com.kopylov.webserver.server.entity.HttpMethod;

import java.util.Map;

public class Request {
    String uri;
    Map<String,String> headers;
    HttpMethod method;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}

