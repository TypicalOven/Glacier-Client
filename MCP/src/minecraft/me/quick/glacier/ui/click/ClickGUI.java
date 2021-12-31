package me.quick.glacier.ui.click;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;

public class ClickGUI extends GuiScreen {

    public Panel panel;

    public ClickGUI() {
        //panel = new Panel(this.width / 2 - 225, this.height / 2 - 150);
    }

    @Override
    public void initGui() {
        super.initGui();
        panel = new Panel(this.width / 2 - 225, this.height / 2 - 150, this.width / 2 + 225, this.height / 2 + 150);
        mc.entityRenderer.loadShader(new ResourceLocation("glacier/blur.json"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        panel.draw(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        mc.entityRenderer.stopUseShader();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        panel.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        panel.handleMouseInput();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
