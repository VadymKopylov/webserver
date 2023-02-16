package com.kopylov.webserver.server.request;

import com.kopylov.webserver.server.entity.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestParser {
    static Request parse(BufferedReader bufferedReader) throws IOException {
        Request request = new Request();
        String firstLine = bufferedReader.readLine();
        injectUriAndMethod(firstLine, request);
        injectHeaders(bufferedReader, request);
        return request;
    }

    public static void injectUriAndMethod(String line, Request request) {
        String requestUri = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
        String httpMethod = line.substring(0, line.indexOf(" "));
        request.setUri(requestUri);
        request.setMethod(HttpMethod.valueOf(httpMethod));
    }

    public static void injectHeaders(BufferedReader bufferedReader, Request request) {
        Map<String, String> map = new HashMap<>();
        Stream<String> lines = bufferedReader.lines();
        lines.forEach(line -> {
                    String[] headers = line.split(":");
                    String values = Arrays.stream(headers).skip(1L).collect(Collectors.joining());
                    map.put(headers[0], values);
                });
        request.setHeaders(map);
    }
}

