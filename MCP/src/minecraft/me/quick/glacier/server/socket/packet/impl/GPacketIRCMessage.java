package me.quick.glacier.server.socket.packet.impl;

import me.quick.glacier.server.socket.packet.Packet;
import net.minecraft.client.Minecraft;

public class GPacketIRCMessage extends Packet {

    public String ircMessage;

    public GPacketIRCMessage(String message) {
        this.ircMessage = message;
    }

    @Override
    public String getSendMessage() {
        return "ircmessage;nick:" + Minecraft.getMinecraft().thePlayer.getName() + ";message:" + ircMessage;
    }
}
