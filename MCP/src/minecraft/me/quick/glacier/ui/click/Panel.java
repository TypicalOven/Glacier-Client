package me.quick.glacier.ui.click;

import me.quick.glacier.Glacier;
import me.quick.glacier.module.Module;
import me.quick.glacier.ui.HUDScreen;
import me.quick.glacier.ui.click.comp.ModButton;
import me.quick.glacier.ui.click.comp.SettingButton;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.setting.Setting;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import me.quick.glacier.util.setting.impl.ModeSetting;
import me.quick.glacier.util.setting.impl.NumberSetting;
import me.quick.glacier.util.ui.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Panel {

    ArrayList<ModButton> modButtons = new ArrayList<>();
    ArrayList<SettingButton> settingButtons = new ArrayList<>();
    public int scrollAmount = 0;

    public PanelType type = PanelType.MODULE;
    public Module configuring;

    public int x, y, w, h;

    public Panel(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(int mouseX, int mouseY) {

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        Gui.drawRect(x, y, w, h, new Color(22, 22, 22).getRGB());
        Gui.drawRect(x + 2, y + 20, w - 2, h - 2, new Color(44, 44, 44).getRGB());

        if(mouseX >= x + 417 && mouseX <= x + 447 && mouseY >= y + 3 && mouseY <= y + 17) {
            DrawUtil.drawRoundedRect(x + 416.3f, y + 2.3f, x + 447.3f, y + 17.3f, 5, -1);
        }
        DrawUtil.drawRoundedRect(x + 417, y + 3, x + 447, y + 17, 5, new Color(190, 0, 0).getRGB());
        DrawUtil.draw2DImage(new ResourceLocation("glacier/icon/exit.png"), x + 427, y + 5, 10, 10, Color.WHITE);

        if(mouseX >= x + 377 && mouseX <= x + 407 && mouseY >= y + 3 && mouseY <= y + 17) {
            DrawUtil.drawRoundedRect(x + 376.3f, y + 2.3f, x + 407.3f, y + 17.3f, 5, -1);
        }
        DrawUtil.drawRoundedRect(x + 377, y + 3, x + 407, y + 17, 5, new Color(33, 33, 33).getRGB());
        DrawUtil.draw2DImage(new ResourceLocation("glacier/icon/move.png"), x + 387, y + 5, 10, 10, Color.WHITE);

        if(mouseX >= x + 337 && mouseX <= x + 367 && mouseY >= y + 3 && mouseY <= y + 17) {
            DrawUtil.drawRoundedRect(x + 336.3f, y + 2.3f, x + 367.3f, y + 17.3f, 5, -1);
        }
        DrawUtil.drawRoundedRect(x + 337, y + 3, x + 367, y + 17, 5, new Color(33, 33, 33).getRGB());
        DrawUtil.draw2DImage(new ResourceLocation("glacier/icon/cosmetic.png"), x + 347, y + 5, 10, 10, Color.WHITE);

        //voice button
        /*if(mouseX >= x + 2 && mouseX <= x + 32 && mouseY >= y + 3 && mouseY <= y + 17) {
            DrawUtil.drawRoundedRect(x + 1.3f, y + 2.3f, x + 32.3f, y + 17.3f, 5, -1);
        }
        DrawUtil.drawRoundedRect(x + 2, y + 3, x + 32, y + 17, 5, new Color(33, 33, 33).getRGB());
        DrawUtil.draw2DImage(new ResourceLocation("glacier/icon/microphone.png"), x + 12, y + 5, 10, 10, Color.WHITE);*/

        if(this.type == PanelType.MODULE) {
            this.modButtons.clear();
            int xAdd = 0;
            int xFactor = 100;
            int yAdd = 0;
            int spots = 0;
            while ((spots * xFactor) < (350)) {
                spots++;
            }
            for (Module m : Glacier.INSTANCE.manager.modules) {
                if (xAdd == (spots * xFactor) && xAdd != 0) {
                    xAdd = 0;
                    yAdd += 40;
                }
                this.modButtons.add(new ModButton(x + 30 + xAdd, y + 30 + yAdd + scrollAmount, 90, 30, m));

                xAdd += xFactor;

            }

            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            this.glScissor(x + 2, y + 25, w - 2, h - 2);
            for (ModButton m : modButtons) {
                m.draw();
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        } else if(this.type == PanelType.SETTING) {

            if(mouseX >= x + 3 && mouseX <= x + 30 && mouseY >= y + 3 && mouseY <= y + 17) {
                DrawUtil.drawRoundedRect(x + 2.3f, y + 2.3f, x + 30.3f, y + 17.3f, 5, -1);
            }
            DrawUtil.drawRoundedRect(x + 3, y + 3, x + 30, y + 17, 5, new Color(33, 33, 33).getRGB());
            DrawUtil.draw2DImage(new ResourceLocation("glacier/icon/back.png"), x + 11, y + 5, 10, 10, Color.WHITE);

            this.settingButtons.clear();
            int yAdd1 = 0;
            int yAdd2 = 0;
            int yAdd3 = 0;
            for (Setting set : configuring.settings) {

                if(set instanceof BooleanSetting) {
                    this.settingButtons.add(new SettingButton(x + 10, y + 30 + yAdd1 + scrollAmount, 60, 20, set));
                    yAdd1 += 40;
                } else if(set instanceof ModeSetting) {
                    this.settingButtons.add(new SettingButton(x + 140, y + 30 + yAdd2 + scrollAmount, 60, 20, set));
                    yAdd2 += 40;
                } else if(set instanceof NumberSetting) {
                    this.settingButtons.add(new SettingButton(x + 250, y + 30 + yAdd3 + scrollAmount, 60, 20, set));
                    yAdd3 += 40;
                }

            }

            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            this.glScissor(x + 2, y + 25, x + 450 - 2, h);
            for (SettingButton m : settingButtons) {
                m.draw(mouseX, mouseY);
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        } else if(this.type == PanelType.CAPE) {

            Gui.drawRect((x + w) / 2 - 90, (y + h) / 2 - 100, (x + w) / 2 + 90, (y + h) / 2 + 100, new Color(33, 33, 33).getRGB());
            Gui.drawRect((x + w) / 2 - 90, (y + h) / 2 + 80, (x + w) / 2 + 90, (y + h) / 2 + 100, new Color(22, 22, 22).getRGB());
            FontUtil.regular.drawString(Glacier.INSTANCE.capeManager.getActiveCape().getName(), (x + w) / 2 -  FontUtil.regular.getStringWidth(Glacier.INSTANCE.capeManager.getActiveCape().getName()) / 2, (y + h) / 2 + 88, -1);

            if(mouseX >= ((x + w) / 2 - 88) && mouseX <= ((x + w) / 2 - 61) && mouseY >= ((y + h) / 2 + 83) && mouseY <= ((y + h) / 2 + 97)) {
                DrawUtil.drawRoundedRect((x + w) / 2 - 88.7f, (y + h) / 2 + 82.3f, (x + w) / 2 - 60.3f, (y + h) / 2 + 97.7f, 5, -1);
            }
            DrawUtil.drawRoundedRect((x + w) / 2 - 88, (y + h) / 2 + 83, (x + w) / 2 - 61, (y + h) / 2 + 97, 5, new Color(33, 33, 33).getRGB());
            DrawUtil.draw2DImage(new ResourceLocation("glacier/icon/back.png"), ((x + w) / 2 - 80), (y + h) / 2 + 85, 10, 10, Color.WHITE);

            if(mouseX >= ((x + w) / 2 + 61) && mouseX <= ((x + w) / 2 + 88) && mouseY >= ((y + h) / 2 + 83) && mouseY <= ((y + h) / 2 + 97)) {
                DrawUtil.drawRoundedRect((x + w) / 2 + 60.3f, (y + h) / 2 + 82.3f, (x + w) / 2 + 88.7f, (y + h) / 2 + 97.7f, 5, -1);
            }
            DrawUtil.drawRoundedRect((x + w) / 2 + 61, (y + h) / 2 + 83, (x + w) / 2 + 88, (y + h) / 2 + 97, 5, new Color(33, 33, 33).getRGB());
            DrawUtil.draw2DImage(new ResourceLocation("glacier/icon/back_face_right.png"), ((x + w) / 2 + 69), (y + h) / 2 + 85, 10, 10, Color.WHITE);

            this.drawEntityOnScreen((x + w) / 2, (y + h) / 2 + 55, 70, 0, 0, Minecraft.getMinecraft().thePlayer);

        }
    }

    private void glScissor(double x, double y, double width, double height) {

        y += height;

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        Minecraft mc = Minecraft.getMinecraft();

        GL11.glScissor((int) ((x * mc.displayWidth) / scaledResolution.getScaledWidth()),
                (int) (((scaledResolution.getScaledHeight() - y) * mc.displayHeight) / scaledResolution.getScaledHeight()),
                (int) (width * mc.displayWidth / scaledResolution.getScaledWidth()),
                (int) (height * mc.displayHeight / scaledResolution.getScaledHeight()));
    }

    public void setType(PanelType lol, Module m) {
        if(lol == PanelType.MODULE) {
            this.type = PanelType.MODULE;
            this.configuring = null;
        } else if(lol == PanelType.SETTING) {
            this.type = PanelType.SETTING;
            this.configuring = m;
        } else if(lol == PanelType.CAPE) {
            this.type = PanelType.CAPE;
            this.configuring = null;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(ModButton m : modButtons) {
            m.onClick(mouseX, mouseY, mouseButton);
        }
        for(SettingButton s : settingButtons) {
            s.onClick(mouseX, mouseY, mouseButton);
        }
        if(mouseX >= x + 3 && mouseX <= x + 30 && mouseY >= y + 3 && mouseY <= y + 17) {
            if(type == PanelType.SETTING) this.setType(PanelType.MODULE, null);
        }
        if(mouseX >= x + 417 && mouseX <= x + 447 && mouseY >= y + 3 && mouseY <= y + 17) {
            Minecraft.getMinecraft().thePlayer.closeScreen();
        }
        if(mouseX >= x + 377 && mouseX <= x + 407 && mouseY >= y + 3 && mouseY <= y + 17) {
            Minecraft.getMinecraft().displayGuiScreen(new HUDScreen());
        }
        if(mouseX >= x + 337 && mouseX <= x + 367 && mouseY >= y + 3 && mouseY <= y + 17) {
            this.setType(PanelType.CAPE, null);
        }

        /*if(mouseX >= x + 2 && mouseX <= x + 32 && mouseY >= y + 3 && mouseY <= y + 17) {
            Glacier.INSTANCE.voiceClient.start();
        }*/

        if(type == PanelType.CAPE) {
            if(mouseX >= ((x + w) / 2 - 88) && mouseX <= ((x + w) / 2 - 61) && mouseY >= ((y + h) / 2 + 83) && mouseY <= ((y + h) / 2 + 97)) {
                Glacier.INSTANCE.capeManager.cycleBack();
            }
            if(mouseX >= ((x + w) / 2 + 61) && mouseX <= ((x + w) / 2 + 88) && mouseY >= ((y + h) / 2 + 83) && mouseY <= ((y + h) / 2 + 97)) {
                Glacier.INSTANCE.capeManager.cycle();
            }
        }
    }

    public void handleMouseInput() throws IOException {

        int i = Integer.signum(Mouse.getEventDWheel());

        scrollAmount += (10 * i);
        if (scrollAmount > 0) scrollAmount = 0;
        if (scrollAmount < -100) scrollAmount = -100;

        //System.out.println(scrollAmount);
    }

    public void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = 180;
        ent.rotationYaw = 180;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

}

