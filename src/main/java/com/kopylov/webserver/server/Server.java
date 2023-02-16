package com.kopylov.webserver.server;

import com.kopylov.webserver.server.request.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private String webAppPath;

    void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    RequestHandler requestHandler = new RequestHandler(bufferedReader, bufferedWriter, webAppPath);
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
