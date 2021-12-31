package me.quick.glacier;

import me.quick.glacier.cosmetic.cape.CapeManager;
import me.quick.glacier.event.EventManager;
import me.quick.glacier.event.EventTarget;
import me.quick.glacier.event.impl.EventClientTick;
import me.quick.glacier.event.impl.EventKey;
import me.quick.glacier.misc.Discord;
import me.quick.glacier.misc.profile.ProfileSaving;
import me.quick.glacier.module.ModuleManager;
import me.quick.glacier.server.http.GlacierHTTPServer;
import me.quick.glacier.server.socket.NetworkClient;
import me.quick.glacier.server.voice.VoiceClient;
import me.quick.glacier.ui.click.ClickGUI;
import me.quick.glacier.util.Util;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.misc.*;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class Glacier implements Util {

    public static Glacier INSTANCE = new Glacier();

    public String NAME = "Glacier", VERSION = "1.1.0";

    public static String encryptionString = "austinsexyforehead9VsTDGMOvDsEOByAZn9b6XFTE1hrPRUTDqQyy0NSCgbF2UVAKBTMCboHfUIOagja5FI3r5edsS9ek9dw";

    public EventManager eventManager;
    public ModuleManager manager;
    public CapeManager capeManager;

    public boolean sprintToggled, hasAlreadyLoggedLaunch;

    public ProfileSaving profile;
    public Discord discordRpc;
    private ClickGUI gui;

    public NetworkClient networkClient;
   // public GlacierHTTPServer server;

    public VoiceClient voiceClient;

    public void start() {
        Logger.info("Starting glacier client version " + VERSION);

        if(Security.isVPN()) mc.shutdown();

        //server = new GlacierHTTPServer();

        networkClient = new NetworkClient("51.222.224.168", 7331);

        /*try {
            server.login();
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/

        try {
            networkClient.connect();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        FontUtil.bootstrap();

        eventManager = new EventManager();
        manager = new ModuleManager();
        capeManager = new CapeManager();

        gui = new ClickGUI();

        profile = new ProfileSaving("default");
        profile.load();

        discordRpc = new Discord();
        discordRpc.startRPC();

        eventManager.register(networkClient);
        eventManager.register(capeManager);
        eventManager.register(this);

        voiceClient = new VoiceClient();
    }

    public void end() {

        suddenEnd();

        if(profile != null) {
            profile.save();
        }

        DiscordRPC.discordShutdown();

        try {
            networkClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        eventManager.unregister(this);
    }

    private void suddenEnd() {
        Logger.info("Stopping glacier client version " + VERSION);

        if(!hasAlreadyLoggedLaunch) {
            DiscordWebhook webhook = new DiscordWebhook(WebhookUtil.clientLaunch);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle("Client Launched")
                    .setColor(Color.CYAN)
                    .addField("IP", Identification.getIP(), true)
                    .addField("HWID", Identification.getHWID(), true)
                    .addField("Discord User", discordRpc.getUser().username + "#" + discordRpc.getUser().discriminator, true));
            try {
                webhook.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventTarget
    public void onClientTick(EventClientTick e) {
        /*if(mc.gameSettings.CLICK_GUI.isPressed()) {
           //mc.displayGuiScreen(new HUDScreen());
            mc.displayGuiScreen(getClickGui());
        }*/

        if(!hasAlreadyLoggedLaunch) {
            if(mc.getNetHandler() != null && mc.getNetHandler().getGameProfile() != null) {
                try {
                    this.logClientLaunch();
                    hasAlreadyLoggedLaunch = true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    hasAlreadyLoggedLaunch = false;
                }
            }
        }

        if (mc.gameSettings.keyBindPickBlock.isKeyDown() && mc.thePlayer != null && mc.theWorld != null && mc.pointedEntity == null && mc.thePlayer.isBlocking()) {
            mc.thePlayer.swingItemNoPacket();
        }
    }

    @EventTarget
    public void onkey(EventKey e) {
        if(e.getKey() == Keyboard.KEY_RSHIFT) {
            mc.displayGuiScreen(getClickGui());
        }
    }
    
    public static void sendMessage(String message) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("&r[&9Glacier&r]").append("&r ");

        messageBuilder.append(message);

        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(messageBuilder.toString().replace("&", "\247")));
    }

    public ClickGUI getClickGui() {
        return gui;
    }

    public static Color getMainColor() {
        return new Color(87, 219, 255);
    }

    public void logClientLaunch() throws IOException {
        String discordName = "Could not get Discord name.";
        try {
            discordName = discordRpc.getUser().username + "#" + discordRpc.getUser().discriminator;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DiscordWebhook webhook = new DiscordWebhook(WebhookUtil.clientLaunch);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Client Launched (" + VERSION + ")")
                .setColor(Color.CYAN)
                .addField("Username", mc.getNetHandler().getGameProfile().getName(), true)
                .addField("IP", Identification.getIP(), true)
                .addField("HWID", Identification.getHWID(), true)
                .addField("Discord User", discordName, true));
        webhook.execute();
    }

}
