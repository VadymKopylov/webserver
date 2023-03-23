package com.kopylov.webserver.server.request;

import com.kopylov.webserver.server.entity.Request;
import com.kopylov.webserver.server.exceptions.ServerException;
import com.kopylov.webserver.server.reader.ContentReader;
import com.kopylov.webserver.server.writer.ResponseWriter;

import java.io.*;

public class RequestHandler {
    private InputStream socketReader;
    private OutputStream socketWriter;
    private ContentReader contentReader;


    public RequestHandler(InputStream inputStream, OutputStream outputStream, ContentReader contentReader) {
        this.socketReader = inputStream;
        this.socketWriter = outputStream;
        this.contentReader = contentReader;
    }

    public void handle() throws IOException {
        try {
            Request request = RequestParser.parse(socketReader);
            InputStream content = contentReader.read(request);
            ResponseWriter.writeResponse(content, socketWriter);
        } catch (ServerException e) {
            ResponseWriter.writeError(socketWriter, e.getStatusCode());
        }
    }
}

