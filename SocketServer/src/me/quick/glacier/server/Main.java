package me.quick.glacier.server;

import me.quick.glacier.server.cosmetic.CosmeticManager;
import me.quick.glacier.server.socket.NetworkServer;
import me.quick.glacier.server.voice.VoiceChat;

public class Main {

    public static String key = "austinsexyforehead9VsTDGMOvDsEOByAZn9b6XFTE1hrPRUTDqQyy0NSCgbF2UVAKBTMCboHfUIOagja5FI3r5edsS9ek9dw";

    public static NetworkServer server;
    public static CosmeticManager cosmeticManager;

    public static VoiceChat voiceChat;

    public static void main(String[] args) {
        cosmeticManager = new CosmeticManager();
        /*new Thread(() -> {
            voiceChat = new VoiceChat();
            voiceChat.run();
        }).start();*/
        try {
            server = new NetworkServer(7331);
            server.run();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
