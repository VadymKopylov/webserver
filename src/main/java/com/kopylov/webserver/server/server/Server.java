package com.kopylov.webserver.server.server;

import com.kopylov.webserver.server.reader.ContentReader;
import com.kopylov.webserver.server.request.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private String webAppPath;

    public void start() throws IOException {
        ContentReader contentReader = new ContentReader(webAppPath);
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                try (Socket socket = serverSocket.accept();
                     InputStream input = socket.getInputStream();
                     OutputStream output = socket.getOutputStream();) {

                    RequestHandler requestHandler = new RequestHandler(input, output, contentReader);
                    requestHandler.handle();
                }
            }
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }
}
