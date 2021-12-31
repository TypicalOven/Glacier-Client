package me.quick.glacier.module.hud.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.util.font.FontUtil;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import me.quick.glacier.util.setting.impl.ModeSetting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;

public class Keystrokes extends RenderModule {

    BooleanSetting cFont = new BooleanSetting("Custom Font", false, this);
    BooleanSetting leftCps = new BooleanSetting("Show LMB CPS", true, this);
    BooleanSetting rightCps = new BooleanSetting("Show RMB CPS", true, this);
    ModeSetting keymode = new ModeSetting("Mode", this, "WASD", "WASD", "WASD MOUSE", "WASD JUMP", "WASD JUMP MOUSE");

    public Keystrokes() {
        super("Keystrokes", "Show keys that are being pressed", new ResourceLocation("glacier/icon/module/keystrokes.png"));
        this.addSettings(keymode,cFont,leftCps,rightCps);
    }

    public static enum KeystrokesMode {

        WASD(Key.W, Key.A, Key.S, Key.D),
        WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
        WASD_JUMP(Key.W, Key.A, Key.S, Key.D, new Key("§m-----", Minecraft.getMinecraft().gameSettings.keyBindSneak, 1, 41, 58, 18, false)),
        WASD_JUMP_MOUSE(Key.W, Key.A, Key.S, Key.LMB, Key.RMB, Key.D, new Key("§m-----", Minecraft.getMinecraft().gameSettings.keyBindSneak, 1, 61, 58, 18, false));
        private final Key[] keys;
        private int width = 0;
        private int height  = 0;

        private KeystrokesMode(Key... keysIn) {
            this.keys = keysIn;

            for(Key key : keys) {
                this.width = Math.max(this.width, key.getX() + key.getWidth());
                this.height = Math.max(this.height, key.getY() + key.getHeight());

            }
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public Key[] getKeys() {
            return keys;
        }

    }

    private static class Key {

        private static final Key W = new Key("W", Minecraft.getMinecraft().gameSettings.keyBindLeft, 21, 1, 18, 18, false);
        private static final Key A = new Key("A", Minecraft.getMinecraft().gameSettings.keyBindBack, 1, 21, 18, 18, false);
        private static final Key S = new Key("S", Minecraft.getMinecraft().gameSettings.keyBindRight, 21, 21, 18, 18, false);
        private static final Key D = new Key("D", Minecraft.getMinecraft().gameSettings.keyBindJump, 41, 21, 18, 18, false);

        private static final Key LMB = new Key("LMB", Minecraft.getMinecraft().gameSettings.keyBindPickBlock, 1, 41, 28, 18, true);
        private static final Key RMB = new Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindDrop, 31, 41, 28, 18, true);

        private final String name;
        private final KeyBinding keyBind;
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private final boolean cps;

        public Key(String name, KeyBinding keyBind, int x, int y, int width, int height, boolean cps) {

            this.name = name;
            this.keyBind = keyBind;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.cps = cps;
        }

        public boolean isDown() {
            return keyBind.isKeyDown();
        }

        public int getHeight() {
            return height;
        }

        public String getName() {
            return name;
        }

