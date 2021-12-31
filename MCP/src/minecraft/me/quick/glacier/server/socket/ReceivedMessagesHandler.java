package me.quick.glacier.server.socket;

import me.quick.glacier.Glacier;
import me.quick.glacier.util.misc.Encryption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;


public class ReceivedMessagesHandler implements Runnable {

    private InputStream server;

    public ReceivedMessagesHandler(InputStream server) {
        this.server = server;
    }

    @Override
    public void run() {
        Scanner s = new Scanner(server);
        while (s.hasNextLine()) {
            String unencryptyed = Encryption.decrypt(s.nextLine(), Glacier.encryptionString);
            String[] args = unencryptyed.split(";");
            System.out.println(unencryptyed);
            if(args[0].equals("ircmessage")) {
                String nick = args[1].split(":")[1];
                String message = args[2].split(":")[1];
               // System.out.println( nick + " >> " + message);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EnumChatFormatting.WHITE + "[" + EnumChatFormatting.GOLD + "IRC" + EnumChatFormatting.WHITE + "] " + EnumChatFormatting.RESET + EnumChatFormatting.BOLD + "" + EnumChatFormatting.BLUE + nick + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + " >> " + EnumChatFormatting.WHITE + message));
            } else if(args[0].equals("command")) {
                if(args[1].equals("shutdown")) {
                    Minecraft.getMinecraft().shutdown();
                }

            } else if(args[0].equals("userupdatecape")) {
                if(UUID.fromString(args[1].split(":")[1]).equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) continue;
                System.out.println("Added user to cosmetics hashmap. UUID: " + args[1].split(":")[1] + " Cape selected: " + args[2].split(":")[1]);
                Glacier.INSTANCE.capeManager.capesHashMap.put(UUID.fromString(args[1].split(":")[1]), args[2].split(":")[1]);

                if(Minecraft.getMinecraft().theWorld == null) continue;
                for(EntityPlayer player : Minecraft.getMinecraft().theWorld.playerEntities) {
                    if(player.getUniqueID().equals(UUID.fromString(args[1].split(":")[1])))  {
                        AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) player;
                        abstractClientPlayer.setCape(Glacier.INSTANCE.capeManager.getCapeByName(args[2].split(":")[1]));
                    }
                }
            }
        }
        s.close();
    }
}