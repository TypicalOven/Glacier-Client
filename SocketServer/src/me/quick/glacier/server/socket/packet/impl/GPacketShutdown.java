package me.quick.glacier.server.socket.packet.impl;

import me.quick.glacier.server.socket.packet.Packet;

public class GPacketShutdown extends Packet {

    @Override
    public String getSendMessage() {
        return "command;shutdown";
    }
}
