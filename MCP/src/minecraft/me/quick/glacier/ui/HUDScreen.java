package me.quick.glacier.ui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import me.quick.glacier.Glacier;
import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.ui.click.ClickGUI;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class HUDScreen extends GuiScreen {

	public static HUDScreen INSTANCE = new HUDScreen();
	private RenderModule dragging;
	private int dragX, dragY;
	
	@Override
	public void initGui() {
		super.initGui();
		mc.entityRenderer.loadShader(new ResourceLocation("glacier/blur.json"));
		this.buttonList.add(new GuiButton(6340, this.width / 2 - 50, this.height / 2 - 10, 100, 20, "ClickGUI"));
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		mc.entityRenderer.stopUseShader();
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		for(RenderModule m : Glacier.INSTANCE.manager.getRenderModuleList()) {
			if (m.isEnabled()) {
				m.renderDummy();
				Gui.drawRect(m.getX() - 2, m.getY() - 2, m.getX() + m.getWidth() + 2, m.getY() + m.getHeight() + 2, new Color(255,255,255,40).getRGB());
				if(m.isHovered(mouseX, mouseY)) {
					this.drawBoxAroundModule(m);
				}
				if(m == dragging) {
					m.setX(dragX + mouseX);
		            m.setY(dragY + mouseY);
				}
			}
		}
	}
	
	private void drawBoxAroundModule(RenderModule m) {
		GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1);
        GL11.glColor4f(255, 255, 255, 255);
        
        // top line
        GL11.glBegin(2);
        GL11.glVertex2d(m.getX() - 1.5, m.getY() - 1.5);
        GL11.glVertex2d(m.getX() + m.getWidth() + 1.5, m.getY() - 1.5);
        GL11.glEnd();
        
        // bottom line
        GL11.glBegin(2);
        GL11.glVertex2d(m.getX() - 1.5, m.getY() + m.getHeight() + 1.5);
        GL11.glVertex2d(m.getX() + m.getWidth() + 1.5, m.getY() + m.getHeight() + 1.5);
        GL11.glEnd();
        
        // left line
        GL11.glBegin(2);
        GL11.glVertex2d(m.getX() - 1.5, m.getY() - 1.5);
        GL11.glVertex2d(m.getX() - 1.5, m.getY() + m.getHeight() + 1.5);
        GL11.glEnd();
        
        // right line
        GL11.glBegin(2);
        GL11.glVertex2d(m.getX() + m.getWidth() + 1.5, m.getY() - 1.5);
        GL11.glVertex2d(m.getX() + m.getWidth() + 1.5, m.getY() + m.getHeight() + 1.5);
        GL11.glEnd();
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
		
		//this.drawHorizontalLine(m.getX() - 2, m.getX() + m.getWidth() + 2, m.getY() - 2, -1);
		//this.drawHorizontalLine(m.getX() - 2, m.getX() + m.getWidth() + 2, m.getY() + m.getHeight(), -1);
		//this.drawVerticalLine(m.getX() - 2, m.getY() - 2, m.getY() + m.getHeight(), -1);
		//this.drawVerticalLine(m.getX() + m.getWidth() + 2, m.getY() - 2, m.getY() + m.getHeight(), -1);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		ArrayList<RenderModule> hovered = new ArrayList<>();
		for(RenderModule m : Glacier.INSTANCE.manager.getRenderModuleList()) {
			if(!m.isEnabled()) continue;
			if (m.isHovered(mouseX, mouseY) && mouseButton == 0) {
				hovered.add(m);
				dragging = m;
	            dragX = m.getX() - mouseX;
	            dragY = m.getY() - mouseY;
	        }
		}

		/*if(hovered.size() > 0 && !hovered.isEmpty() && hovered.get(0) != null) {
			RenderModule m = hovered.get(0);
			Glacier.sendMessage(m.name);
			dragging = m;
			dragX = m.getX() - mouseX;
			dragY = m.getY() - mouseY;
		}*/
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		switch(button.id) {
		case 6340:
			mc.displayGuiScreen(Glacier.INSTANCE.getClickGui());
		}
	}

	
	@Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
		dragging = null;
    }
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public static boolean isHover(int X, int Y, int W, int H, int mX, int mY) {
		return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
	}
}
