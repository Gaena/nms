package com.dnk.notifymonitorservice.server;

import java.io.*;
import java.net.Socket;

public class TcpServerSocketHandler implements Runnable {

    protected Socket socket = null;

    public TcpServerSocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String message = reader.readLine();

            this.handle(message);

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println(1);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void handle(String event) {
        event = event.split(" ")[4];
        if (event.contains("EVENT:106")) {
            BasIpEventHandler basIpEventHandler = new BasIpEventHandler();
            basIpEventHandler.handle(event);
        }
    }
}
