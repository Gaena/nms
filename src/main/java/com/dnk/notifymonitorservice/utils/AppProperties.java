package com.dnk.notifymonitorservice.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    private Properties prop = new Properties();

    private static volatile AppProperties appProperties;

    private AppProperties() {
        try (InputStream input = new FileInputStream("config/application.properties")) {
            this.prop = new Properties();
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static AppProperties getInstance() {
        if (appProperties == null) {
            synchronized (AppProperties.class) {
                if (appProperties == null) {
                    appProperties = new AppProperties();
                }
            }
        }
        return appProperties;
    }


    public String getValue(String key) {
        return prop.getProperty(key);
    }
}
