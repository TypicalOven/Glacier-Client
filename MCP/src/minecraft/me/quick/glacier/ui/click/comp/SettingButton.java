package me.quick.glacier.ui.click.comp;

import java.awt.Color;


import me.quick.glacier.Glacier;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.font.MinecraftFontRenderer;
import me.quick.glacier.util.setting.Setting;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import me.quick.glacier.util.setting.impl.ModeSetting;
import me.quick.glacier.util.setting.impl.NumberSetting;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class SettingButton {
	
	public int x, y, w, h;
	public Setting set;
	public boolean isSliding = false;
	public double val;
	int anim=0,anim2=0;
	
	public SettingButton(int x, int y, int w, int h, Setting set) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.set = set;
	}
	
	public void draw(int mouseX, int mouseY) {
		MinecraftFontRenderer fr = FontUtil.regmed;
		
		if(set instanceof BooleanSetting) {
			fr.drawString(set.name, x, y+h / 2 - fr.getHeight() / 2, -1);
			
			Gui.drawRect(x + fr.getStringWidth(set.name) + 4, y + 2.5f, x + fr.getStringWidth(set.name) + 15 + 4, y + h - 2.5f, new Color(117, 117, 117).getRGB());
			
			if(((BooleanSetting) set).isEnabled()) {
				Gui.drawRect(x + fr.getStringWidth(set.name) + 6, y + 4.5f, x+fr.getStringWidth(set.name) + 15 + 2, y + h - 4.5f, Glacier.getMainColor().getRGB());
			}
		}
		
		if(set instanceof ModeSetting) {
			fr.drawString(set.name + ": " + ((ModeSetting) set).getMode(), x, y+h / 2 - fr.getHeight() / 2, -1);
		}
		if(set instanceof NumberSetting){
			
			boolean hovered = (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h);
            NumberSetting ns = (NumberSetting) set;
            if(isSliding){
                float value = (float)(mouseX - (x)) / (float)((w)/ns.getMaximum());
                value = (float) MathHelper.clamp_double(value, ns.getMinimum(), ns.getMaximum());
                val = (float)(mouseX - (x)) / (float)((w)/ns.getMaximum());
                val = (float) MathHelper.clamp_double(value, ns.getMinimum(), ns.getMaximum());
                //float f = this.denormalizeValue(value);
                ns.setValue(val);
                //value = this.normalizeValue(f);
            }
            
            
            Gui.drawRect(x, y + h - 3, x + w, y + h, new Color(117, 117, 117).getRGB());
        	if(anim2 > 0)anim2-=4;
            
            Gui.drawRect(x, y + h - 3, (x+(ns.getValue()/ns.getMaximum()*w)), (y + h), Glacier.getMainColor().getRGB());
            
            fr.drawString("" + ns.getValue(), (x+(ns.getValue()/ns.getMaximum()*w)) - fr.getStringWidth("" + ns.getValue()), y + h + 2, -1);
           

            fr.drawString(set.name, x, y+h/4, -1);
        }
		
	}
	
	public void onClick(int mouseX, int mouseY, int button) {
		if(set instanceof BooleanSetting) {
			if(mouseX >= x + FontUtil.regmed.getStringWidth(set.name) + 4 && mouseX <= x + FontUtil.regmed.getStringWidth(set.name) + 4 + 15 && mouseY >= y && mouseY <= y + h) {
				((BooleanSetting) set).setEnabled(!((BooleanSetting) set).isEnabled());
			}
		}
		
		if(set instanceof ModeSetting) {
			if(mouseX >= x && mouseX <= x + FontUtil.regmed.getStringWidth(set.name + ((ModeSetting) set).getMode()) && mouseY >= y && mouseY <= y + h) {
				((ModeSetting) set).increment();
			}
		}
		if(set instanceof NumberSetting) {
			if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
				 this.isSliding = true;
			}
		}
	}
	
	public void onRelease(int mouseX, int mouseY, int state) {
		isSliding = false;
	}

}