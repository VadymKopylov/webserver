package com.kopylov.webserver.server.request;

import com.kopylov.webserver.server.entity.HttpMethod;
import com.kopylov.webserver.server.entity.Request;
import com.kopylov.webserver.server.exceptions.ServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.kopylov.webserver.server.entity.StatusCode.*;

public class RequestParser {
    static Request parse(BufferedReader bufferedReader) throws IOException {
        Request request = new Request();
        List<String> lines = collectRequest(bufferedReader);
        injectUriAndMethod(lines, request);
        injectHeaders(lines, request);
        return request;
    }

    public static void injectUriAndMethod(List<String> lines, Request request) {
        String line = lines.get(0);
        String requestUri = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
        String httpMethodLine = line.substring(0, line.indexOf(" "));
        request.setUri(requestUri);
        HttpMethod httpMethod = HttpMethod.valueOf(httpMethodLine);
        if (!httpMethod.equals(HttpMethod.GET)) {
            throw new ServerException(METHOD_NOT_ALLOWED);
        }
        request.setMethod(httpMethod);
    }

    public static void injectHeaders(List<String> lines, Request request) {
        Map<String, String> map = new HashMap<>();
        lines.stream().skip(1l)
                .forEach(line -> {
                    String[] headers = line.split(": ");
                    String values = Arrays.stream(headers).skip(1L).collect(Collectors.joining());
                    map.put(headers[0], values);
                });
        request.setHeaders(map);
    }

    public static List<String> collectRequest(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (line != null && line.length() > 0) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        return lines;
    }
}

