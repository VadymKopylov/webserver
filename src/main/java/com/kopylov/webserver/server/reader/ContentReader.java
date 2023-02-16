package com.kopylov.webserver.server.reader;

import com.kopylov.webserver.server.request.Request;

import java.io.File;

public class ContentReader {
    public static File read(Request request, String webAppPath) {
        return new File(webAppPath, request.getUri());
    }
}
