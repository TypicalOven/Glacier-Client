package me.quick.glacier.server.socket.handler;

import me.quick.glacier.server.Main;
import me.quick.glacier.server.util.EnumChatFormatting;
import me.quick.glacier.server.util.ServerEncryption;
import me.quick.glacier.server.util.Timer;

import java.util.concurrent.CopyOnWriteArrayList;

public class AnnouncementHandler implements Runnable {

    public CopyOnWriteArrayList<String> announcements;
    public Timer timer;

    int index = 0;

    public AnnouncementHandler() {

        announcements = new CopyOnWriteArrayList<>();
        timer = new Timer();
        timer.reset();

        announcements.add("Join our discord server at https://discord.gg/QFMf8GuEvF");
        announcements.add("You guys should totally sub to quick - https://youtube.com/c/quickdaffy");
    }

    @Override
    public void run() {

        while (true) {
            if(timer.hasTimeElapsed(120000, true)) {
                if(index > announcements.size() - 1) index = 0;
                Main.server.broadcastAnnouncement(ServerEncryption.encrypt(EnumChatFormatting.SERVERTAG + announcements.get(index), Main.key));
                index++;
            }
        }

    }
}