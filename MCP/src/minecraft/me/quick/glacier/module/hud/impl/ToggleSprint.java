package me.quick.glacier.module.hud.impl;

import java.text.DecimalFormat;


import me.quick.glacier.Glacier;
import me.quick.glacier.event.EventTarget;
import me.quick.glacier.event.impl.EventKey;
import me.quick.glacier.event.impl.EventUpdate;
import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.util.misc.Timer;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class ToggleSprint extends RenderModule {

	Timer cooldownTimer = new Timer();
	
	BooleanSetting cFont = new BooleanSetting("Custom Font", false, this);
	BooleanSetting fShadow = new BooleanSetting("Font Shadow", false, this);

	public ToggleSprint() {
		super("Toggle Sprint", "Toggle your sprint", new ResourceLocation("glacier/icon/module/togglesprint.png"));
		this.addSettings(cFont,fShadow);
	}
    
    @Override
    public int getWidth() {
        if(cFont.isEnabled()) {
			return (int) customFr.getStringWidth("[Sprinting (Key Toggled)]");
		} else {
			return fr.getStringWidth("[Sprinting (Key Toggled)]");
		}
    }

    @Override
    public int getHeight() {
    	if(cFont.isEnabled()) {
			return customFr.getHeight();
		} else {
			return fr.FONT_HEIGHT;
		}
    }

    @Override
    public void render() {
        String textToRender = getDisplayText();
        if(cFont.isEnabled()) {
        	if(fShadow.isEnabled()) {
				customFr.drawStringWithShadow(textToRender, this.getX(), this.getY(), -1);
        	} else {
				customFr.drawString(textToRender, this.getX(), this.getY(), -1);
        	}
        } else {
        	if(fShadow.isEnabled()) {
        		fr.drawStringWithShadow(textToRender, this.getX(), this.getY(), -1);
        	} else {
        		fr.drawString(textToRender, this.getX(), this.getY(), -1);
        	}
        }
    }
    
    @Override
    public void renderDummy() {
        String textToRenderDummy = "[Sprinting (Key Toggled)]";
        if(cFont.isEnabled()) {
        	if(fShadow.isEnabled()) {
				customFr.drawStringWithShadow(textToRenderDummy, this.getX(), this.getY(), -1);
        	} else {
				customFr.drawString(textToRenderDummy, this.getX(), this.getY(), -1);
        	}
        } else {
        	if(fShadow.isEnabled()) {
        		fr.drawStringWithShadow(textToRenderDummy, this.getX(), this.getY(), -1);
        	} else {
        		fr.drawString(textToRenderDummy, this.getX(), this.getY(), -1);
        	}
        }
    }

    @EventTarget
	public void onKey(EventKey e) {
		int key = mc.gameSettings.keyBindInventory.getKeyCode();
    	if(e.getKey() == key && cooldownTimer.hasTimeElapsed(300, false)) {
			cooldownTimer.reset();
    		if(Glacier.INSTANCE.sprintToggled) {
				Glacier.INSTANCE.sprintToggled = false;
				mc.thePlayer.setSprinting(false);
			} else {
				Glacier.INSTANCE.sprintToggled = true;
			}
    		mc.gameSettings.keyBindInventory.pressed = false;
		}
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {
		if(Glacier.INSTANCE.sprintToggled
				&& mc.thePlayer.onGround
				&& this.isEnabled()
				&& !mc.thePlayer.isUsingItem()
				&& !mc.thePlayer.isSwingInProgress
				&& !mc.thePlayer.isEating()
				&& !mc.thePlayer.isPotionActive(Potion.blindness)
				&& (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F)
				&& !mc.thePlayer.isSneaking()) {
			mc.thePlayer.setSprinting(true);
		}
	}

    public String getDisplayText() {

        String displayText = "";

        boolean isHoldingSprint = Minecraft.getMinecraft().gameSettings.keyBindInventory.isKeyDown(); // sprint


        if(Glacier.INSTANCE.sprintToggled ) {
            if(isHoldingSprint) {
                displayText += "[Sprinting (Key Held)]";
            } else {
                displayText += "[Sprinting (Key Toggled)]";
            }
        }
        return displayText.trim();
    }
	
}
