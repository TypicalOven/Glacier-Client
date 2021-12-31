package me.quick.glacier.util.ui;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;

import me.quick.glacier.util.Util;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class DrawUtil implements Util {
	
	public static void setGlColor(final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
	
	public static void drawRoundedRect(double x, double y, double x1, double y1, double radius, int color) {
	    GL11.glPushAttrib(0);
	    GL11.glScaled(0.5D, 0.5D, 0.5D);
	    x *= 2.0D;
	    y *= 2.0D;
	    x1 *= 2.0D;
	    y1 *= 2.0D;
	    GL11.glEnable(3042);
	    GL11.glDisable(3553);
	    setColor(color);
	    GL11.glEnable(2848);
	    GL11.glBegin(9);
	    int i;
	    for (i = 0; i <= 90; i += 3)
	      GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
	    for (i = 90; i <= 180; i += 3)
	      GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
	    for (i = 0; i <= 90; i += 3)
	      GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
	    for (i = 90; i <= 180; i += 3)
	      GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
	    GL11.glEnd();
	    GL11.glEnable(3553);
	    GL11.glDisable(3042);
	    GL11.glDisable(2848);
	    GL11.glDisable(3042);
	    GL11.glEnable(3553);
	    GL11.glScaled(2.0D, 2.0D, 2.0D);
	    GL11.glPopAttrib();
	  }
	  
	  public static void drawBorderedRoundedRect(float x, float y, float x1, float y1, float borderSize, int borderC, int insideC) {
	    drawRoundedRect(x, y, x1, y1, borderSize, borderC);
	    drawRoundedRect((x + 0.5F), (y + 0.5F), (x1 - 0.5F), (y1 - 0.5F), borderSize, insideC);
	  }
	  
	  public static void drawBorderedRoundedRect(float x, float y, float x1, float y1, float radius, float borderSize, int borderC, int insideC) {
	    drawRoundedRect(x, y, x1, y1, radius, borderC);
	    drawRoundedRect((x + borderSize), (y + borderSize), (x1 - borderSize), (y1 - borderSize), radius, insideC);
	  }
	  
	  public static void setColor(int color) {
	    float a = (color >> 24 & 0xFF) / 255.0F;
	    float r = (color >> 16 & 0xFF) / 255.0F;
	    float g = (color >> 8 & 0xFF) / 255.0F;
	    float b = (color & 0xFF) / 255.0F;
	    GL11.glColor4f(r, g, b, a);
	  }
	  
	  public static void drawHollowRect(double x, double y, double w, double h, int color) {

	      Gui.drawHorizontalLine(x, x + w, y, color);
	      Gui.drawHorizontalLine(x, x + w, y + h, color);

	      Gui.drawVerticalLine(x, y + h, y, color);
	      Gui.drawVerticalLine(x + w, y + h, y, color);

	  }

	public static void drawCircle(double x, double y, float radius, int color) {
		float alpha = (float) (color >> 24 & 255) / 255.0f;
		float red = (float) (color >> 16 & 255) / 255.0f;
		float green = (float) (color >> 8 & 255) / 255.0f;
		float blue = (float) (color & 255) / 255.0f;
		glColor4f(red, green, blue, alpha);
		glBegin(9);
		int i = 0;
		while (i <= 360) {
			glVertex2d(x + Math.sin((double) i * 3.141526 / 180.0) * (double) radius, y + Math.cos((double) i * 3.141526 / 180.0) * (double) radius);
			++i;
		}
		glEnd();
	}

	public static void drawBorderedCircle(double x, double y, float radius, int outsideC, int insideC) {
		//  glEnable((int)3042);
		glDisable(3553);
		glBlendFunc(770, 771);
		glEnable(2848);
		glPushMatrix();
		float scale = 0.1f;
		glScalef(0.1f, 0.1f, 0.1f);
		drawCircle(x * 10, y * 10, radius * 10.0f, insideC);
		// drawUnfilledCircle(x, y, radius, 1.0f, outsideC);
		glScalef(10.0f, 10.0f, 10.0f);
		glPopMatrix();
		glEnable(3553);
		//  glDisable((int)3042);
		glDisable(2848);
	}
	  
	  public static void draw2DImage(ResourceLocation image, int x, int y, int width, int height, Color c) {
			glDisable(GL_DEPTH_TEST);
			glEnable(GL_BLEND);
			glDepthMask(false);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha());
			mc.getTextureManager().bindTexture(image);
			Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
			glDepthMask(true);
			glDisable(GL_BLEND);
			glEnable(GL_DEPTH_TEST);
			glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
}
