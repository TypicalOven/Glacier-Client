package me.quick.glacier.module.impl;

import me.quick.glacier.event.EventTarget;
import me.quick.glacier.event.impl.EventUpdate;
import me.quick.glacier.module.Module;
import me.quick.glacier.util.misc.Timer;
import me.quick.glacier.util.setting.impl.ModeSetting;
import net.minecraft.util.ResourceLocation;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", "Make everything bright", new ResourceLocation("glacier/icon/module/sun.png"));
    }

    Timer timer = new Timer();

    @EventTarget
    public void onUpdate(EventUpdate e) {
        mc.gameSettings.saturation = 100f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.saturation = 10f;
    }
}
