package com.kopylov.webserver.server.writer;

import com.kopylov.webserver.server.entity.StatusCode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class ResponseWriter {
    public static void writeResponse(byte[] content, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("HTTP/1.1 200 OK");
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        for (byte b : content) {
            bufferedWriter.write(b);
        }
    }

    public static void writeError(BufferedWriter bufferedWriter, StatusCode statusCode) throws IOException {
        bufferedWriter.write("HTTP/1.1" + statusCode.getCode()+ " "+ statusCode.getStatus());
    }
}
