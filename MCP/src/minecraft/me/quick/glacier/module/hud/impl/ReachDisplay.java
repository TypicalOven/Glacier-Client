package me.quick.glacier.module.hud.impl;

import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class ReachDisplay extends RenderModule {

    EntityLivingBase hit = null;
    double reach;

    BooleanSetting cFont = new BooleanSetting("Custom Font", false, this);

    public ReachDisplay() {
        super("ReachDisplay", "Displays your reach", new ResourceLocation("glacier/icon/module/reach.png"));
        addSettings(cFont);
    }

    @Override
    public void renderDummy() {
        if(cFont.isEnabled()) {
            FontUtil.regular.drawStringWithShadow("3.00", getX(), getY(), -1);
        } else {
            fr.drawStringWithShadow("3.00", getX(), getY(), -1);
        }
    }

    @Override
    public void render() {
        hit = (EntityLivingBase) mc.pointedEntity;
        if(hit != null) {
            if(hit.hurtTime > 0) {
                reach = mc.thePlayer.getDistanceToEntity(hit) - 1;
            }
        }
        int n = 3;
        if(cFont.isEnabled()) {
            FontUtil.regular.drawStringWithShadow(Double.parseDouble((""+reach).substring(0, n)) + "", getX(), getY(), -1);
        } else {
            fr.drawStringWithShadow(Double.parseDouble((""+reach).substring(0, n)) + "", getX(), getY(), -1);
        }
    }

    @Override
    public int getWidth() {
        if(cFont.isEnabled()) {
            return (int) FontUtil.regular.getStringWidth("3.00");
        } else {
            return (int) fr.getStringWidth("3.00");
        }
    }

    @Override
    public int getHeight() {
        if(cFont.isEnabled()) {
            return FontUtil.regular.getHeight();
        } else {
            return fr.FONT_HEIGHT;
        }
    }

}
