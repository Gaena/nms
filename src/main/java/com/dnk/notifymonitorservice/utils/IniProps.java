package com.dnk.notifymonitorservice.utils;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IniProps {

    private static volatile IniProps iniProps;
    private Ini ini;

    private IniProps() {
        try {
            File iniFile = new File("config/ipconfig.ini");
            this.ini = new Ini(iniFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static IniProps getInstance() {
        if (iniProps == null) {
            synchronized (IniProps.class) {
                if (iniProps == null) {
                    iniProps = new IniProps();
                }
            }
        }
        return iniProps;
    }

    public Map<String , List<String>> getConfig() {
        Map<String , List<String>> ipConfigs = new HashMap<>();

        for (String sectionName : ini.keySet()) {
            List<String> forwardIpList = new ArrayList<>();

            Profile.Section section = ini.get(sectionName);


            for (String optionKey : section.keySet()) {
                forwardIpList.add(section.get(optionKey));
            }

            ipConfigs.put(sectionName , forwardIpList);
        }

        return ipConfigs;
    }

}
