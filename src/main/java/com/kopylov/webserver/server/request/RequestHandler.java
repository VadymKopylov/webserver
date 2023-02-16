package com.kopylov.webserver.server.request;

import com.kopylov.webserver.server.reader.ContentReader;
import com.kopylov.webserver.server.writer.ResponseWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class RequestHandler {
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private String webAppPath;

    public RequestHandler(BufferedReader bufferedReader, BufferedWriter bufferedWriter, String webAppPath) {
        this.socketReader = bufferedReader;
        this.socketWriter = bufferedWriter;
        this.webAppPath = webAppPath;
    }

    public void handle() throws IOException {
        Request request = RequestParser.parse(socketReader);
        File pathToContent = ContentReader.read(request, webAppPath);
        ResponseWriter.writeResponse(pathToContent, socketWriter);
    }
}

