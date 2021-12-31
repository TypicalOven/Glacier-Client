package me.quick.glacier.module.hud.impl;

import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import net.minecraft.util.ResourceLocation;

public class Coords extends RenderModule {

    public BooleanSetting cfont = new BooleanSetting("Custom font", false, this);

    public Coords() {
        super("Coords", "Display your Frames per second", new ResourceLocation("glacier/icon/module/coords.png"));
        addSettings(cfont);
    }

    @Override
    public void renderDummy() {
        String penis = "X: " + (int) mc.thePlayer.posY + " Y: " + (int) mc.thePlayer.posY + " Z: " + (int) mc.thePlayer.posZ;
        if(cfont.isEnabled()) {
            customFr.drawStringWithShadow(penis, getX(), getY() + 1, -1);
        } else {
            fr.drawStringWithShadow(penis, getX(), getY() + 1, -1);
        }
    }

    @Override
    public void render() {
        String penis = "X: " + (int) mc.thePlayer.posY + " Y: " + (int) mc.thePlayer.posY + " Z: " + (int) mc.thePlayer.posZ;
        if(cfont.isEnabled()) {
            customFr.drawStringWithShadow(penis, getX(), getY() + 1, -1);
        } else {
            fr.drawStringWithShadow(penis, getX(), getY() + 1, -1);
        }
    }

    @Override
    public int getWidth() {
        String penis = "X: " + (int) mc.thePlayer.posY + " Y: " + (int) mc.thePlayer.posY + " Z: " + (int) mc.thePlayer.posZ;
        if(cfont.isEnabled()) {
            return (int) customFr.getStringWidth(penis);
        } else {
            return fr.getStringWidth(penis);
        }
    }

    @Override
    public int getHeight() {
        if(cfont.isEnabled()) {
            return (int) customFr.getHeight();
        } else {
            return fr.FONT_HEIGHT;
        }
    }

}
