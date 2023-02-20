package com.kopylov.webserver.server.reader;

import com.kopylov.webserver.server.entity.Request;
import com.kopylov.webserver.server.exceptions.ServerException;

import java.io.*;

import static com.kopylov.webserver.server.entity.StatusCode.*;

public class ContentReader {
    public static byte[] read(Request request, String webAppPath) throws IOException {
        File pathToFile = new File(webAppPath, request.getUri());
        int fileLength = (int) pathToFile.length();
        byte[] contentArray = new byte[fileLength];

        try (FileInputStream fileInputStream = new FileInputStream(pathToFile)) {
            fileInputStream.read(contentArray);
        } catch (FileNotFoundException e) {
            throw new ServerException(NOT_FOUND);
        }
        return contentArray;
    }
}
