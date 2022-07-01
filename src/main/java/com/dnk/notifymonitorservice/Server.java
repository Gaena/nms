package com.dnk.notifymonitorservice;

import com.dnk.notifymonitorservice.server.TcpServer;
import com.dnk.notifymonitorservice.utils.AppProperties;


public class Server {


    public static void main(String[] args) {
        //Load properties
        AppProperties appProperties = AppProperties.getInstance();
        TcpServer server = new TcpServer(Integer.parseInt(appProperties.getValue("server.port")));
        server.run();

        // Details for the properties -
        // http://docs.oracle.com/javase/7/docs/technotes/guides/security/jsse/JSSERefGuide.html
        System.setProperty("jsse.enableSNIExtension", "false");
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        System.setProperty("sun.security.ssl.allowLegacyHelloMessages", "true");
    }

}
