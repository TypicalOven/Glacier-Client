package me.quick.glacier.server.socket.packet.impl;

import me.quick.glacier.server.socket.packet.Packet;
import me.quick.glacier.util.misc.Identification;
import me.quick.glacier.util.misc.MathUtils;
import net.minecraft.client.Minecraft;

public class GPacketLogIn extends Packet {

    @Override
    public String getSendMessage() {
        return "login;username:" + Minecraft.getMinecraft().getNetHandler().getGameProfile().getName() + ";hwid:" + Identification.getHWID() + ";ip:" + Identification.getIP();
    }
}
