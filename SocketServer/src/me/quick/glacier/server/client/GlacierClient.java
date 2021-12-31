package me.quick.glacier.server.client;

import me.quick.glacier.server.util.ServerEncryption;
import me.quick.glacier.server.Main;
import me.quick.glacier.server.socket.packet.Packet;
import me.quick.glacier.server.util.Timer;

import java.io.PrintStream;

public class GlacierClient {

    public PrintStream printStream;
    public String username, hwid, ip;
    public Timer timeSinceKeepalive;

    public GlacierClient() {
        timeSinceKeepalive = new Timer();
    }

    public void sendMessage(String message) {
        printStream.println(message);
    }

    public String getHwid() {
        return hwid;
    }

    public String getIp() {
        return ip;
    }

    public String getUsername() {
        return username;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void setHwid(String hwid) {
        this.hwid = hwid;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimeSinceLastKeepalive() {
        return timeSinceKeepalive.getTime();
    }

    public void resetKeepAliveTime() {
        timeSinceKeepalive.reset();
    }

    public void sendPacket(Packet packet) {
        sendMessage(ServerEncryption.encrypt(packet.getSendMessage(), Main.key));
    }
}
