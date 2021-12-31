package me.quick.glacier.misc;

import me.quick.glacier.Glacier;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;

public class Discord {

    private DiscordUser user;
    public DiscordRichPresence presence;

    public void startRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Welcome " + user.username + "#" + user.discriminator + "!");
            this.user = user;
        }).build();
        DiscordRPC.discordInitialize("897585718916960266", handlers, true);
        presence = new DiscordRichPresence.Builder("Glacier client best").setDetails("Using Glacier Client").setBigImage("glacier_logo", "https://glacierclient.net").build();
        DiscordRPC.discordUpdatePresence(presence);

        new Thread("Discord RPC Callback") {

            @Override
            public void run() {
                while(true) {
                    DiscordRPC.discordRunCallbacks();
                }
            }

        }.start();
    }

    public void setUpperText(String details) {
        presence.details = details;
        DiscordRPC.discordUpdatePresence(presence);
    }

    public void setLowerText(String state) {
        presence.state = state;
        DiscordRPC.discordUpdatePresence(presence);
    }


    public DiscordUser getUser() {
        return user;
    }

}
