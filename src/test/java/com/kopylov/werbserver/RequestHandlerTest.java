package com.kopylov.werbserver;

import com.kopylov.webserver.server.entity.HttpMethod;
import com.kopylov.webserver.server.request.Request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;

import static com.kopylov.webserver.server.request.RequestParser.injectHeaders;
import static com.kopylov.webserver.server.request.RequestParser.injectUriAndMethod;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestHandlerTest {
    private BufferedReader bufferedReader;
    private Request request;
    private final String line = "GET /index.html HTTP/1.1";

    @BeforeEach
    public void before() throws IOException {
        request = new Request();
        File file = new File("src/main/resources/Text.txt");
        bufferedReader = new BufferedReader(new FileReader(file));

    }

    @Test
    public void testInjectHeadersReturnCorrectHeaders() {
        Request expectedRequest = new Request();
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "ru.wikipedia.org");
        expectedHeaders.put("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5");
        expectedHeaders.put("Accept", "text/html");
        expectedHeaders.put("Connection", "close");
        expectedRequest.setHeaders(expectedHeaders);
        injectHeaders(bufferedReader, request);

        assertEquals(expectedRequest.getHeaders(), request.getHeaders());
    }

    @Test
    public void testInjectUriAndMethodReturnCorrectUri() {
        request.setUri("/index.html");
        Request actualRequest = new Request();
        injectUriAndMethod(line, actualRequest);

        assertEquals(request.getUri(), actualRequest.getUri());
    }

    @Test
    public void testInjectUriAndMethodReturnCorrectMethod() {
        request.setMethod(HttpMethod.valueOf("GET"));
        Request actualRequest = new Request();
        injectUriAndMethod(line, actualRequest);

        assertEquals(request.getMethod(), actualRequest.getMethod());
    }
}
