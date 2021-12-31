package me.quick.glacier.module.hud.impl;

import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import net.minecraft.util.ResourceLocation;

public class FPSModule extends RenderModule {

    public BooleanSetting showFPSIndicator = new BooleanSetting("Show \"FPS\" after the FPS counter", true, this);
    public BooleanSetting brackets = new BooleanSetting("Add brackets around FPS counter", false, this);

    public FPSModule() {
        super("FPS", "Display your Frames per second", new ResourceLocation("glacier/icon/module/fps.png"));
        addSettings(showFPSIndicator);
    }

    @Override
    public void renderDummy() {
        StringBuffer sb = new StringBuffer();
        if (brackets.getValue()) sb.append("[");
        sb.append(mc.getDebugFPS());
        if (showFPSIndicator.getValue()) sb.append(" FPS");
        if (brackets.getValue()) sb.append("]");
        fr.drawStringWithShadow(sb.toString(), getX(), getY(), -1);
    }

    @Override
    public void render() {
        StringBuffer sb = new StringBuffer();
        if (brackets.getValue()) sb.append("[");
        sb.append(mc.getDebugFPS());
        if (showFPSIndicator.getValue()) sb.append(" FPS");
        if (brackets.getValue()) sb.append("]");
        fr.drawStringWithShadow(sb.toString(), getX(), getY(), -1);
    }

    @Override
    public int getWidth() {
        StringBuffer sb = new StringBuffer();
        if (brackets.getValue()) sb.append("[");
        sb.append(mc.getDebugFPS());
        if (showFPSIndicator.getValue()) sb.append(" FPS");
        if (brackets.getValue()) sb.append("]");
        return fr.getStringWidth(sb.toString());
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }

}
