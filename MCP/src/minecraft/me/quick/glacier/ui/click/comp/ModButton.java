package me.quick.glacier.ui.click.comp;

import java.awt.*;


import me.quick.glacier.Glacier;
import me.quick.glacier.module.Module;
import me.quick.glacier.ui.click.ClickGUI;
import me.quick.glacier.ui.click.PanelType;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.font.MinecraftFontRenderer;
import me.quick.glacier.util.ui.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModButton {
	
	public int x, y, w, h;
	public Module m;
	
	public ModButton(int x, int y, int w, int h, Module m) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.m = m;
	}
	
	public void draw() {
		MinecraftFontRenderer frbold = FontUtil.bold;
		MinecraftFontRenderer fr = FontUtil.regsmall;
		//Gui.drawRect(x, y, x + w, y + h, new Color(55, 55, 55).getRGB());
		DrawUtil.drawRoundedRect(x, y, x + w, y + h, 10, new Color(55, 55, 55).getRGB());
		/*Gui.drawRect(x, y + h - 15, x + w, y + h, getColor());
		fr.drawString(m.getName(), x + w / 2 - fr.getStringWidth(m.getName()) / 2, y + h - 24, -1);
		frbold.drawString(m.isEnabled() ? "Enabled" : "Disabled", x + w / 2 - frbold.getStringWidth(m.isEnabled() ? "Enabled" : "Disabled") / 2, y + h - 15 / 2 - frbold.getHeight() / 2, -1);
		DrawUtil.draw2DImage(m.getIcon(), x + 30, y + 10, 35, 35, Color.WHITE);*/
		if(!m.getIcon().getResourcePath().equals("")) DrawUtil.draw2DImage(m.getIcon(), x + 5, y + 5, 20, 20, Color.WHITE);
		Gui.drawRect(x + w - 20, y, x + w, y + h, getColor());
		fr.drawString(m.getName(), x + w - 20 - fr.getStringWidth(m.getName()) - 5, y + h / 2 - fr.getHeight() / 2, -1);
	}
	
	private int getColor() {
		if(m.isEnabled()) {
			return Glacier.getMainColor().getRGB();
		} else {
			return new Color(77, 77, 77).getRGB();
		}
	}
	
	
	public void onClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x + w - 20 && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			if(button == 0) {
				m.toggle();
			} else if(button == 1) {
				Glacier.INSTANCE.getClickGui().panel.setType(PanelType.SETTING, m);
			}
		}

	}

}