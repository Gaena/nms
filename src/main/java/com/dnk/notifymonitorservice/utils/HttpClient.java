package com.dnk.notifymonitorservice.utils;

import com.dnk.notifymonitorservice.domain.LoginResponse;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;

public class HttpClient {

    private static volatile HttpClient httpClient;

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        if (httpClient == null) {
            synchronized (AppProperties.class) {
                if (httpClient == null) {
                    httpClient = new HttpClient();
                }
            }
        }
        return httpClient;
    }

    public LoginResponse sendAuthRequest(String ip, String port, String username, String password) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hashPass = DatatypeConverter.printHexBinary(digest).toUpperCase();


        String urlStr = String.format("http://%s:%s/api/v1/login?username=%s&password=%s", ip, port, username, hashPass);

        System.out.println(LocalDateTime.now() + " " + "Requested url : " + urlStr);

        String response = this.sendRequest(urlStr, null);

        System.out.println(LocalDateTime.now() + " " + "Incoming response : " + response);

        return (LoginResponse) Utils.getObjectFromJson(response, LoginResponse.class);
    }

    public void sendOpenLock(String ip, String port, String token) throws IOException {
        String urlStr = String.format("http://%s:%s/api/v1/access/general/lock/open/remote/accepted/0", ip, port);

        System.out.println(LocalDateTime.now() + " " + "Requested url : " + urlStr);
        this.sendRequest(urlStr, token);
    }

    private String sendRequest(String urlStr, String token) throws IOException {
        URL url = new URL(urlStr);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setAllowUserInteraction(false);
        con.setRequestMethod("GET");
        if (Objects.nonNull(token)) {
            con.addRequestProperty("Authorization", "Bearer " + token);
        }
        con.connect();
        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            System.out.println(LocalDateTime.now() + " " + "Response successful");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            return content.toString();
        } else {
            throw new RemoteException("Unexpected response code : " + responseCode);
        }

    }
}