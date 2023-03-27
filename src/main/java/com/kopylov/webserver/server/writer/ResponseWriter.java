package com.kopylov.webserver.server.writer;

import com.kopylov.webserver.server.entity.StatusCode;

import java.io.*;

public class ResponseWriter {
    public static void writeResponse(InputStream content, OutputStream outputStream) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("HTTP/1.1 "+ StatusCode.OK.getStatus());
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.flush();
        byte[] bytes = content.readAllBytes();
        outputStream.write(bytes);
        bufferedWriter.close();
        outputStream.close();
    }

    public static void writeError(OutputStream outputStream, StatusCode statusCode) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("HTTP/1.1" + statusCode.getCode() + " " + statusCode.getStatus());
    }
}
