package me.quick.glacier.module.hud;

import me.quick.glacier.Glacier;
import me.quick.glacier.module.Module;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.font.MinecraftFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderModule extends Module {
	
	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer fr;
	public MinecraftFontRenderer customFr;
	public int xPos = 10;
	public int yPos = 10;

	public RenderModule(String name, String description, ResourceLocation icon) {
		super(name,description, icon);
		fr = mc.fontRendererObj;
		customFr = FontUtil.regular;
		Glacier.INSTANCE.eventManager.register(this);
	}
	
	public RenderModule(String name, String description, ResourceLocation icon, int defaultX, int defaultY) {
		super(name,description, icon);
		fr = mc.fontRendererObj;
		customFr = FontUtil.regular;
		this.xPos = defaultX;
		this.yPos = defaultY;
		Glacier.INSTANCE.eventManager.register(this);
		this.enabled = true;
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		
		return mouseX >= xPos && mouseX <= xPos + getWidth() && mouseY >= yPos && mouseY <= yPos + getHeight();

	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void setX(int x) {
		this.xPos = x;
	}
	
	public void setY(int y) {
		this.yPos = y;
	}
	
	public int getWidth() {
		return 0;
	}
	
	public int getHeight() {
		return 0;
	}
	
	public void render() {
		
	}
	
	public void renderDummy() {
		
	}
}
