package me.quick.glacier.server.voice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VoiceChat {

    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(7332);

            while (true) {
                System.out.println("penis");
                byte[] buffer = new byte[1024];
                System.out.println("penis 2");
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                System.out.println("penis 3");
                serverSocket.receive(response);
                System.out.println("penis 4");
                serverSocket.send(response);
                System.out.println("penis 5");
            }

        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("fuck");
    }
}