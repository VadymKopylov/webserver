package com.kopylov.webserver.server.writer;

import com.kopylov.webserver.server.entity.StatusCode;

import java.io.*;

public class ResponseWriter {
    public static void writeResponse(InputStream content, OutputStream outputStream) throws IOException {
        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
        outputStream.write("\r\n".getBytes());
        byte[] arr = new byte[4096];
        int count;
        while ((count = content.read(arr)) != -1) {
            outputStream.write(count);
        }
        outputStream.write("\r\n\r\n".getBytes());


        outputStream.flush();
        content.close();
        outputStream.close();
    }

    public static void writeError(OutputStream outputStream, StatusCode statusCode) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("HTTP/1.1" + statusCode.getCode() + " " + statusCode.getStatus());
    }
}
