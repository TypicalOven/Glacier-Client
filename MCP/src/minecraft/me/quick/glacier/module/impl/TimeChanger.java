package me.quick.glacier.module.impl;

import me.quick.glacier.event.EventTarget;
import me.quick.glacier.event.impl.EventUpdate;
import me.quick.glacier.module.Module;
import me.quick.glacier.util.misc.Timer;
import me.quick.glacier.util.setting.impl.ModeSetting;
import net.minecraft.util.ResourceLocation;

public class TimeChanger extends Module {

    public ModeSetting time = new ModeSetting("Time", this, "Morning", "Morning", "Day", "Night", "Midnight", "Dynamic");

    public TimeChanger() {
        super("TimeChanger", "Change the time", new ResourceLocation("glacier/icon/module/timechanger.png"));
        this.addSettings(time);
    }

    Timer timer = new Timer();

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(time.is("Dynamic")) {
            if(timer.hasTimeElapsed(17250, true)) {

            }
        }
    }

    public long getTime() {
        long fuck = 1000;
        switch (time.getMode()) {
            case "Morning":
                fuck = 23000;
                break;
            case "Day":
                fuck = 30000;
                break;
            case "Night":
                fuck = 15000;
                break;
            case "Midnight" :
                fuck = 18000;
                break;
            case "Dynamic":
                fuck = timer.getTime() * 4;
                break;
        }
        return fuck;
    }
}
