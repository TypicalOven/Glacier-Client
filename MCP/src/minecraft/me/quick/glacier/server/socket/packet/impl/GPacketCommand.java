package me.quick.glacier.server.socket.packet.impl;


import me.quick.glacier.server.socket.packet.Packet;
import net.minecraft.client.Minecraft;

public class GPacketCommand extends Packet {

    public String ircMessage;

    public GPacketCommand(String message) {
        this.ircMessage = message;
    }

    @Override
    public String getSendMessage() {
        return "command;nick:" + Minecraft.getMinecraft().thePlayer.getName() +";message:" + ircMessage;
    }
}
