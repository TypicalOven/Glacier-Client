package me.quick.glacier.cosmetic.cape;

import me.quick.glacier.Glacier;
import me.quick.glacier.event.EventTarget;
import me.quick.glacier.event.impl.EventClientTick;
import me.quick.glacier.event.impl.EventUpdate;
import me.quick.glacier.server.socket.packet.impl.GPacketChangeCape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class CapeManager {

    public int index;
    public ArrayList<Capes> capes = new ArrayList<>();

    public HashMap<UUID, String> capesHashMap;

    public boolean hasSetCapes = false;

    public CapeManager() {
        capesHashMap = new HashMap<>();
        capes.addAll(Arrays.asList(Capes.values()));
        index = capes.indexOf(Capes.NONE);
    }

    public boolean shouldRender() {
        return getActiveCape() != Capes.NONE;
    }

    public void setMode(Capes activeCape) {
        this.index = this.capes.indexOf(activeCape);
    }

    public Capes getActiveCape() {
        //Minecraft.getMinecraft().thePlayer.sendChatMessage(capes.get(index).name);
        return capes.get(index);
    }

    public void cycle() {
        if (index < capes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
        Minecraft.getMinecraft().thePlayer.setCape(getActiveCape());
        try {
            Glacier.INSTANCE.networkClient.sendPacket(new GPacketChangeCape(getActiveCape()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cycleBack() {
        if (index > 0) {
            index--;
        } else {
            index = capes.size() - 1;
        }
        Minecraft.getMinecraft().thePlayer.setCape(getActiveCape());
        try {
            Glacier.INSTANCE.networkClient.sendPacket(new GPacketChangeCape(getActiveCape()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Capes getCapeByName(String name) {
        for(Capes cape : capes) {
            if(cape.getResourceLocation().getResourcePath().endsWith(name) || cape.getName().equalsIgnoreCase(name)) return cape;
        }

        return null;
    }

    @EventTarget
    public void onUpdate(EventClientTick event) {
        if(Minecraft.getMinecraft().theWorld == null) hasSetCapes = false;

        if(!hasSetCapes && Minecraft.getMinecraft().theWorld != null) {
            for(EntityPlayer e : Minecraft.getMinecraft().theWorld.playerEntities) {
                AbstractClientPlayer player = (AbstractClientPlayer) e;
                if(player.getCape() == null) {

                    for(UUID uuid : capesHashMap.keySet()) {
                        if(player.getUniqueID().equals(uuid)) {
                            player.setCape(getCapeByName(capesHashMap.get(uuid)));
                        }
                    }

                }
            }
        }
    }

}
