package com.dnk.notifymonitorservice.server;

import com.dnk.notifymonitorservice.utils.AppProperties;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

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

            StringBuilder message = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                message.append(line);
            }
            input.close();
            reader.close();

            this.handle(message.toString());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void handle(String event) {
//        AppProperties appProperties = AppProperties.getInstance();
        System.out.println(LocalDateTime.now() + ":" + event.substring(event.lastIndexOf("EVENT")));
        if (event.contains("Door")
                && event.contains("opened")
                && (!event.contains("EVENT:105:Door opened by exit button"))) {
            BasIpEventHandler basIpEventHandler = new BasIpEventHandler();
            basIpEventHandler.handle(event);
        }

    }
}
