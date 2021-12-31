package me.quick.glacier.module.hud.impl;

import java.util.ArrayList;
import java.util.List;

import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import me.quick.glacier.util.setting.impl.ModeSetting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;



public class CPSModule extends RenderModule {

    BooleanSetting cFont = new BooleanSetting("Custom Font", false, this);
    BooleanSetting left = new BooleanSetting("Show left Clicks", true, this);
    BooleanSetting right = new BooleanSetting("Show right Clicks", true, this);
    BooleanSetting label = new BooleanSetting("Label", true, this);
    ModeSetting seperator = new ModeSetting("Seperator", this, "|", "|","-","None");
    ModeSetting brackets = new ModeSetting("Brackets", this, "None", "None","< >","[ ]");

    public CPSModule() {
        super("CPS", "Shows the number of CPS you are clicking", new ResourceLocation("glacier/icon/module/cps.png"));
        this.addSettings(cFont,left,right,label,seperator,brackets);
    }

    private List<Long> leftClicks = new ArrayList<Long>();
    private List<Long> rightClicks = new ArrayList<Long>();
    private boolean leftWasPressed;
    private boolean rightWasPressed;
    private long leftLastPress;
    private long rightLastPress;

    private String getStringToRender() {
        String bracket1 = null, bracket2 = null;
        String toReturn = "Error!";
        switch(brackets.getMode()) {
            case "None" :
                bracket1 = "";
                bracket2 = "";
                break;
            case "< >" :
                bracket1 = "<";
                bracket2 = ">";
                break;
            case "[ ]" :
                bracket1 = "[";
                bracket2 = "]";
                break;
        }

        if(left.isEnabled() && right.isEnabled()) {
            toReturn = bracket1 + this.getLeftCPS() + " " + (seperator.is("None") ? " " : seperator.getMode()) + " " + this.getRightCPS() + " " +  (label.isEnabled() ? "CPS" : "") + bracket2;
        } else if(left.isEnabled() && !right.isEnabled()) {
            toReturn = bracket1 + this.getLeftCPS() + " " +  (label.isEnabled() ? "CPS" : "") + bracket2;
        } else if(!left.isEnabled() && right.isEnabled()) {
            toReturn = bracket1 + this.getRightCPS() + " " + (label.isEnabled() ? "CPS" : "") + bracket2;
        }

        return toReturn;

    }

    @Override
    public int getHeight() {
        //return fr.FONT_HEIGHT;
        if(cFont.isEnabled()) {
            return FontUtil.regular.getHeight();
        } else {
            return fr.FONT_HEIGHT;
        }
    }

    @Override
    public int getWidth() {
        if(cFont.isEnabled()) {
            return (int) FontUtil.regular.getStringWidth(getStringToRender());
        } else {
            return (int) fr.getStringWidth(getStringToRender());
        }
    }


    @Override
    public void render() {
        super.render();
        final boolean leftPressed = Mouse.isButtonDown(0);
        final boolean rightPressed = Mouse.isButtonDown(1);
        if (leftPressed != this.leftWasPressed) {
            this.leftWasPressed = leftPressed;
            this.leftLastPress = System.currentTimeMillis();
            if (leftPressed) {
                this.leftClicks.add(this.leftLastPress);
            }
        }
        if (rightPressed != this.rightWasPressed) {
            this.rightWasPressed = rightPressed;
            this.rightLastPress = System.currentTimeMillis();
            if (rightPressed) {
                this.rightClicks.add(this.rightLastPress);
            }
        }
        if(cFont.isEnabled()) {
            FontUtil.regular.drawStringWithShadow(getStringToRender(), this.getX(), this.getY(), -1);
        } else {
            fr.drawStringWithShadow(getStringToRender(), this.getX(), this.getY(), -1);
        }
    }

    @Override
    public void renderDummy() {
        super.renderDummy();
        final boolean leftPressed = Mouse.isButtonDown(0);
        final boolean rightPressed = Mouse.isButtonDown(1);
        if (leftPressed != this.leftWasPressed) {
            this.leftWasPressed = leftPressed;
            this.leftLastPress = System.currentTimeMillis();
            if (leftPressed) {
                this.leftClicks.add(this.leftLastPress);
            }
        }
        if (rightPressed != this.rightWasPressed) {
            this.rightWasPressed = rightPressed;
            this.rightLastPress = System.currentTimeMillis();
            if (rightPressed) {
                this.rightClicks.add(this.rightLastPress);
            }
        }
        if(cFont.isEnabled()) {
            FontUtil.regular.drawStringWithShadow(getStringToRender(), this.getX(), this.getY(), -1);
        } else {
            fr.drawStringWithShadow(getStringToRender(), this.getX(), this.getY(), -1);
        }
    }

    private int getLeftCPS() {
        final long time = System.currentTimeMillis();
        this.leftClicks.removeIf(aLong -> aLong + 1000L < time);
        return this.leftClicks.size();
    }

    private int getRightCPS() {
        final long time = System.currentTimeMillis();
        this.rightClicks.removeIf(aLong -> aLong + 1000L < time);
        return this.rightClicks.size();
    }
}
