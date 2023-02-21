package com.kopylov.werbserver;

import com.kopylov.webserver.server.entity.HttpMethod;
import com.kopylov.webserver.server.entity.Request;

import com.kopylov.webserver.server.entity.StatusCode;
import com.kopylov.webserver.server.exceptions.ServerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kopylov.webserver.server.reader.ContentReader.read;
import static com.kopylov.webserver.server.request.RequestParser.*;
import static com.kopylov.webserver.server.writer.ResponseWriter.writeError;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestHandlerTest {
    private BufferedReader bufferedReader;
    private Request request;
    private final String firstLine = "GET /index.html HTTP/1.1";
    private List<String> lines = new ArrayList<>();

    @BeforeEach
    public void before() throws IOException {
        request = new Request();
        File file = new File("src/main/resources/Text.txt");
        bufferedReader = new BufferedReader(new FileReader(file));
    }

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
    public void testCollectRequestReturnCorrectListOfLines() throws IOException {
        lines.add("GET /wiki/страница HTTP/1.1");
        lines.add("Host: ru.wikipedia.org");
        lines.add("User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5");
        lines.add("Accept: text/html");
        lines.add("Connection: close");

        List<String> actualList = collectRequest(bufferedReader);

        assertEquals(lines, actualList);
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

    @Test
    public void testReadThrowsServerExceptionWhenFileNotFound() {
        ServerException serverException = Assertions.assertThrows(ServerException.class, () -> {
            String wrongWebAppPath = "src/main";
            request.setUri("/Text.txt");
            read(request, wrongWebAppPath);
        });
        String actualException = serverException.getStatusCode().getCode() + " " + serverException.getStatusCode().getStatus();
        String expectedException = "404 Not found";
        assertEquals(actualException, expectedException);
    }

    @Test
    public void testResponseWriterCatchServerExceptionAndWriteCorrectMessage() {

    }
}
