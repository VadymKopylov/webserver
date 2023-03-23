package com.kopylov.webserver.server.reader;

import com.kopylov.webserver.server.entity.Request;
import com.kopylov.webserver.server.exceptions.ServerException;

import java.io.*;

import static com.kopylov.webserver.server.entity.StatusCode.NOT_FOUND;

public class ContentReader {
    private final String webAppPath;

    public ContentReader(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public InputStream read(Request request) {
        try {
            return new FileInputStream(new File(webAppPath, request.getUri()));
        } catch (FileNotFoundException e) {
            throw new ServerException(NOT_FOUND, e);
        }
    }
}
