package me.quick.glacier.server.socket;

import me.quick.glacier.server.client.GlacierClient;
import me.quick.glacier.server.socket.handler.AnnouncementHandler;
import me.quick.glacier.server.socket.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkServer {

    public int port;
    public List<GlacierClient> clients;
    public ServerSocket server;

    public NetworkServer(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void run() throws IOException {
        server = new ServerSocket(port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };
        System.out.println("Port " + port + " is now open.");

        new Thread(new AnnouncementHandler()).start();

        while (true) {
            Socket client = server.accept();
            System.out.println("Connection established with client: " + client.getInetAddress().getHostAddress());

            new Thread(new ClientHandler(this, client.getInputStream(), client)).start();
        }
    }

    public boolean isClientInListByUsername(String username) {
        for (GlacierClient client : clients) {
            if(client.getUsername().equals(username)) return true;
        }
        return false;
    }

    public GlacierClient getClientByUsername(String username) {
        for (GlacierClient client : clients) {
            if(client.getUsername().equals(username)) return client;
        }
        return null;
    }

    public void addClient(GlacierClient client) {
        System.out.println("Client joined. Username: " + client.getUsername());
        clients.add(client);
    }

    public void broadcastMessage(String msg) {
        for (GlacierClient client : clients) {
            client.sendMessage(msg);
        }
    }

    public void broadcastAnnouncement(String msg) {
        for (GlacierClient client : clients) {
            client.sendMessage(msg);
        }
    }
}
