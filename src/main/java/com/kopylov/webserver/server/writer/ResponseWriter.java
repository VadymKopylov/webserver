package com.kopylov.webserver.server.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class ResponseWriter {
    public static void writeResponse(File path, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("HTTP/1.1 200 OK");
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(String.valueOf(path));
    }
}
