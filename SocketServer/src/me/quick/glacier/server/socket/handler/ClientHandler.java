package me.quick.glacier.server.socket.handler;

import me.quick.glacier.server.Main;
import me.quick.glacier.server.client.GlacierClient;
import me.quick.glacier.server.socket.NetworkServer;
import me.quick.glacier.server.socket.packet.impl.GPacketShutdown;
import me.quick.glacier.server.util.ServerEncryption;
import me.quick.glacier.server.util.StaffUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private NetworkServer server;
    private InputStream client;
    private Socket socket;

    public ClientHandler(NetworkServer server, InputStream client, Socket socket) {
        this.server = server;
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        String message;

        Scanner sc = new Scanner(this.client);
        while (sc.hasNextLine()) {
            try {
                if(socket.isClosed()) {
                    Main.server.clients.remove(socket);
                }
                String unEncrypted = sc.nextLine();
                message = ServerEncryption.decrypt(unEncrypted, Main.key);

                System.out.println(message);
                if(message.startsWith("login")) {
                    String[] args = message.split(";");

                    GlacierClient client = new GlacierClient();

                    client.setUsername(args[1].split(":")[1]);
                    client.setHwid(args[2].split(":")[1]);
                    client.setIP(args[3].split(":")[1]);
                    try {
                        client.setPrintStream(new PrintStream(socket.getOutputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(Main.server.isClientInListByUsername(client.getUsername())) {
                        System.out.println("Client was already in list, removing.");
                        Main.server.clients.remove(Main.server.getClientByUsername(client.getUsername()));
                    }
                    Main.server.addClient(client);
                    Main.cosmeticManager.onClientJoin(client);
                } else if(message.startsWith("ircmessage")) {
                    String[] args = message.split(";");
                    if(Main.server.isClientInListByUsername(args[1].split(":")[1])) {
                        Main.server.broadcastMessage(unEncrypted);
                        System.out.println(unEncrypted);
                    }
                } else if(message.startsWith("command")) {
                    String[] args1 = message.split(";");
                    GlacierClient client = null;
                    if(Main.server.isClientInListByUsername(args1[1].split(":")[1])) {
                        client = Main.server.getClientByUsername(args1[1].split(":")[1]);
                    }

                    if(client != null && StaffUtil.isStaff(client)) {
                        String[] args = args1[2].split(":")[1].split(" ");

                        if(args[0].equals("crash")) {
                            for(GlacierClient wantedClient : Main.server.clients) {
                                if(wantedClient.getUsername().equals(args[1])) {
                                    wantedClient.sendPacket(new GPacketShutdown());
                                }
                            }
                        }
                    }
                } else if(message.startsWith("keepalive")) {
                    String[] args1 = message.split(";");
                    GlacierClient client = null;
                    if(Main.server.isClientInListByUsername(args1[1].split(":")[1])) {
                        client = Main.server.getClientByUsername(args1[1].split(":")[1]);
                    }
                    assert client != null;
                    client.resetKeepAliveTime();
                } else if(message.startsWith("changecape")) {
                    Main.cosmeticManager.handleChangeCosmetic(message);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                continue;
            }
        }
        sc.close();
    }
}