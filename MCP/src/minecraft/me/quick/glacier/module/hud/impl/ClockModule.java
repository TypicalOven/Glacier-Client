package me.quick.glacier.module.hud.impl;

import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import net.minecraft.util.ResourceLocation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ClockModule extends RenderModule {
	
	public BooleanSetting includeSeconds = new BooleanSetting("Include Seconds", false, this);
	public BooleanSetting militaryTime = new BooleanSetting("24 Hour Format", false, this);
	
	BooleanSetting cFont = new BooleanSetting("Custom Font", false, this);
	BooleanSetting fShadow = new BooleanSetting("Font Shadow", false, this);

	public ClockModule() {
		super("Clock", "Show your local time in Minecraft", new ResourceLocation("glacier/icon/module/clock.png"));
		addSettings(includeSeconds, militaryTime,cFont,fShadow);
	}
	
	@Override
	public void render() {
		if(cFont.isEnabled()) {
        	if(fShadow.isEnabled()) {
        		FontUtil.regular.drawStringWithShadow(getTime(), this.getX(), this.getY(), -1);
        	} else {
        		FontUtil.regular.drawString(getTime(), this.getX(), this.getY(), -1);
        	}
        } else {
        	if(fShadow.isEnabled()) {
        		fr.drawStringWithShadow(getTime(), this.getX(), this.getY(), -1);
        	} else {
        		fr.drawString(getTime(), this.getX(), this.getY(), -1);
        	}
        }
	}
	
	@Override
	public void renderDummy() {
		if(cFont.isEnabled()) {
        	if(fShadow.isEnabled()) {
        		FontUtil.regular.drawStringWithShadow(getTime(), this.getX(), this.getY(), -1);
        	} else {
        		FontUtil.regular.drawString(getTime(), this.getX(), this.getY(), -1);
        	}
        } else {
        	if(fShadow.isEnabled()) {
        		fr.drawStringWithShadow(getTime(), this.getX(), this.getY(), -1);
        	} else {
        		fr.drawString(getTime(), this.getX(), this.getY(), -1);
        	}
        }
	}
	
	@Override
    public int getWidth() {
        if(cFont.isEnabled()) {
			return (int) FontUtil.regular.getStringWidth(getTime());
		} else {
			return fr.getStringWidth(getTime());
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

	
	
	public String getTime() {
		DateTimeFormatter dtf = null;
		if (includeSeconds.getValue()) {
			if (militaryTime.getValue()) dtf = DateTimeFormatter.ofPattern("hh:mm:ss.SSa");
			else dtf = DateTimeFormatter.ofPattern("h:mm:ss a");
		} else {
			if (militaryTime.getValue()) dtf = DateTimeFormatter.ofPattern("h:mm.SSa a");
			else dtf = DateTimeFormatter.ofPattern("h:mm a");
		}
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now).toString();
	}
	
}
