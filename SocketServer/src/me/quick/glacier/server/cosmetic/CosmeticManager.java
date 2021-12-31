package me.quick.glacier.server.cosmetic;

import me.quick.glacier.server.Main;
import me.quick.glacier.server.client.GlacierClient;
import me.quick.glacier.server.socket.packet.impl.GPacketShutdown;
import me.quick.glacier.server.socket.packet.impl.GPacketUserUpdateCape;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class CosmeticManager {

    public HashMap<UUID, String> capesHashMap;


    public CosmeticManager() {
        capesHashMap = new HashMap<>();
    }

    public boolean handleChangeCosmetic(String packet) {
        UUID toCompare = UUID.fromString(packet.split(";")[1].split(":")[1]);
        String capeId = packet.split(";")[2].split(":")[1];
        for(UUID uuid : capesHashMap.keySet()) {
            if(uuid.equals(toCompare)) {
                capesHashMap.replace(uuid,  capeId);
                for(GlacierClient client : Main.server.clients) {
                    client.sendPacket(new GPacketUserUpdateCape(toCompare, capeId));
                }
                return true;
            }
        }

        capesHashMap.put(toCompare, capeId);
        for(GlacierClient client : Main.server.clients) {
            client.sendPacket(new GPacketUserUpdateCape(toCompare, capeId));
        }
        return true;
    }

    public void onClientJoin(GlacierClient client) {
        for(UUID uuid : capesHashMap.keySet()) {
            client.sendPacket(new GPacketUserUpdateCape(uuid, capesHashMap.get(uuid)));
        }
    }

}
