package me.quick.glacier.server.socket.packet.impl;

import me.quick.glacier.cosmetic.cape.Capes;
import me.quick.glacier.server.socket.packet.Packet;
import net.minecraft.client.Minecraft;

public class GPacketChangeCape extends Packet {

    public Capes cape;

    public GPacketChangeCape(Capes cape) {
        this.cape = cape;
    }

    @Override
    public String getSendMessage() {
        return "changecape;uuid:" + Minecraft.getMinecraft().thePlayer.getUniqueID() + ";capename:" + cape.getID();
    }
}
