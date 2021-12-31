package me.quick.glacier.util.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("NonAtomicOperationOnVolatileField")
public class FontUtil {
    public static volatile int completed;
    public static MinecraftFontRenderer regular,  bold, regsmall, regmed, bold_large;
    private static Font regular_, bold_, regsmall_, regmed_, bold_large_;

    private static Font getFont(Map<String, Font> locationMap, String location, int size) {
        Font font = null;

        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(Font.PLAIN, size);
            } else {
                InputStream is = Minecraft.getMinecraft().getResourceManager()
                        .getResource(new ResourceLocation("glacier/font/" + location)).getInputStream();
                font = Font.createFont(0, is);
                locationMap.put(location, font);
                font = font.deriveFont(Font.PLAIN, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, +10);
        }

        return font;
    }

    public static boolean hasLoaded() {
        return completed >= 3;
    }

    public static void bootstrap() {
        new Thread(() ->
        {
            Map<String, Font> locationMap = new HashMap<>();
            regular_ = getFont(locationMap, "comfortaa.ttf", 19);
            bold_ = getFont(locationMap, "comfortaa-bold.ttf", 19);
            bold_large_ = getFont(locationMap, "comfortaa-bold.ttf", 30);
            regsmall_ = getFont(locationMap, "comfortaa.ttf", 10);
            regmed_ = getFont(locationMap, "comfortaa.ttf", 15);
            completed++;
        }).start();
        new Thread(() ->
        {
            Map<String, Font> locationMap = new HashMap<>();
            completed++;
        }).start();
        new Thread(() ->
        {
            Map<String, Font> locationMap = new HashMap<>();
            completed++;
        }).start();

        while (!hasLoaded()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        regular = new MinecraftFontRenderer(regular_, true, true);
        bold = new MinecraftFontRenderer(bold_, true, true);
        regsmall = new MinecraftFontRenderer(regsmall_, true, true);
        regmed = new MinecraftFontRenderer(regmed_, true, true);
        bold_large = new MinecraftFontRenderer(bold_large_, true, true);
    }
}