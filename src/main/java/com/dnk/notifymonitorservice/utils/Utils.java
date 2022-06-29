package com.dnk.notifymonitorservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public final class Utils {

    public static File readIniFile() throws URISyntaxException, MalformedURLException {
        URL resource = new URL("file://config/ipconfig.ini");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            return new File(resource.toURI());
        }
    }

    public static Object getObjectFromJson(String json, Class<?> cl) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, cl);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
