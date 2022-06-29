package com.dnk.notifymonitorservice.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer implements Runnable {

    private int port;
    private ServerSocket serverSocket;

    private boolean running = true;

    public TcpServer(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("TCP Server run on port :" + this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        while (running) {
            try {
                final Socket socket = serverSocket.accept();
                new Thread(new TcpServerSocketHandler(socket)).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutdown() {
        try {
            this.running = false;
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