        public int getWidth() {
            return width;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    private KeystrokesMode mode = KeystrokesMode.WASD_JUMP_MOUSE;

    public void setMode(KeystrokesMode mode) {
        this.mode = mode;
    }

    @Override
    public int getWidth() {
        return mode.getWidth();
    }

    @Override
    public int getHeight() {
        return mode.getHeight();
    }

    private List<Long> clicks = new ArrayList<Long>();
    private boolean wasPressed;
    private long lastPressed;

    private List<Long> clicks2 = new ArrayList<Long>();
    private boolean wasPressed2;
    private long lastPressed2;

    @Override
    public void render() {

        if(keymode.is("WASD")) {
            this.setMode(KeystrokesMode.WASD);
        } else if(keymode.is("WASD JUMP")) {
            this.setMode(KeystrokesMode.WASD_JUMP);
        } else if(keymode.is("WASD JUMP MOUSE")) {
            this.setMode(KeystrokesMode.WASD_JUMP_MOUSE);
        } else if(keymode.is("WASD MOUSE")) {
            this.setMode(KeystrokesMode.WASD_MOUSE);
        }

        final boolean lpressed = Mouse.isButtonDown(0);
        final boolean rpressed = Mouse.isButtonDown(1);

        if(lpressed != this.wasPressed) {
            this.lastPressed = System.currentTimeMillis() + 10;
            this.wasPressed = lpressed;
            if(lpressed) {
                this.clicks.add(this.lastPressed);
            }
        }

        if(rpressed != this.wasPressed2) {
            this.lastPressed2 = System.currentTimeMillis() + 10;
            this.wasPressed2 = rpressed;
            if(rpressed) {
                this.clicks2.add(this.lastPressed2);
            }
        }

        GL11.glPushMatrix();

        for(Key key : mode.getKeys()) {

            int textWidth = (int) (cFont.isEnabled() ? FontUtil.regular.getStringWidth(key.getName()) : fr.getStringWidth(key.getName()));

            Gui.drawRect(
                    this.getX() + key.getX(),
                    this.getY() + key.getY(),
                    this.getX() + key.getX() + key.getWidth(),
                    this.getY() + key.getY() + key.getHeight(),
                    key.isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 150).getRGB()
            );

            if(cFont.isEnabled() && !key.getName().matches("§m-----")) {
                FontUtil.regular.drawString(
                        key.getName(),
                        this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 - 0.5f,
                        this.getY() + key.getY() + key.getHeight() / 2 - 4,
                        key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
                );
            } else {
                fr.drawString(
                        key.getName(),
                        this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2,
                        this.getY() + key.getY() + key.getHeight() / 2 - 4,
                        key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
                );
            }
            if(key.cps) {
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                GlStateManager.translate(this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2F , this.getY() + key.getY() + key.getHeight() / 2 + 4F, 1F);
                if(key.getName().matches(key.LMB.getName()) && leftCps.isEnabled()) {
                    if(cFont.isEnabled()) {
                        FontUtil.regular.drawString("CPS: " + getCPS(), this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 , this.getY() + key.getY() + key.getHeight() / 2 + 4, key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                    } else {
                        fr.drawString("CPS: " + getCPS(), this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 , this.getY() + key.getY() + key.getHeight() / 2 + 4, key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                    }
                }

                if(key.getName().matches(key.RMB.getName()) && rightCps.isEnabled()) {
                    if(cFont.isEnabled()) {
                        FontUtil.regular.drawString("CPS: " + getCPS2(), this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 , this.getY() + key.getY() + key.getHeight() / 2 + 4, key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                    } else {
                        fr.drawString("CPS: " + getCPS2(), this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 , this.getY() + key.getY() + key.getHeight() / 2 + 4, key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                    }
                }
                GlStateManager.popMatrix();
            }

        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderDummy() {

        GL11.glPushMatrix();

        for(Key key : mode.getKeys()) {

            int textWidth = (int) (cFont.isEnabled() ? FontUtil.regular.getStringWidth(key.getName()) : fr.getStringWidth(key.getName()));

            Gui.drawRect(
                    this.getX() + key.getX(),
                    this.getY() + key.getY(),
                    this.getX() + key.getX() + key.getWidth(),
                    this.getY() + key.getY() + key.getHeight(),
                    key.isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 150).getRGB()
            );

            if(cFont.isEnabled()) {
                FontUtil.regular.drawString(
                        key.getName(),
                        this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2,
                        this.getY() + key.getY() + key.getHeight() / 2 - 4,
                        key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
                );
            } else {
                fr.drawString(
                        key.getName(),
                        this.getX() + key.getX() + key.getWidth() / 2 - textWidth / 2,
                        this.getY() + key.getY() + key.getHeight() / 2 - 4,
                        key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB()
                );
            }

        }

        GL11.glPopMatrix();

    }

    private int getCPS() {
        final long time = System.currentTimeMillis();
        this.clicks.removeIf(aLong -> aLong + 1000 < time);
        return this.clicks.size();
    }

    private int getCPS2() {
        final long time2 = System.currentTimeMillis();
        this.clicks2.removeIf(aLong2 -> aLong2 + 1000 < time2);
        return this.clicks2.size();
    }

}

