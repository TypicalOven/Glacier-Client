package me.quick.glacier.util.misc;

import me.quick.glacier.util.Util;

public class Logger implements Util {

    public static void info(String message) {
        System.out.println("[Glacier] " + message);
    }

    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }

}
