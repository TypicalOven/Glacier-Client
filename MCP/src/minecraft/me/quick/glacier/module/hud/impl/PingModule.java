package me.quick.glacier.module.hud.impl;

import me.quick.glacier.module.hud.RenderModule;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class PingModule extends RenderModule {

	public PingModule() {
		super("Ping", "Show your ping to the server", new ResourceLocation("glacier/icon/module/ping.png"));
	}
	
	@Override
    public int getWidth() {
        if(mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()) != null) {
            return fr.getStringWidth(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime() + (" ms"));
        } else {
            return fr.getStringWidth("1 ms");
        }
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }

    @Override
    public void render() {
	    if(mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()) != null) {
            fr.drawString(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime() + (" ms"), this.getX() + 1, this.getY() + 1, -1);
        } else {
            fr.drawString("1 ms", this.getX() + 1, this.getY() + 1, -1);
        }
    }
    
    @Override
    public void renderDummy() {
        if(mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()) != null) {
            fr.drawString(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime() + (" ms"), this.getX() + 1, this.getY() + 1, -1);
        } else {
            fr.drawString("1 ms", this.getX() + 1, this.getY() + 1, -1);
        }
	}
	
}

