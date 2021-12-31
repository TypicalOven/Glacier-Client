package me.quick.glacier.util.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;

public class Identification {

    public static String getHWID() {
        String hwid = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getProperty("os.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
        return Encryption.hashMD5(hwid);
    }

    public static String getIP() {
        try {
            URL ip = new URL("http://checkip.amazonaws.com");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    ip.openStream()));
            if(bufferedReader.readLine().equals("this would be where quick's or austin's ip was, but i removed it.")) {
                return "69.420.666.21";
            } else return bufferedReader.readLine();
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }
}