package me.quick.glacier.server.socket.packet.impl;

import me.quick.glacier.server.socket.packet.Packet;
import net.minecraft.client.Minecraft;

public class GPacketKeepAlive extends Packet {

    @Override
    public String getSendMessage() {
        return "keepalive;username:" + Minecraft.getMinecraft().thePlayer.getName();
    }
}
