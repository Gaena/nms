package com.dnk.notifymonitorservice.server;

import com.dnk.notifymonitorservice.domain.LoginResponse;
import com.dnk.notifymonitorservice.utils.AppProperties;
import com.dnk.notifymonitorservice.utils.HttpClient;
import com.dnk.notifymonitorservice.utils.IniProps;
import com.dnk.notifymonitorservice.utils.Utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BasIpEventHandler {

    private AppProperties appProperties;
    private IniProps iniProps;
    private HttpClient httpClient;
    private Map<String, List<String>> ipConfigMap;

    public BasIpEventHandler() {
        this.appProperties = AppProperties.getInstance();
        this.iniProps = IniProps.getInstance();
        this.ipConfigMap = iniProps.getConfig();
        this.httpClient = HttpClient.getInstance();
    }

    public void handle(String event) {
        System.out.println(LocalDateTime.now() + " " +"Handled event : " + event);

        String ip = Utils.getEventIp(event);

        if (ipConfigMap.containsKey(ip)) {
            List<String> ipList = ipConfigMap.get(ip);
            for (String ipItem : ipList) {
                try {
                    LoginResponse loginResponse = httpClient.sendAuthRequest(ipItem,
                            appProperties.getValue("basip.port"),
                            appProperties.getValue("basip.username"),
                            appProperties.getValue("basip.password")
                    );


                    httpClient.sendOpenLock(
                            ipItem,
                            appProperties.getValue("basip.port"),
                            loginResponse.getToken()
                    );

                    System.gc();

                } catch (IOException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new RuntimeException("Main IP not found in config file : " + ip);
        }

    }
}
