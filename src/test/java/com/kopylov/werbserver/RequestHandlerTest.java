package com.kopylov.werbserver;

import com.kopylov.webserver.server.entity.HttpMethod;
import com.kopylov.webserver.server.entity.Request;

import com.kopylov.webserver.server.exceptions.ServerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kopylov.webserver.server.request.RequestParser.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestHandlerTest {
    private Request request = new Request();
    private final String firstLine = "GET /index.html HTTP/1.1";
    private List<String> lines = new ArrayList<>();

    @Test
    public void testInjectHeadersSkipFirstLineReturnCorrectHeaders() {
        Request expectedRequest = new Request();
        lines.add("GET /wiki/страница HTTP/1.1");
        lines.add("Host: ru.wikipedia.org");
        lines.add("Accept: text/html");
        lines.add("Connection: close");
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "ru.wikipedia.org");
        expectedHeaders.put("Accept", "text/html");
        expectedHeaders.put("Connection", "close");
        expectedRequest.setHeaders(expectedHeaders);
        injectHeaders(lines, request);

        assertEquals(expectedRequest.getHeaders(), request.getHeaders());
    }

    @Test
    public void testInjectUriAndMethodReturnCorrectUri() {
        lines.add(firstLine);
        request.setUri("/index.html");
        Request actualRequest = new Request();
        injectUriAndMethod(lines, actualRequest);

        assertEquals(request.getUri(), actualRequest.getUri());
    }

    @Test
    public void testInjectUriAndMethodReturnCorrectMethod() {
        lines.add(firstLine);
        request.setMethod(HttpMethod.valueOf("GET"));
        Request actualRequest = new Request();
        injectUriAndMethod(lines, actualRequest);

        assertEquals(request.getMethod(), actualRequest.getMethod());
    }

    @Test
    public void testInjectUriAndMethodThrowsServerExceptionWhenHttpMethodNotAllowed() {
        ServerException serverException = Assertions.assertThrows(ServerException.class, () -> {
            lines.add("POST /index.html HTTP/1.1");
            request.setMethod(HttpMethod.valueOf("POST"));
            Request request = new Request();
            injectUriAndMethod(lines, request);
        });
        String actualException = serverException.getStatusCode().getCode() + " " + serverException.getStatusCode().getStatus();
        String expectedException = "405 Method not allowed";
        assertEquals(actualException, expectedException);
    }
}
