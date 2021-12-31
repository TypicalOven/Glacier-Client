package me.quick.glacier.server.socket.packet.impl;

import me.quick.glacier.server.socket.packet.Packet;

import java.util.UUID;

public class GPacketUserUpdateCape extends Packet {

    public String cape;
    public UUID uuid;

    public GPacketUserUpdateCape(UUID uuid, String cape) {
        this.uuid = uuid;
        this.cape = cape;
    }

    @Override
    public String getSendMessage() {
        return "userupdatecape;uuid:" + uuid.toString() + ";capename:" + cape;
    }

}
