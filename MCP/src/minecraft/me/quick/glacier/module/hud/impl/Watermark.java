package me.quick.glacier.module.hud.impl;

import me.quick.glacier.module.hud.RenderModule;
import net.minecraft.util.ResourceLocation;

public class Watermark extends RenderModule {

    public Watermark() {
        super("Watermark", "Show some glacier pride!", new ResourceLocation(""));
    }

    @Override
    public void render() {
        fr.drawStringWithShadow("Glacier Client", getX(), getY(), -1);
    }

    @Override
    public void renderDummy() {
        fr.drawStringWithShadow("Glacier Client", getX(), getY(), -1);
    }

    @Override
    public int getWidth() {
        return fr.getStringWidth("Glacier Client");
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
