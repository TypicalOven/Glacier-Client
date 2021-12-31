package me.quick.glacier.server.socket;

import me.quick.glacier.Glacier;
import me.quick.glacier.event.EventTarget;
import me.quick.glacier.event.impl.EventChat;
import me.quick.glacier.event.impl.EventUpdate;
import me.quick.glacier.server.socket.packet.Packet;
import me.quick.glacier.server.socket.packet.impl.GPacketCommand;
import me.quick.glacier.server.socket.packet.impl.GPacketIRCMessage;
import me.quick.glacier.server.socket.packet.impl.GPacketKeepAlive;
import me.quick.glacier.server.socket.packet.impl.GPacketLogIn;
import me.quick.glacier.util.misc.Encryption;
import me.quick.glacier.util.misc.Timer;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class NetworkClient {

    public String host;
    public int port;
    public Socket socket;

    public boolean loggedInToServer;

    public Timer keepAliveTimer;

    public Thread receivedMessageThread;

    public NetworkClient(String host, int port) {
        this.host = host;
        this.port = port;

        loggedInToServer = false;
    }

    public void connect() throws Exception {
        keepAliveTimer = new Timer();
        socket = new Socket(host, port);
        System.out.println("Client successfully connected to server!");
        keepAliveTimer.reset();

        receivedMessageThread = new Thread(new ReceivedMessagesHandler(socket.getInputStream()));
        receivedMessageThread.start();

        //socket.close();
    }

    public void disconnect() throws Exception {
        socket.close();
        receivedMessageThread.interrupt();
    }

    public void sendIRCMessage(String message) throws IOException {
        if(!loggedInToServer) {
            Glacier.sendMessage("You are not connected to the Glacier Client servers!");
            return;
        }
        this.sendPacket(new GPacketIRCMessage(message));
    }

    public void sendCommand(String message) throws IOException {
        if(!loggedInToServer) {
            Glacier.sendMessage("You are not connected to the Glacier Client servers!");
            return;
        }
        this.sendPacket(new GPacketCommand(message));
    }

    public void sendPacket(Packet p) throws IOException {
        if(!loggedInToServer) {
            return;
        }
        PrintStream output = new PrintStream(socket.getOutputStream());
        output.println(Encryption.encrypt(p.getSendMessage(), Glacier.encryptionString));
    }

    @EventTarget
    public void onChat(EventChat e) {
        String message = e.getMessage();

        if (!message.startsWith("-"))
            return;

        e.setCancelled(true);

        message = message.substring("-".length());

        try {
            sendIRCMessage(message);
            System.out.println("sent irc message");
        } catch (IOException ioException) {
            ioException.printStackTrace();
            Glacier.sendMessage("The IRC seems to be down or locked right now. Check back in later!");
        }

    }

    @EventTarget
    public void onUpdate(EventUpdate e) {

        if(!loggedInToServer && Minecraft.getMinecraft().thePlayer != null) {
            try {
                GPacketLogIn loginPacket = new GPacketLogIn();
                PrintStream output = new PrintStream(socket.getOutputStream());
                output.println(Encryption.encrypt(loginPacket.getSendMessage(), Glacier.encryptionString));
                System.out.println("Sent login packet.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            loggedInToServer = true;
        }

        if(keepAliveTimer.hasTimeElapsed(30000, true)) {
            try {
                sendPacket(new GPacketKeepAlive());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}