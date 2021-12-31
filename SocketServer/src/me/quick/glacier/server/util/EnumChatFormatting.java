package me.quick.glacier.server.util;

import sun.java2d.windows.GDIRenderer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class EnumChatFormatting {

    public static String encoder = "\247";
    public static String BOLD = encoder + "l";
    public static String AQUA = encoder + "b";
    public static String RESET = encoder + "r";
    public static String WHITE = encoder + "f";
    public static String GRAY = encoder + "7";

    public static String SERVERTAG = BOLD + "" + AQUA + "Server" + RESET + GRAY + " >> " + WHITE;

}
