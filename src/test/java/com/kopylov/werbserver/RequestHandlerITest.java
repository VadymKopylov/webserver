package com.kopylov.werbserver;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.kopylov.webserver.server.request.RequestParser.collectRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestHandlerITest {
    @Test
    public void testCollectRequestReturnCorrectListOfLines() throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("GET /wiki/страница HTTP/1.1");
        lines.add("Host: ru.wikipedia.org");
        lines.add("User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5");
        lines.add("Accept: text/html");
        lines.add("Connection: close");

        File file = new File("src/main/resources/Text.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
            List<String> actualList = collectRequest(bufferedReader);

            assertEquals(lines, actualList);
        }
    }
}
